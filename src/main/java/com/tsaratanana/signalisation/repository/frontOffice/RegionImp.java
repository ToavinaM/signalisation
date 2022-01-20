/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.repository.frontOffice;

import com.tsaratanana.signalisation.model.Admin;
import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author zola
 */
@Repository
@CrossOrigin
public class RegionImp implements RepositoryRegion{

    @Autowired
    JdbcTemplate jdbc;
    
    
    @Override
    public Region login(String login, String mdp) throws Exception {
        String toLog = "SELECT * FROM REGION WHERE LOGIN = ? AND MDP= ?";
        try {
            
            System.out.println(login+"---huhuh----"+mdp);
            Region region = jdbc.queryForObject(toLog, new Object[] {login,mdp},rowMapperRegion);
            System.out.println("idddddddddd"+region);
            
            if(region==null) {
               throw new Exception("Invalid password/mail");
            }
            return region;
        } catch (Exception e) {
            throw new Exception("Invalid password/mail");
        }
       
    }
    
    
    private RowMapper<Region> rowMapperRegion = ((rs,rowNum)->{
        try {
            System.err.println(rs);
           return new Region(rs.getInt("idregion"),rs.getString("login"),rs.getString("mdp"));
        } catch (Exception e) {
            return null;
        }
     
          
    });

    @Override
    public List<Signal> signalRegions() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
