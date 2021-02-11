package soportetest.zeus.data.dao;

import soportetest.zeus.data.dto.*;

public interface IDAOEmployee {
    public boolean exists(int employeeId);
    public boolean isWorkedDateDuplicate(int employeeId, String workedDate);
    public DTORSEmployeeOperation add(DTORQEmployee newEmployeeInfo);
    public DTORSEmployeeOperation registerWorkedHours(DTORQEmployeeWorkedHours employeeWorkedHoursInfo);
    public Integer getIdByName(String name, String lastName);
    public DTORSEmployeesSearch findByJobId(Integer jobId);
    public DTORSWorkedHours getWorkedHours(DTORQEmployeeWorkProfile workProfileSearchRequest);
    public DTORSWorkedHoursPayment getWorkedHoursPayment(DTORQEmployeeWorkProfile workProfileSearchRequest);
}