/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author zola
 */
@Entity
public class Signal2 {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
    int idSignal;
   int idtypeSignal;
   int idUtilisateur;
   int idRegion;
   String nomSignal;
   String nomRegion;       
   String description;
   @Lob
   byte[] photo;
   Timestamp dateSignal;
   Double lat;
   Double lng;
   String subUrb;
   String province;
   Timestamp lastUpdate;
   String lastStatus;

    public int getIdSignal() {
        return idSignal;
    }

    public void setIdSignal(int idSignal) {
        this.idSignal = idSignal;
    }

    public int getIdtypeSignal() {
        return idtypeSignal;
    }

    public void setIdtypeSignal(int idtypeSignal) {
        this.idtypeSignal = idtypeSignal;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getNomSignal() {
        return nomSignal;
    }

    public void setNomSignal(String nomSignal) {
        this.nomSignal = nomSignal;
    }

    public String getNomRegion() {
        return nomRegion;
    }

    public void setNomRegion(String nomRegion) {
        this.nomRegion = nomRegion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Timestamp getDateSignal() {
        return dateSignal;
    }

    public void setDateSignal(Timestamp dateSignal) {
        this.dateSignal = dateSignal;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getSubUrb() {
        return subUrb;
    }

    public void setSubUrb(String subUrb) {
        this.subUrb = subUrb;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    
   
 
    
}
