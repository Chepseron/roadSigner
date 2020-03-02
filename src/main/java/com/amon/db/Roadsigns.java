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
 * @author Amon.Sabul
 */
@Entity
@Table(name = "roadsigns")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roadsigns.findAll", query = "SELECT r FROM Roadsigns r")
    , @NamedQuery(name = "Roadsigns.findByIdsigns", query = "SELECT r FROM Roadsigns r WHERE r.idsigns = :idsigns")
    , @NamedQuery(name = "Roadsigns.findByLat", query = "SELECT r FROM Roadsigns r WHERE r.lat = :lat")
    , @NamedQuery(name = "Roadsigns.findByLongitude", query = "SELECT r FROM Roadsigns r WHERE r.longitude = :longitude")
    , @NamedQuery(name = "Roadsigns.findByDescription", query = "SELECT r FROM Roadsigns r WHERE r.description = :description")
    , @NamedQuery(name = "Roadsigns.findByDateinstalled", query = "SELECT r FROM Roadsigns r WHERE r.dateinstalled = :dateinstalled")
    , @NamedQuery(name = "Roadsigns.findByCreatedBy", query = "SELECT r FROM Roadsigns r WHERE r.createdBy = :createdBy")
    , @NamedQuery(name = "Roadsigns.findByCreatedOn", query = "SELECT r FROM Roadsigns r WHERE r.createdOn = :createdOn")
    , @NamedQuery(name = "Roadsigns.findByPlaceName", query = "SELECT r FROM Roadsigns r WHERE r.placeName = :placeName")
    , @NamedQuery(name = "Roadsigns.findByRoadName", query = "SELECT r FROM Roadsigns r WHERE r.roadName = :roadName")
    , @NamedQuery(name = "Roadsigns.findByStatus", query = "SELECT r FROM Roadsigns r WHERE r.status = :status")})
public class Roadsigns implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsigns")
    private Integer idsigns;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "lat")
    private String lat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "longitude")
    private String longitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateinstalled")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateinstalled;
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
    @Column(name = "placeName")
    private String placeName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "roadName")
    private String roadName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @JoinColumn(name = "signid", referencedColumnName = "idsigns")
    @ManyToOne(optional = false)
    private Signs signid;

    public Roadsigns() {
    }

    public Roadsigns(Integer idsigns) {
        this.idsigns = idsigns;
    }

    public Roadsigns(Integer idsigns, String lat, String longitude, String description, Date dateinstalled, int createdBy, Date createdOn, String placeName, String roadName, int status) {
        this.idsigns = idsigns;
        this.lat = lat;
        this.longitude = longitude;
        this.description = description;
        this.dateinstalled = dateinstalled;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.placeName = placeName;
        this.roadName = roadName;
        this.status = status;
    }

    public Integer getIdsigns() {
        return idsigns;
    }

    public void setIdsigns(Integer idsigns) {
        this.idsigns = idsigns;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateinstalled() {
        return dateinstalled;
    }

    public void setDateinstalled(Date dateinstalled) {
        this.dateinstalled = dateinstalled;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Signs getSignid() {
        return signid;
    }

    public void setSignid(Signs signid) {
        this.signid = signid;
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
        if (!(object instanceof Roadsigns)) {
            return false;
        }
        Roadsigns other = (Roadsigns) object;
        if ((this.idsigns == null && other.idsigns != null) || (this.idsigns != null && !this.idsigns.equals(other.idsigns))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Roadsigns[ idsigns=" + idsigns + " ]";
    }
    
}
