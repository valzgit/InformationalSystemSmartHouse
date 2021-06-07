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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "planer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planer.findAll", query = "SELECT p FROM Planer p"),
    @NamedQuery(name = "Planer.findByIdplaner", query = "SELECT p FROM Planer p WHERE p.idplaner = :idplaner"),
    @NamedQuery(name = "Planer.findByPocetak", query = "SELECT p FROM Planer p WHERE p.pocetak = :pocetak"),
    @NamedQuery(name = "Planer.findByKraj", query = "SELECT p FROM Planer p WHERE p.kraj = :kraj"),
    @NamedQuery(name = "Planer.findByDestinacija", query = "SELECT p FROM Planer p WHERE p.destinacija = :destinacija")})
public class Planer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idplaner")
    private Integer idplaner;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pocetak")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pocetak;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kraj")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kraj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "destinacija")
    private String destinacija;
    @JoinColumn(name = "idKorisnika", referencedColumnName = "idkorisnik")
    @ManyToOne(optional = false)
    private Korisnik idKorisnika;

    public Planer() {
    }

    public Planer(Integer idplaner) {
        this.idplaner = idplaner;
    }

    public Planer(Integer idplaner, Date pocetak, Date kraj, String destinacija) {
        this.idplaner = idplaner;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.destinacija = destinacija;
    }

    public Integer getIdplaner() {
        return idplaner;
    }

    public void setIdplaner(Integer idplaner) {
        this.idplaner = idplaner;
    }

    public Date getPocetak() {
        return pocetak;
    }

    public void setPocetak(Date pocetak) {
        this.pocetak = pocetak;
    }

    public Date getKraj() {
        return kraj;
    }

    public void setKraj(Date kraj) {
        this.kraj = kraj;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(String destinacija) {
        this.destinacija = destinacija;
    }

    public Korisnik getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Korisnik idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idplaner != null ? idplaner.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planer)) {
            return false;
        }
        Planer other = (Planer) object;
        if ((this.idplaner == null && other.idplaner != null) || (this.idplaner != null && !this.idplaner.equals(other.idplaner))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Planer[ idplaner=" + idplaner + " ]";
    }
    
}
