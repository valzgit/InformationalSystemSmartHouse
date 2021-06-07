package planer;

import entities.Korisnik;
import entities.Pesma;
import entities.Planer;
import entities.Slusao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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

    @Resource(lookup = "jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
    @Resource(lookup = "MyTopic")
    static Topic topic;
    @Resource(lookup = "QueuePlaner")
    static Queue queue;
    @Resource(lookup = "QueueCreatePlan")
    static Queue queuePlan;

    private static String key = "Ahy9tK5-GRaXp6VTZQ2b0c1_ACwSkL42MtYUm5Qn_T2XhqNN3L3EZj1Z0o9QsvVA";
    private static HttpURLConnection connection;

    public static void main(String[] args) throws JMSException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlanerPU");
        EntityManager em = emf.createEntityManager();
        JMSContext context = connectionFactory.createContext();
        context.setClientID("planer");
        JMSConsumer consumer = context.createConsumer(topic, "to = 'planer'", true);
        JMSProducer producer = context.createProducer();

        while (true) {
            System.out.println("Usao u while");
            Message m = consumer.receive();
            System.out.println("Primio zahtev od servera ");
            if (m instanceof ObjectMessage) {
                ObjectMessage obj = (ObjectMessage) m;
                String operation = obj.getStringProperty("operation");
                if (operation.equals("distanca")) {
                    System.out.println("Distance calculation:");
                    Koordinate k1 = getLocation(obj.getStringProperty("from"));
                    Koordinate k2 = getLocation(obj.getStringProperty("too"));
                    if (k1 == null || k2 == null) {
                        System.out.println("Pogresno unet grad ili nije moguce izracunati distancu");
                        ObjectMessage obj2 = context.createObjectMessage();
                        obj2.setStringProperty("vreme", "0");
                        producer.send(queue, obj2);
                    } else {
                        double time = getTime(k1.getK1(), k1.getK2(), k2.getK1(), k2.getK2());
                        ObjectMessage obj2 = context.createObjectMessage();
                        obj2.setStringProperty("vreme", "" + time);
                        System.out.println("Poslao vreme = " + time);
                        producer.send(queue, obj2);
                    }
                } 
                else if (operation.equals("obrisi")){
                    System.out.println("=======Obrisi plan");
                    System.out.println("Poslat ID = "+ obj.getIntProperty("deleteid"));
                    Planer plan = em.find(Planer.class,obj.getIntProperty("deleteid"));
                    if(plan!=null){
                        em.getTransaction().begin();
                        em.remove(plan);
                        em.getTransaction().commit();
                        System.out.println("Uspesno obrisan plan");
                    }
                    else System.out.println("Plan sa datim ID-JEM ne postoji");
                    
                }
                else if (operation.equals("kreiraj")) {
                    System.out.println("=======================Distance calculation:");
                    Korisnik k = em.createNamedQuery("Korisnik.findByIdkorisnik", Korisnik.class).setParameter("idkorisnik", obj.getIntProperty("who")).getSingleResult();
                    //List<Planer> planerList = em.createNamedQuery("Planer.findByIdget",Planer.class).setParameter("idget", k.getIdkorisnik()).getResultList();
                    List<Planer> planerList = em.createQuery("SELECT p FROM Planer p WHERE p.idget = :idget ORDER BY p.kraj",Planer.class).setParameter("idget", k.getIdkorisnik()).getResultList();
                    Koordinate k2 = getLocation(obj.getStringProperty("too"));
                    Date sada = new Date();
                    int hours = Integer.parseInt(obj.getStringProperty("hours"));
                    System.out.println("Hours to there " + hours);
                    int minutes = Integer.parseInt(obj.getStringProperty("minutes"));
                    System.out.println("Minutes to there " + minutes);
                    int trajanje = Integer.parseInt(obj.getStringProperty("trajanje"));
                    long timeToThere = hours * 60 + minutes;
                    String messageToSend = "";
                    Date pocetak = new Date((long) (sada.getTime() + timeToThere * 60 * 1000));
                    Date kraj = new Date((long) (pocetak.getTime() + trajanje * 60 * 1000));
                    int nasao = -1;
                    for (int i = planerList.size() - 1; i >= 0; --i) {
                        System.out.println("Usao u for iteraciju " + planerList.get(i).getKraj() + " - - - " + pocetak);
                        System.out.println(planerList.get(i).getKraj().getTime() < pocetak.getTime());
                        if ((planerList.get(i).getKraj().getTime() < pocetak.getTime())) {
                            nasao = i;
                            break;
                        }
                    }
                    Koordinate k1;
                    if (nasao != -1) {
                        sada = planerList.get(nasao).getKraj();
                        k1 = getLocation(planerList.get(nasao).getDestinacija());
                        long allMinutes = (planerList.get(nasao).getKraj().getTime() - (new Date()).getTime()) / (1000 * 60);
                        minutes = minutes - (int) (allMinutes % 60);
                        hours = hours - (int) (allMinutes / 60);
                        timeToThere = hours * 60 + minutes;
                    } else {
                        k1 = getLocation(obj.getStringProperty("from"));
                    }
                    if (k1 == null || k2 == null) {
                        System.out.println("Pogresno unet grad ili nije moguce izracunati distancu");
                        ObjectMessage obj2 = context.createObjectMessage();
                        obj2.setStringProperty("vreme", "Neuspesno kreiran plan [Mesto destinacije ne postoji]");
                        producer.send(queuePlan, obj2);
                    } 
                    else {
                    double time = getTime(k1.getK1(), k1.getK2(), k2.getK1(), k2.getK2());
                    if (timeToThere < time) {
                        messageToSend = "Ne moze da stigne na obavezu!";
                    } 
                    else {
                        System.out.println("ULAZI U FOR ZA " + planerList.size() + " ELEMENATA");
                        for (Planer planer : planerList) {
                            System.out.println("Usao u for iteraciju " + planer.getPocetak() + " - " + planer.getKraj() + " - - - " + pocetak + " - " + kraj);
                            if (planer.getPocetak().getTime() > sada.getTime()) {
                                //System.out.println("Usao u prvi if ");
                                if ((planer.getPocetak().getTime() >= pocetak.getTime()) && (planer.getPocetak().getTime() <= kraj.getTime())) {
                                    messageToSend = "1. Postoji obaveza sa kojom se ovo preklapa u gradu " + planer.getDestinacija();
                                    break;
                                } else if ((planer.getKraj().getTime() <= kraj.getTime()) && (planer.getKraj().getTime() >= pocetak.getTime())) {
                                    messageToSend = "2. Postoji obaveza sa kojom se ovo preklapa u gradu " + planer.getDestinacija();
                                    break;
                                } else if ((planer.getPocetak().getTime() <= pocetak.getTime()) && (planer.getKraj().getTime() >= kraj.getTime())) {
                                    messageToSend = "3. Postoji obaveza sa kojom se ovo preklapa u gradu " + planer.getDestinacija();
                                    break;
                                }
                            }
                        }
                        if ("".equals(messageToSend)) {
                            Planer plan = new Planer();
                            plan.setIdKorisnika(k);
                            System.out.println("idK:" + k.getIdkorisnik());
                            plan.setPocetak(pocetak);
                            System.out.println("pocetak:" + pocetak);
                            plan.setKraj(kraj);
                            System.out.println("kraj:" + kraj);
                            plan.setDestinacija(obj.getStringProperty("too"));
                            plan.setIdget(k.getIdkorisnik());
                            System.out.println(obj.getStringProperty("too"));
                            em.getTransaction().begin();
                            em.persist(plan);
                            em.getTransaction().commit();
                            ObjectMessage txt = context.createObjectMessage();
                            try {
                                txt.setStringProperty("to", "timer");
                                txt.setIntProperty("who", k.getIdkorisnik());
                                //System.out.print("Za ovoliko milisekundi tajmer " + pocetak.getTime() +" " + time*60*1000 +" " + sada.getTime());
                                sada = new Date();
                                txt.setLongProperty("when", (long) (pocetak.getTime() - time * 60 * 1000 - sada.getTime()));
                                txt.setStringProperty("operation", "single");
                                txt.setIntProperty("zvuk", 1);
                                producer.send(topic, txt);
                                messageToSend = "Uspesno je kreirao plan!";
                            } catch (JMSException ex) {
                                messageToSend = "Doslo je do neocekivane greske!";
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    ObjectMessage obj2 = context.createObjectMessage();
                    obj2.setStringProperty("vreme", messageToSend);
                    System.out.println("Poslao poruku = " + messageToSend);
                    producer.send(queuePlan, obj2);
                    k = null;
                    }
                }
            }
            //producer.send(topic, "Zar dama tako da prica ");  
        }
    }

    public static double getTime(double k1, double k2, double k3, double k4) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            URL url = new URL("http://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?origins=" + k1 + "," + k2 + "&destinations=" + k3 + "," + k4 + "&travelMode=driving&startTime=2017-06-15T13:00:00-07:00&key=" + key);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            String response = responseContent.toString();
            if (response.contains("travelDuration")) {
                String substring = response.substring(response.indexOf("travelDuration") + 16, response.indexOf("travelDuration") + 23);
                substring = substring.replace("}", "");
                substring = substring.replace("]", "");
                double time = Double.parseDouble(substring);
                System.out.println(time);
                return time;
            } else {
                System.out.println("Ne postoji dati grad");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public static Koordinate getLocation(String mesto) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            URL url = new URL("http://dev.virtualearth.net/REST/v1/Locations?locality=" + mesto + "&maxResults=1&key=" + key);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            String response = responseContent.toString();
            if (response.contains("coordinates")) {
                String substring = response.substring(response.indexOf("coordinates") + 14, response.indexOf("coordinates") + 51);
                System.out.println(substring);
                String[] split = substring.split(",");
                split[0] = split[0].replace("}", "");
                split[0] = split[0].replace("]", "");
                split[1] = split[1].replace("}", "");
                split[1] = split[1].replace("]", "");
                double k1 = Double.parseDouble(split[0]);
                double k2 = Double.parseDouble(split[1]);
                System.out.println(k1 + "  " + k2);
                return new Koordinate(k1, k2);
            } else {
                System.out.println("Ne postoji dati grad");
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
