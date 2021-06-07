package zvuk;

import entities.Korisnik;
import entities.Pesma;
import entities.Slusao;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    @Resource(lookup ="jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
    @Resource(lookup = "MyTopic")
    static Topic topic;
    @Resource(lookup = "QueueZvuk")
    static Queue queue;
             
    public static void main(String[] args) throws JMSException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ZvukPU");
        EntityManager em = emf.createEntityManager();
        JMSContext context = connectionFactory.createContext();
        context.setClientID("zvuk");
        JMSConsumer consumer = context.createConsumer(topic,"to = 'uzrz'",true);
        JMSProducer producer = context.createProducer();

        while(true){
            System.out.println("Usao u while");
            Message m = consumer.receive();
            System.out.println("Primio zahtev od servera ");
            if(m instanceof ObjectMessage){
                ObjectMessage obj = (ObjectMessage)m;
                String operation = obj.getStringProperty("operation");
                if(operation.equals("logs")){
                    System.out.println("Operation logs:");
                    int idK = obj.getIntProperty("who");
                    List<Slusao> resultList = em.createQuery("SELECT p FROM Slusao p",Slusao.class).getResultList();
                    List<String> songNames = new LinkedList<>();
                    for(int i=0;i<resultList.size();++i){
                        if(resultList.get(i).getIdKorisnik().getIdkorisnik()==idK)
                        songNames.add(resultList.get(i).getIdPesma().getImePesme());
                    }
                    ObjectMessage response = context.createObjectMessage();
                    response.setObject((Serializable) songNames);
                    producer.send(queue, response);
                    System.out.println("Uspesno poslao odgovor serveru!");
                }
                else{
                    try {
                        System.out.println("Play music:");
                        List<Pesma> resultList = em.createNamedQuery("Pesma.findByImePesme",Pesma.class).setParameter("imePesme",obj.getStringProperty("ime")).getResultList();
                        Pesma p = !resultList.isEmpty() ? resultList.get(0) : null;
                        if(p!=null){
                            String URL = p.getUrl();
                            Slusao slusao = new Slusao();
                            Korisnik k = em.createNamedQuery("Korisnik.findByIdkorisnik",Korisnik.class).setParameter("idkorisnik", obj.getIntProperty("who")).getSingleResult();
                            slusao.setIdKorisnik(k);
                            slusao.setIdPesma(p);
                            em.getTransaction().begin();
                            em.persist(slusao);
                            em.getTransaction().commit();
                            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
                        }            
                        else{
                            String URL = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstleyVEVO";
                            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }  
        }
        
    }
    
}
