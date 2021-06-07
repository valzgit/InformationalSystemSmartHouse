/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "alarm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alarm.findAll", query = "SELECT a FROM Alarm a"),
    @NamedQuery(name = "Alarm.findByIdalarm", query = "SELECT a FROM Alarm a WHERE a.idalarm = :idalarm"),
    @NamedQuery(name = "Alarm.findByVreme", query = "SELECT a FROM Alarm a WHERE a.vreme = :vreme"),
    @NamedQuery(name = "Alarm.findByPeriodican", query = "SELECT a FROM Alarm a WHERE a.periodican = :periodican"),
    @NamedQuery(name = "Alarm.findByPesma", query = "SELECT a FROM Alarm a WHERE a.pesma = :pesma")})
public class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idalarm")
    private Integer idalarm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodican")
    private int periodican;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pesma")
    private int pesma;
    @JoinColumn(name = "idKorisnik", referencedColumnName = "idkorisnik")
    @ManyToOne(optional = false)
    private Korisnik idKorisnik;

    public Alarm() {
    }

    public Alarm(Integer idalarm) {
        this.idalarm = idalarm;
    }

    public Alarm(Integer idalarm, Date vreme, int periodican, int pesma) {
        this.idalarm = idalarm;
        this.vreme = vreme;
        this.periodican = periodican;
        this.pesma = pesma;
    }

    public Integer getIdalarm() {
        return idalarm;
    }

    public void setIdalarm(Integer idalarm) {
        this.idalarm = idalarm;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public int getPeriodican() {
        return periodican;
    }

    public void setPeriodican(int periodican) {
        this.periodican = periodican;
    }

    public int getPesma() {
        return pesma;
    }

    public void setPesma(int pesma) {
        this.pesma = pesma;
    }

    public Korisnik getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Korisnik idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalarm != null ? idalarm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alarm)) {
            return false;
        }
        Alarm other = (Alarm) object;
        if ((this.idalarm == null && other.idalarm != null) || (this.idalarm != null && !this.idalarm.equals(other.idalarm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Alarm[ idalarm=" + idalarm + " ]";
    }
    
}
