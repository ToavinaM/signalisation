/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.mobile;

import com.tsaratanana.signalisation.model.Signal2;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author zola
 */
public interface sourceRepository extends JpaRepository <Signal2, Integer>{
    
    
}
