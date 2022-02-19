/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.mobile;

import com.tsaratanana.signalisation.model.Signalement;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Dell
 */
@Transactional
interface SignalRepository extends CrudRepository<Signalement, Integer>,JpaRepository <Signalement, Integer>  {
    List<Signalement> findByIdstatut(Integer idstatut);
    
    List<Signalement> findByIdregion(Integer idregion);
    
    List<Signalement> findByIdstatutAndIdregion(Integer idstatut,Integer idregion);
    
    @Modifying
    @Query(value="select * from (select * from signalement a where a.idregion=:idregion) s where s.idstatut= :idstatut or s.idTypesignalement= :type or s.dateheure<=:dateh and s.dateheure>=:datee", nativeQuery=true)
    List<Signalement> getSign(@Param("idregion") Integer region,@Param("idstatut") Integer statut, @Param("type") Integer type, @Param("dateh") Date date,@Param("datee") Date date1);
    
    @Modifying
    @Query(value="select * from (select * from signalement a where a.idregion=:idregion) s where s.idstatut= :idstatut or s.idTypesignalement= :type", nativeQuery=true)
    List<Signalement> getSignSansDate(@Param("idregion") Integer region,@Param("idstatut") Integer statut, @Param("type") Integer type);
}
