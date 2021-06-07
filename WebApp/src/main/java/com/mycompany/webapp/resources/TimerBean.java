/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapp.resources;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author stubic
 */
//@Stateless
//@Path("timer")
public class TimerBean {
    
//    @Resource
//    private TimerService timerService;
//    
//    //Kreiranje automatskog timera
//    //Okida na svakih 5 sekundi
////    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
////    public void timer(){
////        System.out.println("Okinuo je automatski timerrrr");
////    }
//    
//    @POST
//    //Kreiranje programabilnog timera
//    //Kada okine poziva metodu anotiranu sa @Timeout
//    public void createTimer(){
//        //Kreiranje timera koji jednom okida
//        timerService.createSingleActionTimer(12000, new TimerConfig());
//        //Kreiranje timera koji periodicno okida
//        timerService.createTimer(12000, 5000, "Kreiran periodicni timer");
//    }
//    
//    @Timeout
//    public void timeout(Timer timer) {
//        System.out.println("Okinuo je programabilni timer ");
//    }
}
