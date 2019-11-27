/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.logistic.factory;

import epsi.tma.entity.CommandeStatutLog;
import java.sql.Timestamp;

/**
 *
 * @author utilisateur
 */
public class FactoryCommandeStatutLog implements IFactoryCommandeStatutLog {
    
    public CommandeStatutLog create(int idCommande, int idEtat, Timestamp horodatage, String emeteur, String action){
    CommandeStatutLog commandeStatutLog = new CommandeStatutLog();
    commandeStatutLog.setIdCommande(idCommande);
    commandeStatutLog.setIdEtat(idEtat);
    commandeStatutLog.setHorodatage(horodatage);
    commandeStatutLog.setEmmeteur(emeteur);
    commandeStatutLog.setAction(action);
    return commandeStatutLog;
    }
}
