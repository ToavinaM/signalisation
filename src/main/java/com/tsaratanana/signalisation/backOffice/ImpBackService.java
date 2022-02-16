/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.backOffice;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import com.tsaratanana.signalisation.model.Admin;
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.model.StatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImpBackService implements ServiceBack{
    @Autowired
    RepositoryBack rep;
    
    @Override
    public Admin login(String login, String mdp) throws Exception {
        return rep.login(login, mdp);
    }
    
    @Override
    public List<Signal> signals() throws Exception {
        return rep.signals();
    }

    @Override
    public Integer affectation(int idSignal, int valide) throws Exception  {
        System.err.println("Affectation");
        return rep.affectation(idSignal,valide);
    }


    @Override
    public int getIdTegion(int idSignal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String imageEncoderDecoder(String imgPath) throws IOException{
        String imageString;
        byte[] decodeImg;
        // get byte array from image stream
        try (FileInputStream stream = new FileInputStream(imgPath)) {
            // get byte array from image stream
            int bufLength = 2048;
            byte[] buffer = new byte[2048];
            byte[] data;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int readLength;
                while ((readLength = stream.read(buffer, 0, bufLength)) != -1) {
                    out.write(buffer, 0, readLength);
                }       data = out.toByteArray();
                imageString = Base64.getEncoder().withoutPadding().encodeToString(data);
              //  decodeImg = Base64.getDecoder().decode(imageString);
            }
        }
    return imageString;
  
  
    }

    @Override
    public Integer historique (int idSignal, String status) throws Exception {
        return rep.historique (idSignal, status);
    }

    @Override
    public Integer updateSignal(int idSignal, String lastStatus) throws Exception {
       return rep.updateSignal(idSignal, lastStatus);
    }

    @Override
    public List<StatStatus> getStatStatus(String key) throws Exception {
       return rep.getStatStatus(key);
    }

    
   
    
   
    
}
