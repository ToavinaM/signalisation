/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.frontOffice;

import com.tsaratanana.signalisation.model.Admin;
import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signal;
import java.sql.SQLException;
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
public class ImpFrontRepository implements RepositoryRegion{

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
     private RowMapper<Signal> rowMapperSignal = ((rs,rowNum)->{
        try {
            System.err.println(rs);
            
            return new Signal(rs.getInt("idSignal"),rs.getInt("idtypeSignal"),rs.getInt("idUtilisateur"),rs.getInt("idRegion"),rs.getString("nomSignal"),rs.getString("nomRegion"),rs.getString("description"),rs.getString("photo"),rs.getTimestamp("dateSignal"),rs.getDouble("lat"),rs.getDouble("lng"),rs.getString("subUrb"),rs.getString("province"),rs.getTimestamp("lastUpdate"),rs.getString("lastStatus"));
        } catch (SQLException e) {
            return null;
        }    
    });
    

    @Override
    public List<Signal> signalRegions(String idRegion) throws Exception {
     String sign = "select  s.*, t.nom nomSignal, r.nom nomRegion from signal s inner join  typeSignal  t on s.idtypesignal=t.idtypesignal inner join region r on s.idRegion=r.idRegion where s.lastStatus != 'En attente' s.idRegion="+idRegion;
//       String sign = " select  s.*, t.nom nomSignal, r.nom nomRegion from signal s inner join  typeSignal  t on s.idtypesignal=t.idtypesignal inner join region r on s.idRegion=r.idRegion left join historique h on s.idSignal=h.idSignal;";
       return jdbc.query(sign,rowMapperSignal);
    }
    
}
