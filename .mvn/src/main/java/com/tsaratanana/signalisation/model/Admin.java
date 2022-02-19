package com.tsaratanana.signalisation.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.Connection;
import java.sql.SQLException;

public class Admin {
    private int  id;
    private String login;
    private String nom;
    private String mdp;
    

    public Admin(int id, String login, String mdp) throws Exception{
        this.setId(id);
        this.setLogin(login);
        this.setMdp(mdp);
    }

    public Admin(String login, String nom, String mdp) {
        this.login = login;
        this.nom = nom;
        this.mdp = mdp;
    }
    

    public Admin() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws Exception {
        if("".equals(login))
            throw new Exception("veuillez remplir tout les champs!");
         
        this.login = login;
    }

    public String getMdp() {
        
        return mdp;
    }

    public void setMdp(String mdp) throws Exception  {
         if("".equals(mdp))
            throw new Exception("veuillez remplir tout les champs!");
         
        this.mdp = mdp;
    }
    
   

  
    
    
    
}
