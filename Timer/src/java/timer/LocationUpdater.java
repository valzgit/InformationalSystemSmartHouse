/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timer;

import java.util.TimerTask;

/**
 *
 * @author PC
 */
public class LocationUpdater extends TimerTask{
    String destinacija;
    int idKorisnik;
    public LocationUpdater(String destinacija, int idKorisnik) {
        this.destinacija = destinacija;
        this.idKorisnik = idKorisnik;
    }

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(String destinacija) {
        this.destinacija = destinacija;
    }
    
    
    @Override
    public void run() {
//        Main.updateLocation(destinacija,idKorisnik);
    }
    
}
