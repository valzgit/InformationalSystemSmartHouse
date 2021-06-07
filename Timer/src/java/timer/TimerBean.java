/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timer;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author stubic
 */


public class TimerBean extends TimerTask{
 
    int songnumber = 1;

    public TimerBean(int songnumber) {
        if(songnumber<1 || songnumber>3){
            this.songnumber = 3;
        }
        else {
            this.songnumber = songnumber;
        }
        
    }
    
    @Override
    public void run() {
       
            File musicPath = new File(songnumber+".wav");
            if(musicPath.exists()){
                AudioInputStream audioInput = null;
                try {
                    audioInput = AudioSystem.getAudioInputStream(musicPath);
                    Clip clip =  AudioSystem.getClip();    
                    clip.open(audioInput);
                    clip.start();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(TimerBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TimerBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(TimerBean.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        audioInput.close();
                    } catch (IOException ex) {
                        Logger.getLogger(TimerBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else{
                System.out.println("Nepoznata pesma ili ne moze da pronadje fajl");
            }
        
        
        
    }
    
 
}
