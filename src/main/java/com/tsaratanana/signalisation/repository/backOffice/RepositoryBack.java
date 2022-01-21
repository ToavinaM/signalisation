
package com.tsaratanana.signalisation.repository.backOffice;

import java.util.List;
import com.tsaratanana.signalisation.model.Admin;
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.model.StatStatus;

/**
 *
 * @author zola
 */

public interface RepositoryBack {
    
    Admin login(String login, String mdp)throws Exception;
    List<Signal> signals()throws Exception;
    Integer updateSignal (int idSignal,String lastStatus) throws Exception;
    Integer historique  (int idSignal, String status ) throws Exception ;
    List<StatStatus> getStatStatus(String key)throws Exception;

    
    
    
    Integer affectation(int idSignal, int valide) throws Exception ;
    
}
