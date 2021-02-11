package soportetest.zeus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soportetest.zeus.data.dao.IDAOEmployee;
import soportetest.zeus.data.dto.*;

import java.time.LocalDate;
import java.time.Period;

@Service
public class SVEmployee implements ISVEmployee {

    @Autowired
    IDAOEmployee employeeDAO;
    @Autowired
    ISVJob jobSV;
    @Autowired
    ISVGender genderSV;

    private int calculateAge(String birthdateRaw) {

        int calculatedAge = 0;

        try {
            if (birthdateRaw != null && !birthdateRaw.isEmpty()) {
                calculatedAge = Period.between(LocalDate.parse(birthdateRaw), LocalDate.now()).getYears();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return calculatedAge;
    }

    @Override
    public boolean exists(int employeeId) {
        return employeeDAO.exists(employeeId);
    }

    @Override
    public boolean isNewRequestValid(DTORQEmployee newEmployeeInfo) {
        if (newEmployeeInfo == null ||
            employeeDAO.getIdByName(newEmployeeInfo.getName(), newEmployeeInfo.getLast_name()) != null ||
            calculateAge(newEmployeeInfo.getBirthdate()) < 18 ||
            !jobSV.exists(newEmployeeInfo.getJob_id()) ||
            !genderSV.exists(newEmployeeInfo.getGender_id())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isWorkedHoursRequestValid(DTORQEmployeeWorkedHours employeeWorkedHoursInfo) {
        if (employeeWorkedHoursInfo == null ||
            employeeWorkedHoursInfo.getWorked_hours() > 20 ||
            LocalDate.parse(employeeWorkedHoursInfo.getWorked_date()).isAfter(LocalDate.now()) ||
            employeeDAO.isWorkedDateDuplicate(employeeWorkedHoursInfo.getEmployee_id(), employeeWorkedHoursInfo.getWorked_date())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isWorkProfileSearchRequestValid(DTORQEmployeeWorkProfile employeeWorkProfileSearch) {
        if (employeeWorkProfileSearch == null ||
                LocalDate.parse(employeeWorkProfileSearch.getStart_date()).isAfter(LocalDate.parse(employeeWorkProfileSearch.getEnd_date()))) {
            return false;
        }

        return true;
    }

    @Override
    public DTORSEmployeeOperation add(DTORQEmployee newEmployeeInfo) {
        return employeeDAO.add(newEmployeeInfo);
    }

    @Override
    public DTORSEmployeeOperation registerWorkedHours(DTORQEmployeeWorkedHours employeeWorkedHoursInfo) {
        return employeeDAO.registerWorkedHours(employeeWorkedHoursInfo);
    }

    @Override
    public DTORSEmployeeOperation getEmptyOperationResponse() {
        return new DTORSEmployeeOperation();
    }

    @Override
    public DTORSWorkedHours getWorkedHoursEmptyOperationResponse() {
        return new DTORSWorkedHours();
    }

    @Override
    public DTORSWorkedHoursPayment getWorkedHoursPaymentEmptyOperationResponse() {
        return new DTORSWorkedHoursPayment();
    }

    @Override
    public DTORSEmployeesSearch getEmptyResponse() {
        return new DTORSEmployeesSearch();
    }

    @Override
    public DTORSEmployeesSearch findByJobId(Integer jobId) {
        return employeeDAO.findByJobId(jobId);
    }

    @Override
    public DTORSWorkedHours getWorkedHours(DTORQEmployeeWorkProfile workProfileSearchRequest) {
        return employeeDAO.getWorkedHours(workProfileSearchRequest);
    }

    @Override
    public DTORSWorkedHoursPayment getWorkedHoursPayment(DTORQEmployeeWorkProfile workProfileSearchRequest) {
        return employeeDAO.getWorkedHoursPayment(workProfileSearchRequest);
    }

}
