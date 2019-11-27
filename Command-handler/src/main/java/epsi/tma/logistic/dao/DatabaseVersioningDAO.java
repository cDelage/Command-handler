/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.logistic.dao;

import epsi.tma.database.DatabaseSpring;
import epsi.tma.entity.DatabaseVersioning;
import epsi.tma.logistic.factory.IFactoryDatabaseVersioning;
import epsi.tma.util.ParameterParserUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * DAO object to access to DatabaseVersion table Target is to handle database
 * version
 *
 * @author cDelage
 */
@Repository
public class DatabaseVersioningDAO implements IDatabaseVersioningDAO {

    private static final Logger LOG = LogManager.getLogger(DatabaseVersioningDAO.class);

    @Autowired
    private DatabaseSpring databaseSpring;

    @Autowired
    private IFactoryDatabaseVersioning factoryDatabaseVersioning;

    @Override
    public DatabaseVersioning findMyVersionByKey(String key) {
        DatabaseVersioning result = new DatabaseVersioning();
        final String query = "SELECT * FROM DatabaseVersioning dv WHERE dv.`key` = ? ";

        // Debug message on SQL.
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL : " + query);
            LOG.debug("SQL.param.key : " + key);
        }
        LOG.info("findMyVersionByKey launched, try to connect to database");

        try {
            Connection connection = this.databaseSpring.connect();
            try {
                PreparedStatement preStat = connection.prepareStatement(query);
                try {
                    preStat.setString(1, key);

                    ResultSet resultSet = preStat.executeQuery();
                    try {
                        if (resultSet.next()) {
                            result = loadFromResultSet(resultSet);
                        }
                    } catch (SQLException exception) {
                        result = null;
                        LOG.warn("Unable to execute query : " + exception.toString());
                    } finally {
                        resultSet.close();
                    }
                } catch (Exception exception) {
                    result = null;
                    LOG.warn("Unable to execute query : " + exception.toString());
                } finally {
                    preStat.close();
                }
            } catch (SQLException exception) {
                LOG.warn("Unable to execute query : " + exception.toString());
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    LOG.warn(e.toString());
                }
            }
        } catch (Exception exception) {
            LOG.error("Failed to connect to database, catched Exception : ", exception);
        }

        return result;
    }

    private DatabaseVersioning loadFromResultSet(ResultSet resultSet) {
        String key = "";
        int value = 0;
        try {
            key = ParameterParserUtil.parseStringParam(resultSet.getString("Key"), "DatabaseVersion");
            value = ParameterParserUtil.parseIntegerParam(resultSet.getInt("Value"), 0);
        } catch (SQLException ex) {
            LOG.warn(ex);
        }

        return factoryDatabaseVersioning.create(key, value);
    }

    @Override
    public String updateSingleVersion(String SQLString) {
        LOG.info("Starting Execution of '" + SQLString + "'");
        System.out.println("-------------------------------");
        System.out.println("DAO - updateSingleVersion");
        System.out.println(SQLString);
        System.out.println("-------------------------------");
        try (Connection connection = this.databaseSpring.connect();
                Statement preStat = connection.createStatement();) {
            preStat.execute(SQLString);
            LOG.info("'" + SQLString + "' Executed successfully.");
        } catch (Exception exception) {
            LOG.error(exception.toString(), exception);
            System.out.println("-------------------------------");
            System.out.println("DAO - error catch");
            System.out.println(exception.getMessage());
            System.out.println("-------------------------------");
            return exception.toString();
        } finally {
            this.databaseSpring.closeConnection();
        }
        return "OK";
    }

    @Override
    public void updateNumberVersion(Integer version) {
        try (Connection connection = this.databaseSpring.connect();) {
            LOG.info("Try to update database to " + version + " version");
            System.out.println("updateNumberVersion called, update version to " + version);
            String query = "UPDATE DatabaseVersioning SET `Value` = ? WHERE `Key` = 'DatabaseVersion';";
            PreparedStatement preStat = connection.prepareStatement(query);
            preStat.setInt(1, version);
            preStat.executeUpdate();
        } catch (Exception exception) {
            LOG.error("Failed to update version number, catched exception", exception);
            System.out.println("Error to updateNumberVersion, catch exception : " + exception);
        }
    }

    @Override
    public Map<String, Object> readDatabaseInformation() {
        Map<String, Object> response = new HashMap();
        Connection connection = this.databaseSpring.connect();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            response.put("DatabaseProductName", metaData.getDatabaseProductName());
            response.put("DatabaseProductVersion", metaData.getDatabaseProductVersion());
            response.put("DatabaseMajorVersion", Integer.toString(metaData.getDatabaseMajorVersion()));
            response.put("DatabaseMinorVersion", Integer.toString(metaData.getDatabaseMinorVersion()));
            response.put("DatabaseVersioningService - version", this.findMyVersionByKey("DatabaseVersion"));

            response.put("DriverName", metaData.getDriverName());
            response.put("DriverVersion", metaData.getDriverVersion());
            response.put("DriverMajorVersion", Integer.toString(metaData.getDriverMajorVersion()));
            response.put("DriverMinorVersion", Integer.toString(metaData.getDriverMinorVersion()));

            response.put("JDBCMajorVersion", Integer.toString(metaData.getJDBCMajorVersion()));
            response.put("JDBCMinorVersion", Integer.toString(metaData.getJDBCMinorVersion()));
            
        } catch (SQLException exception) {
            LOG.error("Catch sql exception during read database information, ", exception);
            response.put("SQL Exception", exception);
        }
        return response;
    }
}
