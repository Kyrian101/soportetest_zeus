package soportetest.zeus.data.dao;

import org.springframework.stereotype.Repository;
import soportetest.zeus.data.dto.DTOJob;

import java.sql.*;

@Repository
public class DAOJob implements IDAOJob {

    // URL Connection
    private final String CONNECTION_URL = "jdbc:h2:mem:soportetest";
    private final String CONNECTION_USERNAME = "sa";
    private final String CONNECTION_PASSWORD = "";
    // Raw Queries
    private final String QUERY_GET_JOB = "SELECT * FROM jobs WHERE id = ?";

    @Override
    public DTOJob getJob(int jobId) {

        DTOJob jobResponse = null;

        try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

            PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_GET_JOB);
            queryPrepared.setInt(1, jobId);
            ResultSet queryResults = queryPrepared.executeQuery();

            while (queryResults != null && queryResults.next()) {
                jobResponse = new DTOJob(queryResults.getInt("id"),
                                         queryResults.getString("name"),
                                         queryResults.getDouble("salary"));
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobResponse;
    }
}
