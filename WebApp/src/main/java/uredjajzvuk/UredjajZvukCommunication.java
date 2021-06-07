/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uredjajzvuk;

import entities.Korisnik;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedList;
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
import javax.jms.TextMessage;
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
@Path("zvuk")
@Stateless
public class UredjajZvukCommunication {
    
    @Resource(lookup = "MyTopic")
    Topic topic;
    
    @Resource(lookup = "QueueZvuk")
    Queue queue;
    
    @Resource(lookup ="jms/__defaultConnectionFactory")
    ConnectionFactory connectionFactory;
    
    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;
    
    
    @GET
    @Path("logs")
    public Response getMusicLogsForUser(@Context HttpHeaders httpHeaders){
     List<String> authHeaderValues = httpHeaders.getRequestHeader("Authorization");
        
        if(authHeaderValues != null && authHeaderValues.size() > 0){
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")),StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();
            
            Korisnik k = em.createNamedQuery("Korisnik.findByUsername",Korisnik.class).setParameter("username", username).getSingleResult();

            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            ObjectMessage txt = context.createObjectMessage();
            try {
                txt.setIntProperty("who", k.getIdkorisnik());
                txt.setStringProperty("to", "uzrz");
                txt.setStringProperty("operation", "logs");
            } catch (JMSException ex) {
                Logger.getLogger(UredjajZvukCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(topic, txt);

//            Message msg = consumer.receive(); 
//            ObjectMessage obj = (ObjectMessage)msg;
           
            return Response.status(Response.Status.OK).entity("Uspesno primljena poruka").build();
            
            }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("UKNOWN ERROR!").build();
        }
        
    }
    
    @POST
    @Path("play/{ImePesme}")
    public Response playMusic(@Context HttpHeaders httpHeaders,@PathParam("ImePesme") String imePesme){
           List<String> authHeaderValues = httpHeaders.getRequestHeader("Authorization");
        
        if(authHeaderValues != null && authHeaderValues.size() > 0){
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")),StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();
            
            Korisnik k = em.createNamedQuery("Korisnik.findByUsername",Korisnik.class).setParameter("username", username).getSingleResult();


            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            ObjectMessage txt = context.createObjectMessage();
            try {
                txt.setStringProperty("to", "uzrz");
                txt.setIntProperty("who", k.getIdkorisnik());
                txt.setStringProperty("ime", imePesme);
                txt.setStringProperty("operation", "music");
            } catch (JMSException ex) {
                Logger.getLogger(UredjajZvukCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(topic, txt);

//            Message msg = consumer.receive(); 
//            ObjectMessage obj = (ObjectMessage)msg;
           
            return Response.status(Response.Status.OK).entity("Uspesno poslata poruka").build();
            
        }
        
        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }
    
    @GET
    @Path("getlogs")
    public Response getMusicLogsForUser2(){
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        
        Message msg = consumer.receive(); 
        ObjectMessage obj = (ObjectMessage)msg;
        try {
            LinkedList<String> songNames = (LinkedList<String>) obj.getObject();
            return Response.status(Response.Status.OK).entity(songNames).build();
        } catch (JMSException ex) {
            Logger.getLogger(UredjajZvukCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.OK).entity("Uspesno primljen objekat").build();
    }
    
}
