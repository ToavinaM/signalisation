/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.service.mobile;

import com.tsaratanana.signalisation.service.frontOffice.*;

import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.model.Utilisateur;
import java.io.IOException;
import java.io.Reader;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author zola
 */


public interface ServiceUser {

    Utilisateur login(String login, String mdp)throws Exception;
    List<Signal> signalRegions()throws Exception; 
    Integer addsignal  (int idUtilisateur,int idtypeSignal,String description,String photo,Double lat,Double lng,int idRegion,String subUrb,String province ) throws Exception ;
    JSONObject readJsonFromUrl(String url) throws IOException, JSONException ;
    String readAll(Reader rd) throws IOException;
    JSONObject  liens (String lat,String lon);

    
    
}
