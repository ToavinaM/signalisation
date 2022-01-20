/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsaratanana.signalisation.model;

/**
 *
 * @author zola
 */
public class StatStatus {
    int idSignal;
    int countSignal;
    String lastStatus;

    
    public StatStatus(int idSignal, int countSignal, String lastStatus) {
        this.idSignal = idSignal;
        this.countSignal = countSignal;
        this.lastStatus = lastStatus;
    }

    public StatStatus(int countSignal, String lastStatus) {
        this.countSignal = countSignal;
        this.lastStatus = lastStatus;
    }

   
    public void setIdSignal(int idSignal) {
        this.idSignal = idSignal;
    }

    public void setCountSignal(int countSignal) {
        this.countSignal = countSignal;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public int getIdSignal() {
        return idSignal;
    }

    public int getCountSignal() {
        return countSignal;
    }

    public String getLastStatus() {
        return lastStatus;
    }
    
    
    
}
