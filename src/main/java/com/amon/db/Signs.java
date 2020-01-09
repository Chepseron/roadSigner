/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.amon.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Anonymous
 */
@Entity
@Table(name = "signs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Signs.findAll", query = "SELECT s FROM Signs s"),
    @NamedQuery(name = "Signs.findByIdsigns", query = "SELECT s FROM Signs s WHERE s.idsigns = :idsigns"),
    @NamedQuery(name = "Signs.findBySignname", query = "SELECT s FROM Signs s WHERE s.signname = :signname"),
    @NamedQuery(name = "Signs.findByDescription", query = "SELECT s FROM Signs s WHERE s.description = :description"),
    @NamedQuery(name = "Signs.findByCreatedBy", query = "SELECT s FROM Signs s WHERE s.createdBy = :createdBy"),
    @NamedQuery(name = "Signs.findByCreatedOn", query = "SELECT s FROM Signs s WHERE s.createdOn = :createdOn"),
    @NamedQuery(name = "Signs.findByPhoto", query = "SELECT s FROM Signs s WHERE s.photo = :photo")})
public class Signs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsigns")
    private Integer idsigns;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "signname")
    private String signname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdBy")
    private int createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "photo")
    private String photo;

    public Signs() {
    }

    public Signs(Integer idsigns) {
        this.idsigns = idsigns;
    }

    public Signs(Integer idsigns, String signname, String description, int createdBy, Date createdOn, String photo) {
        this.idsigns = idsigns;
        this.signname = signname;
        this.description = description;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.photo = photo;
    }

    public Integer getIdsigns() {
        return idsigns;
    }

    public void setIdsigns(Integer idsigns) {
        this.idsigns = idsigns;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsigns != null ? idsigns.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Signs)) {
            return false;
        }
        Signs other = (Signs) object;
        if ((this.idsigns == null && other.idsigns != null) || (this.idsigns != null && !this.idsigns.equals(other.idsigns))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Signs[ idsigns=" + idsigns + " ]";
    }
    
}
