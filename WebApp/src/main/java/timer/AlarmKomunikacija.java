/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timer;

import entities.Korisnik;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

@Path("alarm")
@Stateless
public class AlarmKomunikacija {
    
    @Resource(lookup = "MyTopic")
    Topic topic;
    
    @Resource(lookup = "QueueTimer")
    Queue queue;
    
    @Resource(lookup ="jms/__defaultConnectionFactory")
    ConnectionFactory connectionFactory;
    
    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;
    
    
    @POST
    @Path("single/{when}/{sound}")
    public Response playAlarm(@Context HttpHeaders httpHeaders,@PathParam("when") long when,@PathParam("sound") int sound){
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
                txt.setStringProperty("to", "timer");
                txt.setIntProperty("who", k.getIdkorisnik());
                txt.setLongProperty("when", when);
                txt.setStringProperty("operation", "single");
                txt.setIntProperty("zvuk", sound);
            } catch (JMSException ex) {
                Logger.getLogger(AlarmKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            producer.send(topic, txt);

            return Response.status(Response.Status.OK).entity("Uspesno poslata poruka").build();
            
        }
        
        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }
    
    @POST
    @Path("periodic/{when}/{sound}/{period}")
    public Response playPeriodicAlarm(@Context HttpHeaders httpHeaders,@PathParam("when") long when,@PathParam("sound") int sound,@PathParam("period") int period){
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
                txt.setStringProperty("to", "timer");
                txt.setIntProperty("who", k.getIdkorisnik());
                txt.setLongProperty("when", when);
                txt.setStringProperty("operation", "periodic");
                txt.setIntProperty("zvuk", sound);
                txt.setIntProperty("period", period);
            } catch (JMSException ex) {
                Logger.getLogger(AlarmKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            producer.send(topic, txt);

            return Response.status(Response.Status.OK).entity("Uspesno poslata poruka").build();
            
        }
        
        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }
    
     @POST
    @Path("oldalarms")
    public Response oldAlarms(@Context HttpHeaders httpHeaders){
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
                txt.setStringProperty("to", "timer");
                txt.setIntProperty("who", k.getIdkorisnik());
                txt.setStringProperty("operation", "wakeup");
            } catch (JMSException ex) {
                Logger.getLogger(AlarmKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            producer.send(topic, txt);

            return Response.status(Response.Status.OK).entity("Uspesno poslata poruka").build();
            
        }
        
        return Response.status(Response.Status.OK).entity("Neupesno poslata poruka").build();
    }
    
}
