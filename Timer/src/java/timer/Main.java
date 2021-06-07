package timer;

import entities.Alarm;
import entities.Korisnik;
import entities.Planer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
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
    @Resource(lookup = "QueueTimer")
    static Queue queue;
             
    static final List<Alarm> alarmi = new ArrayList<Alarm>();
    
    public static void main(String[] args) throws JMSException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimerPU");
        EntityManager em = emf.createEntityManager();
        JMSContext context = connectionFactory.createContext();
        context.setClientID("timer");
        JMSConsumer consumer = context.createConsumer(topic,"to = 'timer'",true);
        JMSProducer producer = context.createProducer();
        
        
        while(true){
            System.out.println("Usao u while");
            Message m = consumer.receive();
            System.out.println("Primio zahtev od servera ");
            if(m instanceof ObjectMessage){
                ObjectMessage obj = (ObjectMessage)m;
                String operation = obj.getStringProperty("operation");
                if(operation.equals("single")){
                    System.out.println("Single alarm:");
                    long when = obj.getLongProperty("when");
                    Date sada = new Date();
                    Date kada = new Date(sada.getTime() + when);
                    Alarm alarm = new Alarm();
                    Korisnik k = em.createNamedQuery("Korisnik.findByIdkorisnik",Korisnik.class).setParameter("idkorisnik", obj.getIntProperty("who")).getSingleResult();
                    alarm.setVreme(kada);
                    alarm.setIdKorisnik(k);
                    alarm.setPeriodican(0);
                    if(obj.getIntProperty("zvuk")<1 || obj.getIntProperty("zvuk")>3){
                        alarm.setPesma(3);
                    }
                    else{
                        alarm.setPesma(obj.getIntProperty("zvuk"));
                    }
                    em.getTransaction().begin();
                    em.persist(alarm);
                    em.getTransaction().commit();
                    scheduleTimer(alarm);
                }
                else if(operation.equals("periodic")){
                   System.out.println("Periodic alarm:");
                    long when = obj.getLongProperty("when");
                    Date sada = new Date();
                    Date kada = new Date(sada.getTime() + when);
                    Alarm alarm = new Alarm();
                    Korisnik k = em.createNamedQuery("Korisnik.findByIdkorisnik",Korisnik.class).setParameter("idkorisnik", obj.getIntProperty("who")).getSingleResult();
                    alarm.setVreme(kada);
                    alarm.setIdKorisnik(k);
                    alarm.setPeriodican(obj.getIntProperty("period"));
                    if(obj.getIntProperty("zvuk")<1 || obj.getIntProperty("zvuk")>3){
                        alarm.setPesma(3);
                    }
                    else{
                        alarm.setPesma(obj.getIntProperty("zvuk"));
                    }
                    em.getTransaction().begin();
                    em.persist(alarm);
                    em.getTransaction().commit();
                    schedulePeriodicTimer(alarm);
                }
                else if(operation.equals("wakeup")){
                    System.out.println("Stari alarmi:");
                    Korisnik k = em.createNamedQuery("Korisnik.findByIdkorisnik",Korisnik.class).setParameter("idkorisnik", obj.getIntProperty("who")).getSingleResult();
                    List<Alarm> alarmList = k.getAlarmList();
                    Date sada = new Date();
                    System.out.println("Broj starih alarma = "+ ((alarmList!=null) ? alarmList.size(): "0"));
                    for (Alarm alarm : alarmList) {
                        if(alarm.getVreme().getTime()>sada.getTime()){
                            if(alarm.getPeriodican()==0)scheduleTimer(alarm);
                            else schedulePeriodicTimer(alarm);
                        }
                    }
                }
            }  
        }
        
    }
//    public static void updateLocation(String destinacija,int idKorisnik){
//        EntityManager em = Persistence.createEntityManagerFactory("TimerPU").createEntityManager();
//        Korisnik k = em.createNamedQuery("Korisnik.findByIdkorisnik",Korisnik.class).setParameter("idkorisnik",idKorisnik ).getSingleResult();
//        k.setLokacija(destinacija);
//        em.persist(k);
//    }
    static void schedulePeriodicTimer(Alarm alarm){
        for (Alarm alarmInst : alarmi) {
            if(Objects.equals(alarmInst.getIdalarm(), alarm.getIdalarm())){
                System.out.println("Alarm ce vec biti pusten");
                return;
            }
        }
        alarmi.add(alarm);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerBean(alarm.getPesma()), alarm.getVreme(), alarm.getPeriodican());
       
    }
    static void scheduleTimer(Alarm alarm) {
        for (Alarm alarmInst : alarmi) {
            if(Objects.equals(alarmInst.getIdalarm(), alarm.getIdalarm())){
                System.out.println("Alarm ce vec biti pusten");
                return;
            }
        }
        alarmi.add(alarm);
        Timer timer = new Timer(true);
        System.out.println("Vreme single tajmera je " + alarm.getVreme());
        timer.schedule(new TimerBean(alarm.getPesma()), alarm.getVreme());       
    }
    
}
