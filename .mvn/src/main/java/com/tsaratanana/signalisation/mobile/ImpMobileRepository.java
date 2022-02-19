/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.mobile;

import com.tsaratanana.signalisation.model.Region;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.model.Utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author zola
 */
@Repository
@CrossOrigin
public class ImpMobileRepository implements RepositoryMobile {
    @Autowired
    JdbcTemplate jdbc;
    
    private RowMapper<Utilisateur> rowMapperUser = ((rs,rowNum)->{
        try {
            System.err.println(rs);
            return new Utilisateur (rs.getInt("idUtilisateur"),rs.getString("login"),rs.getString("nom"),rs.getString("mdp"));
        } catch (Exception e) {
            return null;
        }
    });
  
    
    private RowMapper<Region>  rowMapperRegion  = ((rs,rowNum)->{
        try {
            System.err.println(rs);
            return new Region (rs.getInt("idRegion"),rs.getString("login"),rs.getString("nom"),rs.getString("mdp"));
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
    public Utilisateur login(String login, String mdp) throws Exception{
        String toLog = "SELECT * FROM Utilisateur WHERE LOGIN = ? AND MDP= ?";
        try {
            System.out.println(mdp+"---huhuh----"+login);
            Utilisateur admin = jdbc.queryForObject(toLog, new Object[] {login,mdp},rowMapperUser);
            System.out.println("idddddddddd"+admin);
            if(admin==null) {
               throw new Exception("Invalid password/mail");
            }
            return admin;
        } catch (Exception e) {
            throw new Exception("Invalid password/mail");
        }     
    }
    
    @Override
    public List<Signal> signals() throws Exception {
       String sign = "select  s.*, t.nom nomSignal, r.nom nomRegion from signal s inner join  typeSignal  t on s.idtypesignal=t.idtypesignal inner join region r on s.idRegion=r.idRegion where s.lastStatus = 'en attente'";
//       String sign = " select  s.*, t.nom nomSignal, r.nom nomRegion from signal s inner join  typeSignal  t on s.idtypesignal=t.idtypesignal inner join region r on s.idRegion=r.idRegion left join historique h on s.idSignal=h.idSignal;";
       return jdbc.query(sign,rowMapperSignal);
    }

   

    @Override
    public Integer updateSignal(int idSignal, String lastStatus) throws Exception {
       String req = "UPDATE SIGNAL SET  lastUpdate=NOW(), lastStatus = ? WHERE idSignal = ?";
       try {
            KeyHolder keyHoslder = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                    PreparedStatement ps =  connection.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1,lastStatus);
                    ps.setInt(2,idSignal);
                    return ps;
            },keyHoslder);
            return (Integer) keyHoslder.getKeys().get("idSignal");
        } catch (DataAccessException e) {
                throw new Exception("Insertion erreur");
        }
    }

    @Override
    public  Integer addsignal  (int idUtilisateur,int idtypeSignal,String description,String photo,Double lat,Double lng,int idRegion,String subUrb,String province ) throws Exception  {
      String addAffect = "INSERT INTO  signal  (idUtilisateur,idtypeSignal,description,photo,lat,lng,idRegion,subUrb,province,dateSignal,lastUpdate,lastStatus) VALUES (?,?,?,?,?,?,?,?,?,NOW(),NOW(),'En attente')";
      System.err.println("INSERTION SIGNAL");
        try {
                KeyHolder keyHoslder = new GeneratedKeyHolder();
                jdbc.update(connection -> {
                        PreparedStatement ps =  connection.prepareStatement(addAffect,Statement.RETURN_GENERATED_KEYS);
                        ps.setInt(1, idUtilisateur);
                        ps.setInt(2, idtypeSignal);
                        ps.setString(3, description);
                        ps.setString(4, photo);
                        ps.setDouble(5,lat);
                        ps.setDouble(6,lng);
                        ps.setInt(7, idRegion);
                        ps.setString(8, subUrb);
                        ps.setString(9, province);
                        return ps;
                },keyHoslder);
                return (Integer) keyHoslder.getKeys().get("idSignal");
        } catch (DataAccessException e) {
                throw new Exception("Invalid request");
        }  
        
    }

    @Override
    public Integer inscription(String login, String nom, String mdp) throws Exception {
        String req = "insert into utilisateur (login,nom,mdp) values(?,?,?)";
      System.err.println("INSERTION SIGNAL");
        try {
                KeyHolder keyHoslder = new GeneratedKeyHolder();
                jdbc.update(connection -> {
                        PreparedStatement ps =  connection.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, login);
                        ps.setString(2, nom);
                        ps.setString(3, mdp);
                        return ps;
                },keyHoslder);
                return (Integer) keyHoslder.getKeys().get("idUtilisateur");
        } catch (DataAccessException e) {
                throw new Exception("Invalid request");
        }  
    }

    @Override
    public List<Region> findBynameRegion(String nom) throws Exception {
        String req= "select * From Region where nom='"+nom+"'";
        System.out.println(req);
        return jdbc.query(req,rowMapperRegion);
        
    }
    
    
    
    
    
    
    
    }

  

   


