/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.frontOffice;

import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zola
 */


public interface ServiceRegion {

    Region login(String login, String mdp)throws Exception;
    List<Signal> signalRegions(String idRegion)throws Exception; 
    
    
}
