package epsi.tma.logistic.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cDelage
 */
public class CommandeStatutLogDAO implements ICommandeStatutLogDAO{
    public Map<String,Object> read(){
    Map<String,Object> response = new HashMap();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM CommandeStatutLog");
    //try{
    
    //}catch(SQLException exception){
        
    //}
    return response;
    }
    
    //public String create(String emmeteur, String action, int idCommande, Timestamp horodatage, int idProduit)
    
}
