/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.logistic.service;

import epsi.tma.entity.DatabaseVersioning;
import epsi.tma.logistic.dao.IDatabaseVersioningDAO;
import java.util.ArrayList;
import java.util.Map;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cDelage
 */
@Service
public class DatabaseVersioningService implements IDatabaseVersioningService {

    private static final Logger LOG = LogManager.getLogger(DatabaseVersioningService.class);

    @Autowired
    IDatabaseVersioningDAO databaseversioningDAO;

    @Override
    public String updateDatabaseVersion() {
        LOG.info("updateDatabaseVersion running");
        System.out.println("updateDatabaseVersion running");
        String response = new String();
        DatabaseVersioning currentDatabaseVersion = findMyVersionByKey("DatabaseVersion");
        ArrayList<String> sqlList = this.getSQLScript();
        int i = 0;
        try {
            if (!this.isDatabaseUptodate()) {
                for (String sqlUpdate : sqlList) {
                    if (i > currentDatabaseVersion.getVersion() || currentDatabaseVersion.getVersion() == 0) {
                        LOG.info("Update sql version launched for request " + sqlList.get(i));
                        this.updateSingleVersion(sqlList.get(i));
                    }
                    i = i + 1;
                }
            }
            this.updateNumberVersion(sqlList.size());
            response = "Update database version run successfuly";
        } catch (Exception exception) {
            LOG.error("Catched exception", exception);
            response = "Catched exception during update of databaseversion : " + exception;
        }

        return response;
    }

    @Override
    public boolean isDatabaseUptodate() {
        // Getting the Full script to update the database.
        ArrayList<String> SQLList;
        SQLList = this.getSQLScript();
        // Get version from the database
        DatabaseVersioning curentDatabaseVersion = findMyVersionByKey("DatabaseVersion");
        if (curentDatabaseVersion != null) {
            // compare both to see if version is uptodate.
            if (SQLList.size() == curentDatabaseVersion.getVersion()) {
                return true;
            }
            LOG.info("Database needs an upgrade - Script : " + SQLList.size() + " Database : " + curentDatabaseVersion.getVersion());
        }
        return false;
    }

    @Override
    public DatabaseVersioning findMyVersionByKey(String key) {
        return databaseversioningDAO.findMyVersionByKey(key);
    }

    @Override
    public String updateSingleVersion(String SQLString) {
        return databaseversioningDAO.updateSingleVersion(SQLString);
    }

    @Override
    public void updateNumberVersion(Integer version) {
        this.databaseversioningDAO.updateNumberVersion(version);
    }

    @Override
    public Map<String, Object> readDatabaseInformation(){
    return databaseversioningDAO.readDatabaseInformation();
    }
            
    @Override
    public ArrayList<String> getSQLScript() {
        // Temporary string that will store the SQL Command before putting in the array.
        StringBuilder b;
        // Full script that create the cerberus database.
        ArrayList<String> a;

        // Start to build the SQL Script here.
        a = new ArrayList();

        // ***********************************************
        // ***********************************************
        // SQL Script Instructions.
        // ***********************************************
        // ***********************************************
        // - Every Query must be independant.<ul>
        //    - Drop and Create index of the table / columns inside the same SQL
        //    - Drop and creation of Foreign Key inside the same SQL
        // - SQL must be fast (even on big tables)
        //    - 1 Index or Foreign Key at a time.
        //    - Beware of big tables that may result a timeout on the GUI side.
        // - Limit the number of SQL required in this class.
        //    - When inserting some data in table, group them inside the same SQL
        // - Never introduce an SQL between 2 SQL.
        //    - it messup the seq of SQL to execute in all users that moved to
        //      earlier version
        // - Only modify the SQL to fix an SQL issue but not to change a
        //   structure or enrich some data on an existing SQL. You need to
        //   create a new one to secure that it gets executed in all env.
        // ***********************************************
        // ***********************************************

        b = new StringBuilder();
        b.append("CREATE TABLE Magasin (");
        b.append("idMagasin INT PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        b.append("nomMagasin VARCHAR(50)");
        b.append(");");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Magasin` (`nomMagasin`) VALUES ('magasin1');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Magasin` (`nomMagasin`) VALUES ('magasin2');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Magasin` (`nomMagasin`) VALUES ('magasin3');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("CREATE TABLE Produit (");
        b.append("idProduit INT PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        b.append("nomProduit VARCHAR(50)");
        b.append(");");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Produit` (`nomProduit`) VALUES ('produit1');");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("INSERT INTO `Produit` (`nomProduit`) VALUES ('produit2');");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("INSERT INTO `Produit` (`nomProduit`) VALUES ('produit3');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("CREATE TABLE Entrepot (");
        b.append("idEntrepot INT PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        b.append("nomEntrepot VARCHAR(50)");
        b.append(");");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Entrepot` (`nomEntrepot`) VALUES ('entrepot1');");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("CREATE TABLE Etat (");
        b.append("idEtat INT PRIMARY KEY NOT NULL,");
        b.append("descriptionEtat VARCHAR(50)");
        b.append(");");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("INSERT INTO `Etat` (`idEtat`,`descriptionEtat`) VALUES (1, 'Demande de préparation');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Etat` (`idEtat`,`descriptionEtat`) VALUES (2, 'En cours de préparation');");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("INSERT INTO `Etat` (`idEtat`,`descriptionEtat`) VALUES (3, 'Commande finie');");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("INSERT INTO `Etat` (`idEtat`,`descriptionEtat`) VALUES (4, 'Commande du jour');");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("CREATE TABLE Commande (");
        b.append("idCommande INT PRIMARY KEY NOT NULL,");
        b.append("idMagasin  INT NOT NULL,");
        b.append("idProduit  INT NOT NULL,");
        b.append("idEntrepot INT NOT NULL,");
        b.append("CONSTRAINT fk_idMagasin_magasin FOREIGN KEY (idMagasin) REFERENCES Magasin(idMagasin),");
        b.append("CONSTRAINT fk_idProduit_produit FOREIGN KEY (idProduit)  REFERENCES Produit(idProduit),");
        b.append("CONSTRAINT fk_idEntrepot_entrepot FOREIGN KEY (identrepot) REFERENCES Entrepot(idEntrepot)");
        b.append(");");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("CREATE TABLE CommandeStatutLog (");
        b.append("idLog      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        b.append("idCommande INT             NOT NULL,");
        b.append("idEtat     INT             NOT NULL,");
        b.append("horodatage TIMESTAMP,");
        b.append("emmeteur   VARCHAR(50),");
        b.append("Action     VARCHAR(50),");
        b.append("CONSTRAINT fk_idCommande_commande FOREIGN KEY (idCommande) REFERENCES Commande(idCommande),");
        b.append("CONSTRAINT fk_idEtat_etat         FOREIGN KEY (idEtat)     REFERENCES Etat(idEtat)");
        b.append(");");
        a.add(b.toString());

        return a;
    }

}
