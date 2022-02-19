package com.tsaratanana.signalisation.model;


public class Utilisateur {
   int idUtilisateur;
   String login;
   String  nom ;
   String mdp;

    public Utilisateur(int idUtilisateur, String login, String nom, String mdp) {
        this.idUtilisateur = idUtilisateur;
        this.login = login;
        this.nom = nom;
        this.mdp = mdp;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
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
