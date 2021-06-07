/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planer;

import entities.Korisnik;
import entities.Planer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author PC
 */
@Path("planer")
@Stateless
public class PlanerKomunikacija {

    @Resource(lookup = "MyTopic")
    Topic topic;

    @Resource(lookup = "QueuePlaner")
    Queue queue;

    @Resource(lookup = "QueueCreatePlan")
    Queue queuePlan;

    @Resource(lookup = "jms/__defaultConnectionFactory")
    ConnectionFactory connectionFactory;

    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;

    @POST
    @Path("distanceTime/{from}/{to}")
    public Response setDist(@Context HttpHeaders httpHeaders, @PathParam("from") String from, @PathParam("to") String to) {
        List<String> authHeaderValues = httpHeaders.getRequestHeader("Authorization");

        if (authHeaderValues != null && authHeaderValues.size() > 0) {
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")), StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();

            Korisnik k = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();

            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            ObjectMessage txt = context.createObjectMessage();

            try {
                txt.setStringProperty("to", "planer");
                txt.setIntProperty("who", k.getIdkorisnik());
                txt.setStringProperty("from", from);
                txt.setStringProperty("too", to);
                txt.setStringProperty("operation", "distanca");
            } catch (JMSException ex) {
                Logger.getLogger(PlanerKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
            }

            producer.send(topic, txt);

            return Response.status(Response.Status.OK).entity("Uspesno poslata poruka").build();

        }

        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }
    
        @POST
    @Path("deleteCreation/{id}")
    public Response setDist(@Context HttpHeaders httpHeaders, @PathParam("id") int id) {
        List<String> authHeaderValues = httpHeaders.getRequestHeader("Authorization");

        if (authHeaderValues != null && authHeaderValues.size() > 0) {
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")), StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();

            Korisnik k = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();

            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            ObjectMessage txt = context.createObjectMessage();

            try {
                txt.setStringProperty("to", "planer");
                txt.setIntProperty("deleteid", id);
                txt.setStringProperty("operation", "obrisi");
            } catch (JMSException ex) {
                Logger.getLogger(PlanerKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
            }

            producer.send(topic, txt);

            return Response.status(Response.Status.OK).entity("Uspesno poslata poruka").build();

        }

        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }

    @GET
    @Path("myPlans")
    public Response getDist(@Context HttpHeaders httpHeaders) {
        List<String> authHeaderValues = httpHeaders.getRequestHeader("Authorization");

        if (authHeaderValues != null && authHeaderValues.size() > 0) {
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")), StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();

            Korisnik k = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();
            List<Planer> planerList = em.createNamedQuery("Planer.findByIdget",Planer.class).setParameter("idget", k.getIdkorisnik()).getResultList();
            List<String> odgovor = new ArrayList<>();
            planerList.forEach(planer -> {
                odgovor.add(planer.getDestinacija()+" --- *FROM* "+planer.getPocetak()+" *TO* "+planer.getKraj()+" ID:"+planer.getIdplaner());
            });                        
            return Response.status(Response.Status.OK).entity(odgovor).build();
        }
        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }

    @GET
    @Path("distanceTimeGet")
    public Response getDist() {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);

        Message msg = consumer.receive();
        ObjectMessage obj = (ObjectMessage) msg;
        String broj;
        try {
            broj = obj.getStringProperty("vreme");
            return Response.status(Response.Status.OK).entity(broj).build();
        } catch (JMSException ex) {
            Logger.getLogger(PlanerKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }

    @GET
    @Path("getCreation")
    public Response getCreation() {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queuePlan);

        Message msg = consumer.receive();
        ObjectMessage obj = (ObjectMessage) msg;
        String broj;
        try {
            broj = obj.getStringProperty("vreme");
            return Response.status(Response.Status.OK).entity(broj).build();
        } catch (JMSException ex) {
            Logger.getLogger(PlanerKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }

    @POST
    @Path("create/{to}/{hours}/{minutes}/{trajanje}")
    public Response playAlarm(@Context HttpHeaders httpHeaders, @PathParam("to") String to, @PathParam("hours") int hours, @PathParam("minutes") int minutes, @PathParam("trajanje") int trajanje) {
        List<String> authHeaderValues = httpHeaders.getRequestHeader("Authorization");

        if (authHeaderValues != null && authHeaderValues.size() > 0) {
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")), StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();

            Korisnik k = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();

            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            ObjectMessage txt = context.createObjectMessage();

            try {
                txt.setStringProperty("to", "planer");
                txt.setIntProperty("who", k.getIdkorisnik());
                txt.setStringProperty("from", k.getKuca());
                txt.setStringProperty("too", (("_".equals(to)) ? k.getKuca() : to));
                txt.setStringProperty("operation", "kreiraj");
                txt.setIntProperty("hours", hours);
                txt.setIntProperty("minutes", minutes);
                txt.setIntProperty("trajanje", trajanje);
            } catch (JMSException ex) {
                Logger.getLogger(PlanerKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
            }

            producer.send(topic, txt);

            return Response.status(Response.Status.OK).entity("Uspesno poslata poruka").build();

        }

        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }

}
