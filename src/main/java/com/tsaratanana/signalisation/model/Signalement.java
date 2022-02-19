/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author Dell
 */
@Entity
public class Signalement {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;
    int idpersonne;
    Date dateheure;
    String designation;
    int idtypesignalement;
    int idregion;
    int idstatut;
    String latitude;
    String longitude;
    
    @Lob
    byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
    
    
    public Signalement() {
    }

    public Signalement(int id, int idPersonne, Date dateHeure, String designation, int idTypeSignalement, int idRegion, int idStatut, String latitude, String longitude) {
        this.id = id;
        this.idpersonne = idPersonne;
        this.dateheure = dateHeure;
        this.designation = designation;
        this.idtypesignalement = idTypeSignalement;
        this.idregion = idRegion;
        this.idstatut = idStatut;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdpersonne() {
        return idpersonne;
    }

    public void setIdpersonne(int idPersonne) {
        this.idpersonne = idPersonne;
    }

    public Date getDateheure() {
        return dateheure;
    }

    public void setDateheure(Date dateHeure) {
        this.dateheure = dateHeure;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getIdtypesignalement() {
        return idtypesignalement;
    }

    public void setIdtypesignalement(int idTypeSignalement) {
        this.idtypesignalement = idTypeSignalement;
    }

    public int getIdregion() {
        return idregion;
    }

    public void setIdregion(int idRegion) {
        this.idregion = idRegion;
    }

    public int getIdstatut() {
        return idstatut;
    }

    public void setIdstatut(int idStatut) {
        this.idstatut = idStatut;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    
    
    
}
