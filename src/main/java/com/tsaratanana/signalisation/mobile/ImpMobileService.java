/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.mobile;
import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.model.TypeSignal;
import com.tsaratanana.signalisation.model.Utilisateur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author zola
 */
@Service
@Transactional
public class ImpMobileService implements ServiceMobile {     
    @Autowired
   RepositoryMobile rep; 
  
   @Override
    public Utilisateur login(String login, String mdp) throws Exception {
        return rep.login(login, mdp);
        
    }

   
    
    @Override
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    try (InputStream is = new URL(url).openStream()) {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    }
    }

    @Override
    public String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
    }
    
    @Override
    public JSONObject liens(String lat, String lon) {
         JSONObject json=null;
        JSONObject v=null;
        try{
            json = readJsonFromUrl("https://nominatim.openstreetmap.org/reverse?format=json&lat="+lat+"&lon="+lon+"&zoom=27");
             v=json.getJSONObject("address");
              System.out.println(json.toString());
      }
      catch(IOException e){
          System.err.println("---------------------------------------------------------");
          e.printStackTrace();
      }
       return v;
    }


    @Override
    public  Integer addsignal  (int idUtilisateur,int idtypeSignal,String description,String photo,Double lat,Double lng,int idRegion,String subUrb,String province ) throws Exception {
        return rep.addsignal(idUtilisateur, idtypeSignal, description, photo, lat, lng,idRegion,subUrb,province);
    }

    @Override
    public Integer inscription(String login, String nom, String mdp) throws Exception {
        return rep.inscription(login, nom, mdp);
       
    }

    @Override
    public List<Region> findBynameRegion(String nom) throws Exception {
       return rep.findBynameRegion(nom);
    }

    @Override
    public List<Region> findRegion() throws Exception {
        return rep.findRegion();
    }

    @Override
    public List<TypeSignal> findTypeSignal() throws Exception {
          return rep.findTypeSignal();
    }

    @Override
    public List<Signal> signals(String id) throws Exception {
        return rep.signals(id);
    }
}
