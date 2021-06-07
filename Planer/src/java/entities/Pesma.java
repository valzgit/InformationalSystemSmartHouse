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
@Table(name = "pesma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pesma.findAll", query = "SELECT p FROM Pesma p"),
    @NamedQuery(name = "Pesma.findByIdpesma", query = "SELECT p FROM Pesma p WHERE p.idpesma = :idpesma"),
    @NamedQuery(name = "Pesma.findByImePesme", query = "SELECT p FROM Pesma p WHERE p.imePesme = :imePesme"),
    @NamedQuery(name = "Pesma.findByUrl", query = "SELECT p FROM Pesma p WHERE p.url = :url")})
public class Pesma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpesma")
    private Integer idpesma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ImePesme")
    private String imePesme;
    @Size(max = 200)
    @Column(name = "url")
    private String url;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPesma")
    private List<Slusao> slusaoList;

    public Pesma() {
    }

    public Pesma(Integer idpesma) {
        this.idpesma = idpesma;
    }

    public Pesma(Integer idpesma, String imePesme) {
        this.idpesma = idpesma;
        this.imePesme = imePesme;
    }

    public Integer getIdpesma() {
        return idpesma;
    }

    public void setIdpesma(Integer idpesma) {
        this.idpesma = idpesma;
    }

    public String getImePesme() {
        return imePesme;
    }

    public void setImePesme(String imePesme) {
        this.imePesme = imePesme;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public List<Slusao> getSlusaoList() {
        return slusaoList;
    }

    public void setSlusaoList(List<Slusao> slusaoList) {
        this.slusaoList = slusaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpesma != null ? idpesma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pesma)) {
            return false;
        }
        Pesma other = (Pesma) object;
        if ((this.idpesma == null && other.idpesma != null) || (this.idpesma != null && !this.idpesma.equals(other.idpesma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pesma[ idpesma=" + idpesma + " ]";
    }
    
}
