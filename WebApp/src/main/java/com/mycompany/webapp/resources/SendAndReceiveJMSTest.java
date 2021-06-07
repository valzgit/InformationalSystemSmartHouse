package com.mycompany.webapp.resources;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("sendreceive")
@Stateless
public class SendAndReceiveJMSTest {
    
    @Resource(lookup = "MyTopic")
    public Topic destination;
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory connectionFactory;
    
    @GET
    @Path("receive")
    //Sinhroni prijem poruke
    public Response receive() throws JMSException{
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createSharedDurableConsumer(destination, "A");
        Message message = consumer.receive();
        
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
             return Response
                .ok("received message: " + textMessage.getText())
                .build();
        }
        
        return Response
                .ok("nothing received")
                .build();
    }
    
    @POST
    @Path("send")
    public Response send(String message){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        producer.send(destination, message);
        
        return Response
                .ok("message sent")
                .build();
    }
}
