/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIdkorisnik", query = "SELECT k FROM Korisnik k WHERE k.idkorisnik = :idkorisnik"),
    @NamedQuery(name = "Korisnik.findByUsername", query = "SELECT k FROM Korisnik k WHERE k.username = :username"),
    @NamedQuery(name = "Korisnik.findByPassword", query = "SELECT k FROM Korisnik k WHERE k.password = :password"),
    @NamedQuery(name = "Korisnik.findByKuca", query = "SELECT k FROM Korisnik k WHERE k.kuca = :kuca")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idkorisnik")
    private Integer idkorisnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "kuca")
    private String kuca;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKorisnik")
    private List<Slusao> slusaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKorisnik")
    private List<Alarm> alarmList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKorisnika")
    private List<Planer> planerList;

    public Korisnik() {
    }

    public Korisnik(Integer idkorisnik) {
        this.idkorisnik = idkorisnik;
    }

    public Korisnik(Integer idkorisnik, String username, String password, String kuca) {
        this.idkorisnik = idkorisnik;
        this.username = username;
        this.password = password;
        this.kuca = kuca;
    }

    public Integer getIdkorisnik() {
        return idkorisnik;
    }

    public void setIdkorisnik(Integer idkorisnik) {
        this.idkorisnik = idkorisnik;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKuca() {
        return kuca;
    }

    public void setKuca(String kuca) {
        this.kuca = kuca;
    }

    @XmlTransient
    public List<Slusao> getSlusaoList() {
        return slusaoList;
    }

    public void setSlusaoList(List<Slusao> slusaoList) {
        this.slusaoList = slusaoList;
    }

    @XmlTransient
    public List<Alarm> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }

    @XmlTransient
    public List<Planer> getPlanerList() {
        return planerList;
    }

    public void setPlanerList(List<Planer> planerList) {
        this.planerList = planerList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idkorisnik != null ? idkorisnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.idkorisnik == null && other.idkorisnik != null) || (this.idkorisnik != null && !this.idkorisnik.equals(other.idkorisnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korisnik[ idkorisnik=" + idkorisnik + " ]";
    }
    
}
