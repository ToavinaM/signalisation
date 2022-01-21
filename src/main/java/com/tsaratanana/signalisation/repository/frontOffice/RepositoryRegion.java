
package com.tsaratanana.signalisation.repository.frontOffice;

import java.util.List;
import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signal;

/**
 *
 * @author zola
 */
public interface RepositoryRegion {
    
    Region login(String login, String mdp)throws Exception;
    List<Signal> signalRegions(String idRegion)throws Exception;  
}
