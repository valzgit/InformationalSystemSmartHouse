/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisnickiuredjaj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class KorisnickiUredjaj {

    /**
     * @param args the command line arguments
     */
    
    private static HttpURLConnection connection;
    public static String username = "admin";
    public static String password = "1234";
    
    public static void main(String[] args) {
        //pustiPesmu("Toxic");
        //dohvatiPustanePesme();
        oldAlarms();
        new GUI();
    }
    
    private static void fetchPustanePesme(){
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/zvuk/logs");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            System.out.println(responseContent.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     public static List<String> dohvatiPustanePesme(){
         fetchPustanePesme();
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/zvuk/getlogs");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            String response = responseContent.toString();
            response = response.replace("[", "");
            response = response.replace("]", "");
            response = response.replace("\"", "");
            ArrayList<String> myList = new ArrayList<String>(Arrays.asList(response.split(",")));
            
            return myList;
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
     
    public static void pustiPesmu(String ime){
         BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/zvuk/play/" + ime);
            connection = (HttpURLConnection)url.openConnection();
            
            connection.setRequestMethod("POST");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            System.out.println(responseContent.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void navijAlarm(long zakoliko,int muzika){
         BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/alarm/single/" + zakoliko+"/" + muzika);
            connection = (HttpURLConnection)url.openConnection();
            
            connection.setRequestMethod("POST");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            System.out.println(responseContent.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
            
        public static void oldAlarms(){
         BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/alarm/oldalarms");
            connection = (HttpURLConnection)url.openConnection();
            
            connection.setRequestMethod("POST");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            System.out.println(responseContent.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
        public static void navijPeriodicniAlarm(long zakoliko,int muzika,long period){
         BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/alarm/periodic/" + zakoliko+"/" + muzika+"/"+period);
            connection = (HttpURLConnection)url.openConnection();
            
            connection.setRequestMethod("POST");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            System.out.println(responseContent.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private static void fetchDistance(String from,String to){
    BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/planer/distanceTime/"+from+"/"+to);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }          
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void kreirajPlanFetch(String to, int hours, int minutes, int length){
    BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/planer/create/"+to+"/"+hours+"/"+minutes+"/"+length);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }          
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String distance(String from, String to) {
            fetchDistance(from,to);
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/planer/distanceTimeGet");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            String response = responseContent.toString();           
            return response;
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    static String kreirajPlan(String to, int hours, int minutes, int length) {
        kreirajPlanFetch(to,hours,minutes,length);
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/planer/getCreation");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            String response = responseContent.toString();           
            return response;
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    static List<String> dohvatiPlanove() {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            URL url = new URL("http://localhost:8080/WebApp/api/planer/myPlans");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            String response = responseContent.toString();
            //System.out.println(response);
            response = response.replace("[", "");
            response = response.replace("]", "");
            response = response.replace("\"", "");
            ArrayList<String> myList = new ArrayList<String>(Arrays.asList(response.split(",")));
            
            return myList;
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    static void obrisiPlan(int selectedId) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            // TODO code application logic here
            System.out.println("Selected id = " + selectedId);
            URL url = new URL("http://localhost:8080/WebApp/api/planer/deleteCreation/"+selectedId);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");          
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
            int status = connection.getResponseCode();
            System.out.println(status);
            System.out.println(connection.getResponseMessage());
            
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            String response = responseContent.toString();  
            System.out.println(response);
        } catch (MalformedURLException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnickiUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
