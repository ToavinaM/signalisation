/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.model;

/**
 *
 * @author zola
 */
public class Region {
    
   int idRegion;
   String login;
   String nom;
   String mdp;

    public Region() {
    }

    public Region(int idRegion, String login, String nom, String mdp) {
        this.idRegion = idRegion;
        this.login = login;
        this.nom = nom;
        this.mdp = mdp;
    }

    public Region(String login, String nom, String mdp) {
        this.login = login;
        this.nom = nom;
        this.mdp = mdp;
    }

    public Region(int idRegion, String login, String mdp) {
        this.idRegion = idRegion;
        this.login = login;
        this.mdp = mdp;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
   
    
}
