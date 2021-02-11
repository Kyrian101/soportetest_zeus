package soportetest.zeus.data.dao;

import org.springframework.stereotype.Repository;
import soportetest.zeus.data.dto.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DAOEmployee implements IDAOEmployee {

    // URL connection related info
    private final String CONNECTION_URL = "jdbc:h2:mem:soportetest";
    private final String CONNECTION_USERNAME = "sa";
    private final String CONNECTION_PASSWORD = "";
    // Raw Queries
    private final String QUERY_NEW_EMPLOYEE = "INSERT INTO employees (gender_id, job_id, name, last_name, birthdate)\n" +
                                              "VALUES (?, ?, ?, ?, ?);";
    private final String QUERY_REGISTER_EMPLOYEE_WORKED_HOURS = "INSERT INTO employee_worked_hours (employee_id, worked_hours, worked_date)\n" +
                                                                 "VALUES (?, ?, ?);";
    private final String QUERY_GET_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE id = ?;";
    private final String QUERY_GET_EMPLOYEEID_BY_NAME = "SELECT id FROM employees WHERE name = ? AND last_name = ?;";
    private final String QUERY_GET_WORKEDHOURSID_BY_WORKEDDATE = "SELECT id FROM employee_worked_hours WHERE employee_id = ? AND worked_date = ?;";
    private final String QUERY_GET_TOTALWORKEDHOURS_BY_EMPLOYEEID_AND_PERIOD = "SELECT SUM(worked_hours) AS total_worked_hours\n" +
                                                                               "FROM employee_worked_hours\n" +
                                                                               "WHERE employee_id = ? AND (worked_date BETWEEN ? AND ?)\n" +
                                                                               "GROUP BY employee_id;";
    private final String QUERY_GET_TOTALPAYMENT_BY_EMPLOYEEID_AND_PERIOD = "SELECT\n" +
                                                                           "    (j.salary * t.total_hours) AS total_payment\n" +
                                                                           "FROM employees e\n" +
                                                                           "INNER JOIN jobs j ON j.id = e.job_id\n" +
                                                                           "INNER JOIN (\n" +
                                                                           "    SELECT employee_id, SUM(worked_hours) AS total_hours\n" +
                                                                           "    FROM employee_worked_hours\n" +
                                                                           "    WHERE employee_id = ? AND worked_date BETWEEN ? AND ?\n" +
                                                                           "    GROUP BY employee_id\n" +
                                                                           ") t ON t.employee_id = e.id;";
    private final String QUERY_GET_EMPLOYEES = "SELECT\n" +
                                             "    et.id,\n" +
                                             "    et.name,\n" +
                                             "    et.last_name,\n" +
                                             "    et.birthdate,\n" +
                                             "    jt.id AS job_id,\n" +
                                             "    jt.name AS job_name,\n" +
                                             "    jt.salary,\n" +
                                             "    gt.id AS gender_id,\n" +
                                             "    gt.name AS gender_name\n" +
                                             "FROM employees et\n" +
                                             "INNER JOIN jobs jt ON jt.id = et.job_id\n" +
                                             "INNER JOIN genders gt ON gt.id = et.gender_id|";
    private final String QUERYCOMP_GET_EMPLOYEES_BYJOBID = "\nWHERE jt.id = ?;";

    @Override
    public boolean exists(int employeeId) {

        boolean employeeExists = false;

        try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

            PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_GET_EMPLOYEE_BY_ID);
            queryPrepared.setInt(1, employeeId);

            ResultSet queryResults = queryPrepared.executeQuery();

            while (queryResults.next()) {
                if (queryResults != null) {
                    employeeExists = (queryResults.getInt("id") > 0? true : false);
                }
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeExists;
    }

    @Override
    public boolean isWorkedDateDuplicate(int employeeId, String workedDate) {

        boolean isWorkedDateDiplicate = false;

        try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

            PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_GET_WORKEDHOURSID_BY_WORKEDDATE);
            queryPrepared.setInt(1, employeeId);
            queryPrepared.setDate(2, new Date((new SimpleDateFormat("yyyy-MM-dd").parse(workedDate)).getTime()));

            ResultSet queryResults = queryPrepared.executeQuery();

            while (queryResults.next()) {
                if (queryResults != null) {
                    isWorkedDateDiplicate = (queryResults.getInt(1) > 0? true : false);
                }
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isWorkedDateDiplicate;
    }

    @Override
    public DTORSEmployeeOperation add(DTORQEmployee newEmployeeInfo) {

        Integer employeeIdOperationResult = null;

        try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

            PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_NEW_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
            queryPrepared.setInt(1, newEmployeeInfo.getGender_id());
            queryPrepared.setInt(2, newEmployeeInfo.getJob_id());
            queryPrepared.setString(3, newEmployeeInfo.getName());
            queryPrepared.setString(4, newEmployeeInfo.getLast_name());
            queryPrepared.setString(5, newEmployeeInfo.getBirthdate());

            queryPrepared.executeUpdate();

            ResultSet queryResults = queryPrepared.getGeneratedKeys();

            while (queryResults.next()) {
                if (queryResults != null) {
                    employeeIdOperationResult = queryResults.getInt(1);
                }
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DTORSEmployeeOperation employeeOperationResult = new DTORSEmployeeOperation();

        employeeOperationResult.setId(employeeIdOperationResult);
        employeeOperationResult.setSuccess(employeeIdOperationResult == null? false : true);

        return employeeOperationResult;
    }

    @Override
    public DTORSEmployeeOperation registerWorkedHours(DTORQEmployeeWorkedHours employeeWorkedHoursInfo) {

        Integer workedHourRecordIdResult = null;

        try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

            PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_REGISTER_EMPLOYEE_WORKED_HOURS, Statement.RETURN_GENERATED_KEYS);
            queryPrepared.setInt(1, employeeWorkedHoursInfo.getEmployee_id());
            queryPrepared.setInt(2, employeeWorkedHoursInfo.getWorked_hours());
            queryPrepared.setDate(3, new Date((new SimpleDateFormat("yyyy-MM-dd").parse(employeeWorkedHoursInfo.getWorked_date())).getTime()));

            queryPrepared.executeUpdate();

            ResultSet queryResults = queryPrepared.getGeneratedKeys();

            while (queryResults.next()) {
                if (queryResults != null) {
                    workedHourRecordIdResult = queryResults.getInt(1);
                }
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DTORSEmployeeOperation employeeOperationResult = new DTORSEmployeeOperation();

        employeeOperationResult.setId(workedHourRecordIdResult);
        employeeOperationResult.setSuccess(workedHourRecordIdResult == null? false : true);

        return employeeOperationResult;
    }

    @Override
    public Integer getIdByName(String name, String lastName) {

        Integer employeeId = null;

        if (name != null && lastName != null) {
            try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

                PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_GET_EMPLOYEEID_BY_NAME);
                queryPrepared.setString(1, name);
                queryPrepared.setString(2, lastName);


                ResultSet queryResults = queryPrepared.executeQuery();

                while (queryResults.next()) {
                    if (queryResults != null) {
                        employeeId = queryResults.getInt("id");
                    }
                }

            } catch (SQLException sqlE) {
                sqlE.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return employeeId;
    }

    @Override
    public DTORSEmployeesSearch findByJobId(Integer jobId) {

        List<DTOEmployee> tmpEmployeeList = new ArrayList<>();
        DTORSEmployeesSearch employeesResponse = new DTORSEmployeesSearch();
        boolean operationSuccess = true;

        try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

            PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_GET_EMPLOYEES.replace("|", jobId == null? ";" : QUERYCOMP_GET_EMPLOYEES_BYJOBID));
            if (jobId != null) {
                queryPrepared.setInt(1, jobId);
            }
            ResultSet queryResults = queryPrepared.executeQuery();

            while (queryResults.next()) {
                if (queryResults != null) {
                    DTOEmployee tmpEmployee = new DTOEmployee(queryResults.getInt("id"),
                                                              queryResults.getString("name"),
                                                              queryResults.getString("last_name"),
                                                              (new SimpleDateFormat("dd-MM-yyyy")).format(queryResults.getDate("birthdate")),
                                                              queryResults.getInt("job_id"),
                                                              queryResults.getString("job_name"),
                                                              queryResults.getDouble("salary"),
                                                              queryResults.getInt("gender_id"),
                                                              queryResults.getString("gender_name"));
                    tmpEmployeeList.add(tmpEmployee);
                }
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            operationSuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            operationSuccess = false;
        }

        employeesResponse.setEmployees(operationSuccess? tmpEmployeeList : null);
        employeesResponse.setSuccess(operationSuccess);

        return employeesResponse;
    }

    @Override
    public DTORSWorkedHours getWorkedHours(DTORQEmployeeWorkProfile workProfileSearchRequest) {

        int totalWorkedHours = 0;
        boolean operationSuccess = true;

        try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

            PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_GET_TOTALWORKEDHOURS_BY_EMPLOYEEID_AND_PERIOD);
            queryPrepared.setInt(1, workProfileSearchRequest.getEmployee_id());
            queryPrepared.setDate(2, new Date((new SimpleDateFormat("yyyy-MM-dd").parse(workProfileSearchRequest.getStart_date())).getTime()));
            queryPrepared.setDate(3, new Date((new SimpleDateFormat("yyyy-MM-dd").parse(workProfileSearchRequest.getEnd_date())).getTime()));

            ResultSet queryResults = queryPrepared.executeQuery();

            while (queryResults.next()) {
                if (queryResults != null) {
                    totalWorkedHours = queryResults.getInt("total_worked_hours");
                }
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            operationSuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            operationSuccess = false;
        }

        DTORSWorkedHours employeeOperationResult = new DTORSWorkedHours();

        employeeOperationResult.setTotal_worked_hours(operationSuccess? totalWorkedHours : null);
        employeeOperationResult.setSuccess(operationSuccess);

        return employeeOperationResult;
    }

    @Override
    public DTORSWorkedHoursPayment getWorkedHoursPayment(DTORQEmployeeWorkProfile workProfileSearchRequest) {

        double totalPayment = 0D;
        boolean operationSuccess = true;

        try (Connection cnx = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD)) {

            PreparedStatement queryPrepared = cnx.prepareStatement(QUERY_GET_TOTALPAYMENT_BY_EMPLOYEEID_AND_PERIOD);
            queryPrepared.setInt(1, workProfileSearchRequest.getEmployee_id());
            queryPrepared.setDate(2, new Date((new SimpleDateFormat("yyyy-MM-dd").parse(workProfileSearchRequest.getStart_date())).getTime()));
            queryPrepared.setDate(3, new Date((new SimpleDateFormat("yyyy-MM-dd").parse(workProfileSearchRequest.getEnd_date())).getTime()));

            ResultSet queryResults = queryPrepared.executeQuery();

            while (queryResults.next()) {
                if (queryResults != null) {
                    totalPayment = queryResults.getDouble("total_payment");
                }
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            operationSuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            operationSuccess = false;
        }

        DTORSWorkedHoursPayment employeeOperationResult = new DTORSWorkedHoursPayment();

        employeeOperationResult.setPayment(operationSuccess? totalPayment : null);
        employeeOperationResult.setSuccess(operationSuccess);

        return employeeOperationResult;
    }
}
