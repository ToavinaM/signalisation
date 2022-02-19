/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.mobile;

import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.model.Utilisateur;
import java.util.List;

/**
 *
 * @author zola
 */
public interface RepositoryMobile {
    
  Utilisateur login(String login, String mdp)throws Exception;
  Integer updateSignal(int idSignal, String lastStatus) throws Exception ;
  List<Signal> signals() throws Exception ;
  Integer addsignal  (int idUtilisateur,int idtypeSignal,String description,String photo,Double lat,Double lng,int idRegion,String subUrb,String province ) throws Exception ;
  Integer inscription (String login, String nom, String mdp)throws Exception ;
  List<Region> findBynameRegion (String nom)throws Exception ;
}