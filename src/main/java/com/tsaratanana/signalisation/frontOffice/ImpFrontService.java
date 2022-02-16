/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.frontOffice;

import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.backOffice.RepositoryBack;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zola
 */
@Service
@Transactional
public class ImpFrontService implements ServiceRegion{    
    @Autowired
    RepositoryRegion rep;

    
    @Override
    public Region login(String login, String mdp) throws Exception {
         return rep.login(login, mdp);
    }

    @Override
    public List<Signal> signalRegions(String idRegion) throws Exception {
         return rep.signalRegions(idRegion);
    }
    
    
}
