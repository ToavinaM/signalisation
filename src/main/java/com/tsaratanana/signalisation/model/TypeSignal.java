/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.model;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author zola
 */
@Document(collection = "typeSignal")
public class TypeSignal {
    
   int idTypeSignal;
   String nom;

    public TypeSignal(int idTypeSignal, String nom) {
        this.idTypeSignal = idTypeSignal;
        this.nom = nom;
    }

    public int getIdTypeSignal() {
        return idTypeSignal;
    }

    public void setIdTypeSignal(int idTypeSignal) {
        this.idTypeSignal = idTypeSignal;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
 
   
    
}
