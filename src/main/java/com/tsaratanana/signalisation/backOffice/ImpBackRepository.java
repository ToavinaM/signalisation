/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.backOffice;

import com.tsaratanana.signalisation.backOffice.RepositoryBack;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.tsaratanana.signalisation.model.Admin;
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.model.StatStatus;
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
public class ImpBackRepository implements RepositoryBack {
    @Autowired
    JdbcTemplate jdbc;
    
    private RowMapper<Admin> rowMapperAdmin = ((rs,rowNum)->{
        try {
            System.err.println(rs);
            return new Admin (rs.getInt("idAdmin"),rs.getString("login"),rs.getString("mdp"));
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
    
    private final RowMapper<StatStatus> rowMapperStatStatus = ((rs,rowNum)->{
        try {
            System.err.println(rs);
            return new StatStatus(rs.getInt("countSignal"),rs.getString("lastStatus"));
        } catch (SQLException e) {
            return null;
        }    
    });
    @Override
    public Admin login(String login, String mdp) throws Exception{
        String toLog = "SELECT * FROM ADMIN WHERE LOGIN = ? AND MDP= ?";
        try {
            System.out.println(mdp+"---huhuh----"+login);
            Admin admin = jdbc.queryForObject(toLog, new Object[] {login,mdp},rowMapperAdmin);
            System.out.println("idddddddddd"+admin);
            if(admin==null) {
               throw new Exception("Invalid password or email");
            }
            return admin;
        } catch (Exception e) {
            throw new Exception("Invalid password or email");
        }     
    }
    
    @Override
    public List<Signal> signals() throws Exception {
       String sign = "select  s.*, t.nom nomSignal, r.nom nomRegion from signal s inner join  typeSignal  t on s.idtypesignal=t.idtypesignal inner join region r on s.idRegion=r.idRegion where s.lastStatus = 'En attente'";
//       String sign = " select  s.*, t.nom nomSignal, r.nom nomRegion from signal s inner join  typeSignal  t on s.idtypesignal=t.idtypesignal inner join region r on s.idRegion=r.idRegion left join historique h on s.idSignal=h.idSignal;";
       return jdbc.query(sign,rowMapperSignal);
    }

    @Override
    public Integer affectation(int idSignal, int valide) throws Exception {
      String addAffect = "INSERT INTO AFFECTATION (idSignal,valide,dateAffectation) VALUES (?,?,NOW())";
      System.err.println("AFFECTATION");
    try {
            KeyHolder keyHoslder = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                    PreparedStatement ps =  connection.prepareStatement(addAffect,Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, idSignal);
                    ps.setInt(2, valide);
                    return ps;
            },keyHoslder);
            return (Integer) keyHoslder.getKeys().get("idAffectation");
    } catch (DataAccessException e) {
            throw new Exception("Invalid request");
    }
    }
    

    @Override
    public Integer historique (int idSignal, String status) throws Exception {
      String addAffect = "INSERT INTO  historique  (idSignal,status,dateT) VALUES (?,?,NOW())";
      System.err.println("AFFECTATION");
        try {
                KeyHolder keyHoslder = new GeneratedKeyHolder();
                jdbc.update(connection -> {
                        PreparedStatement ps =  connection.prepareStatement(addAffect,Statement.RETURN_GENERATED_KEYS);
                        ps.setInt(1, idSignal);
                        ps.setString(2, status);
                        return ps;
                },keyHoslder);
                return (Integer) keyHoslder.getKeys().get("idTraitement");
        } catch (DataAccessException e) {
                throw new Exception("Invalid request");
        }  
        
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
    public List<StatStatus> getStatStatus(String key) throws Exception {
        String requet="";
        if (key.equals("status"))
            requet = " select  count (idSignal) countSignal, lastStatus from signal group by lastStatus";
        if (key.equals("region"))
            requet="select  count (s.idSignal) countSignal, t.nom as lastStatus from signal s inner join region t on t.idRegion=s.idRegion  group by t.nom";
        if (key.equals("type"))
            requet="select  count (s.idSignal) countSignal, t.nom as lastStatus from signal s inner join typeSignal t on t.idTypeSignal=s.idTypeSignal  group by t.nom";
        if (key.equals("date"))
             requet=" select  count (idSignal) countSignal, DATE(dateSignal) as lastStatus from signal group by DATE(dateSignal) limit 3";
        return jdbc.query(requet, rowMapperStatStatus);
    }

   
}

