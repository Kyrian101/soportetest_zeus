package soportetest.zeus.data.dao;

import org.springframework.stereotype.Repository;
import soportetest.zeus.data.dto.DTOGender;
import soportetest.zeus.data.dto.DTOJob;

import java.sql.*;

@Repository
public class DAOGender implements IDAOGender {

    // URL Connection
    private final String CONNECTION_URL = "jdbc:h2:mem:soportetest";
    private final String CONNECTION_USERNAME = "sa";
    private final String CONNECTION_PASSWORD = "";
    // Raw Queries
    private final String QUERY_GET_GENDER = "SELECT * FROM genders WHERE id = ?";

    @Override
    public DTOGender getGender(int genderId) {

        DTOGender genderResponse = null;

        try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

            PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_GET_GENDER);
            queryPrepared.setInt(1, genderId);
            ResultSet queryResults = queryPrepared.executeQuery();

            while (queryResults != null && queryResults.next()) {
                genderResponse = new DTOGender(queryResults.getInt("id"),
                                               queryResults.getString("name"));
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genderResponse;
    }
}
