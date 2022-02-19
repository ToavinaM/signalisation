/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.backOffice;

import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.List;
import com.tsaratanana.signalisation.model.Admin;
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.model.StatStatus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author zola
 */
public interface ServiceBack {
    
    Admin login(String login, String mdp)throws Exception;
    List<Signal> signals()throws Exception;
    Integer updateSignal(int idSignal, String lastStatus) throws Exception;
    Integer affectation(int idSignal, int valide) throws Exception ;
    int getIdTegion (int idSignal);
    String imageEncoderDecoder( String imgPath)throws IOException;
    Integer historique (int idSignal, String status) throws Exception;
    List<StatStatus> getStatStatus(String key)throws Exception;
   
          
}
