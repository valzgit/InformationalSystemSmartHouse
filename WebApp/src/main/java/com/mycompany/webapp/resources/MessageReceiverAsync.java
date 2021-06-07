package com.mycompany.webapp.resources;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

//Asinhroni prijem poruke koriscenjem message driven bean-a.
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "MyTopic"),
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability",
            propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName",
            propertyValue = "B")
})
public class MessageReceiverAsync implements MessageListener{

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            try {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Message received: " + textMessage.getText());
            } catch (JMSException ex) {
                Logger.getLogger(MessageReceiverAsync.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
