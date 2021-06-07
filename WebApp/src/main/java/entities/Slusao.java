/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "slusao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slusao.findAll", query = "SELECT s FROM Slusao s"),
    @NamedQuery(name = "Slusao.findByIdslusao", query = "SELECT s FROM Slusao s WHERE s.idslusao = :idslusao")})
public class Slusao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idslusao")
    private Integer idslusao;
    @JoinColumn(name = "idKorisnik", referencedColumnName = "idkorisnik")
    @ManyToOne(optional = false)
    private Korisnik idKorisnik;
    @JoinColumn(name = "idPesma", referencedColumnName = "idpesma")
    @ManyToOne(optional = false)
    private Pesma idPesma;

    public Slusao() {
    }

    public Slusao(Integer idslusao) {
        this.idslusao = idslusao;
    }

    public Integer getIdslusao() {
        return idslusao;
    }

    public void setIdslusao(Integer idslusao) {
        this.idslusao = idslusao;
    }

    public Korisnik getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Korisnik idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public Pesma getIdPesma() {
        return idPesma;
    }

    public void setIdPesma(Pesma idPesma) {
        this.idPesma = idPesma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idslusao != null ? idslusao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slusao)) {
            return false;
        }
        Slusao other = (Slusao) object;
        if ((this.idslusao == null && other.idslusao != null) || (this.idslusao != null && !this.idslusao.equals(other.idslusao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Slusao[ idslusao=" + idslusao + " ]";
    }
    
}
