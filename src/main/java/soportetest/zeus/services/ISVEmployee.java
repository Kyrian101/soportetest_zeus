package soportetest.zeus.services;

import soportetest.zeus.data.dto.*;

public interface ISVEmployee {
    public boolean exists(int employeeId);
    public boolean isNewRequestValid(DTORQEmployee newEmployeeInfo);
    public boolean isWorkedHoursRequestValid(DTORQEmployeeWorkedHours employeeWorkedHoursInfo);
    public boolean isWorkProfileSearchRequestValid(DTORQEmployeeWorkProfile employeeWorkProfileSearch);
    public DTORSEmployeeOperation add(DTORQEmployee newEmployeeInfo);
    public DTORSEmployeeOperation registerWorkedHours(DTORQEmployeeWorkedHours employeeWorkedHoursInfo);
    public DTORSEmployeeOperation getEmptyOperationResponse();
    public DTORSWorkedHours getWorkedHoursEmptyOperationResponse();
    public DTORSWorkedHoursPayment getWorkedHoursPaymentEmptyOperationResponse();
    public DTORSEmployeesSearch getEmptyResponse();
    public DTORSEmployeesSearch findByJobId(Integer jobId);
    public DTORSWorkedHours getWorkedHours(DTORQEmployeeWorkProfile workProfileSearchRequest);
    public DTORSWorkedHoursPayment getWorkedHoursPayment(DTORQEmployeeWorkProfile workProfileSearchRequest);
}
