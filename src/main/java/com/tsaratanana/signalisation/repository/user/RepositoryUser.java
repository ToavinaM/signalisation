/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.repository.user;

import com.tsaratanana.signalisation.model.User;

/**
 *
 * @author zola
 */
public interface RepositoryUser {
    
  User login(String login, String mdp)throws Exception;
  
    
}
