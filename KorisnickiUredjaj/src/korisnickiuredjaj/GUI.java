/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisnickiuredjaj;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author PC
 */
public class GUI implements ActionListener{
    JLabel label = new JLabel("Search song:");
    JTextArea tarea = new JTextArea(5,30);
    JTextArea tareaPlanovi = new JTextArea(10,50);
    JTextField txtfield = new JTextField(20);
    JButton button = new JButton("Song History");
    JButton userbutton = new JButton("USER/PASS");
    JButton buttonSearch = new JButton("Search");
    JTextField username = new JTextField("admin");
    JTextField password = new JTextField("1234");
    JLabel songtimelabel = new JLabel("Trigger alarm time:");
    JTextField songtime = new JTextField("5000");
    JLabel songtimelabelp = new JLabel("Trigger alarm period:");
    JTextField songtimep = new JTextField("0000");
    JLabel songtimelabels = new JLabel("Alarm song: [1 or 2 or 3]");
    JTextField songtimes = new JTextField("1");
    JButton buttonAlarm = new JButton("Alarm it!");
    JButton buttonAlarm2 = new JButton("Alarm from list!");
    JLabel timelabel = new JLabel("(Time [ms])");
    JTextField from = new JTextField("Beograd");
    JTextField to = new JTextField("Kraljevo");
    JButton dugmedistanca = new JButton("Vreme");
    JLabel potrebnoVreme = new JLabel("0 minutes");
    JLabel newplanLabel = new JLabel("New plan:");
    JTextField destinacijaPlan = new JTextField("Kraljevo");
    JTextField startTime = new JTextField("time(in hours)");
    JTextField startTimeMin = new JTextField("time(in minutes)");
    JTextField length = new JTextField("length(in minutes)");
    JLabel timeMin = new JLabel("min");
    JButton planButton = new JButton("Create");
    JLabel poruka = new JLabel("");
    JButton planoviBatn = new JButton("Listaj");
    JButton deleteBatn = new JButton("Obrisi izabrani plan");
    private JComboBox boxOfIDs = new JComboBox();
    private String[] optionalTimes = {"1000","2000","3000","4000","5000","6000","7000","8000","9000","10000"};
    private JComboBox boxOfTimes = new JComboBox();
    int selectedTime = 1000;
    int selectedId = 0;
    public GUI(){
        for(int i = 0 ; i<optionalTimes.length;++i){
            boxOfTimes.addItem(optionalTimes[i]);
        }
        boxOfTimes.addActionListener(this);
        JFrame frame = new JFrame();
        button.setSize(10, 10);
        button.addActionListener(this);
        JScrollPane scrollPane = new JScrollPane(tarea);
        JScrollPane scrollPane2 = new JScrollPane(tareaPlanovi);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridLayout(4,1));
        mainpanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        JPanel panel = new JPanel();
        panel.add(username);
        panel.add(password);
        panel.add(label);
        panel.add(txtfield);
        
        
        buttonSearch.setSize(5, 5);
        buttonSearch.addActionListener(this);
        buttonAlarm.setSize(10,10);
        buttonAlarm.addActionListener(this);
        buttonAlarm2.setSize(10,10);
        buttonAlarm2.addActionListener(this);
        panel.add(buttonSearch);
        panel.add(button);
        panel.add(scrollPane);
        
        JPanel panel2 = new JPanel();
        userbutton.setSize(10,10);
        userbutton.addActionListener(this);
        panel2.add(userbutton);
        panel2.add(timelabel);
        panel2.add(songtimelabel);
        panel2.add(songtime);
        panel2.add(songtimelabelp);
        panel2.add(songtimep);
        panel2.add(songtimelabels);
        panel2.add(songtimes);
        panel2.add(buttonAlarm);
        panel2.add(boxOfTimes);
        panel2.add(buttonAlarm2);
        
        JPanel panel3 = new JPanel();
        panel3.add(from);
        panel3.add(to);
        dugmedistanca.setSize(10,10);
        dugmedistanca.addActionListener(this);
        planButton.setSize(10,10);
        planButton.addActionListener(this);
        panel3.add(dugmedistanca);
        panel3.add(potrebnoVreme);
        panel3.add(newplanLabel);
        panel3.add(destinacijaPlan);
        panel3.add(startTime);
        panel3.add(startTimeMin);
        panel3.add(length);
        panel3.add(timeMin);
        panel3.add(planButton);
        panel3.add(poruka);
        
        JPanel panel4 = new JPanel();
        planoviBatn.setSize(10,10);
        planoviBatn.addActionListener(this);
        panel4.add(planoviBatn);
        panel4.add(scrollPane2);
        boxOfIDs.addActionListener(this);
        panel4.add(boxOfIDs);
        deleteBatn.setSize(10,10);
        deleteBatn.addActionListener(this);
        panel4.add(deleteBatn);
        
        mainpanel.add(panel);
        mainpanel.add(panel2);
        mainpanel.add(panel3);
        mainpanel.add(panel4);
        
        frame.add(mainpanel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Korisnicki Uredjaj");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        KorisnickiUredjaj.username = username.getText();
        KorisnickiUredjaj.password = password.getText();
        if(e.getSource()==button){
            List<String> lista = KorisnickiUredjaj.dohvatiPustanePesme();
            StringBuilder sb = new StringBuilder();
            int num = 1;
            if(lista!=null){
                for(int i = lista.size()-1;i>=0;--i){
                   sb.append(num).append(". ").append(lista.get(i));        
                   sb.append("\n");
                   ++num;
                }
            }
            tarea.setText("Song history [from latest to oldest]: \n" + sb.toString());
        }
        else if(e.getSource()==buttonSearch){
            KorisnickiUredjaj.username = username.getText();
            KorisnickiUredjaj.password = password.getText();
            if("".equals(txtfield.getText())){System.out.println("Unesite pesmu!");return;}
            KorisnickiUredjaj.pustiPesmu(txtfield.getText());
        }
        else if(e.getSource()==buttonAlarm){
             KorisnickiUredjaj.username = username.getText();
             KorisnickiUredjaj.password = password.getText();
            if("".equals(songtime.getText()) || "".equals(songtimes.getText()) || "".equals(songtimep.getText())){
                System.out.println("Unesite vreme ili period ili tip pesme");return;
            }
            long time = Long.parseLong(songtime.getText());
            int sound = Integer.parseInt(songtimes.getText());
            long period = Long.parseLong(songtimep.getText());
            if(period>0){
                KorisnickiUredjaj.navijPeriodicniAlarm(time, sound, period);
            }
            else{
                KorisnickiUredjaj.navijAlarm(time,sound);
            }
        }
        else if(e.getSource()==boxOfTimes){
            KorisnickiUredjaj.username = username.getText();
            KorisnickiUredjaj.password = password.getText();
            selectedTime = Integer.parseInt((String)((JComboBox)e.getSource()).getSelectedItem());
        }
        else if (e.getSource()==buttonAlarm2){
            KorisnickiUredjaj.username = username.getText();
            KorisnickiUredjaj.password = password.getText();
            int sound = Integer.parseInt(songtimes.getText());
            KorisnickiUredjaj.navijAlarm(selectedTime,sound);
        }
        else if (e.getSource()==userbutton){
            KorisnickiUredjaj.username = username.getText();
            KorisnickiUredjaj.password = password.getText();
            KorisnickiUredjaj.oldAlarms();
        }
        else if (e.getSource()==dugmedistanca){
            KorisnickiUredjaj.username = username.getText();
            KorisnickiUredjaj.password = password.getText();
            String distanceTime = KorisnickiUredjaj.distance(from.getText(),to.getText());
            distanceTime += " min";
            potrebnoVreme.setText(distanceTime);
        }
        else if (e.getSource()==planButton){
            KorisnickiUredjaj.username = username.getText();
            KorisnickiUredjaj.password = password.getText();
            String text = KorisnickiUredjaj.kreirajPlan(destinacijaPlan.getText(),Integer.parseInt(startTime.getText()),Integer.parseInt(startTimeMin.getText()),Integer.parseInt(length.getText()));
            System.out.println(text);
            poruka.setText(text);
        }
        else if (e.getSource()==planoviBatn){
            boxOfIDs.removeAllItems();
            KorisnickiUredjaj.username = username.getText();
            KorisnickiUredjaj.password = password.getText();
            List<String> lista = KorisnickiUredjaj.dohvatiPlanove();
            StringBuilder sb = new StringBuilder();
            int num = 1;           
            if(lista!=null){
                for(int i = 0;i<lista.size();i++){
                   String s = lista.get(i);
                   s = s.substring(s.indexOf("ID:")+3,s.length());
                   boxOfIDs.addItem(Integer.parseInt(s));
                   sb.append(num).append(". ").append(lista.get(i));        
                   sb.append("\n");
                   ++num;
                }         
            }
            tareaPlanovi.setText("Plans history: \n" + sb.toString());
        }
        else if (e.getSource()==boxOfIDs){
            KorisnickiUredjaj.username = username.getText();
            KorisnickiUredjaj.password = password.getText();
            if(boxOfIDs!=null && boxOfIDs.getSelectedItem()!=null){
                System.out.println("IZABRAN JE = " + boxOfIDs.getSelectedItem().toString());
                selectedId = (int)(boxOfIDs.getSelectedItem());
            }
        }
        else if (e.getSource()==deleteBatn){
            KorisnickiUredjaj.username = username.getText();
            KorisnickiUredjaj.password = password.getText();
            KorisnickiUredjaj.obrisiPlan(selectedId);
        }
    }
}
