package soportetest.zeus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soportetest.zeus.data.dto.*;
import soportetest.zeus.services.ISVEmployee;
import soportetest.zeus.services.ISVJob;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/employee")
public class CTEmployee {

    @Autowired
    ISVJob jobSV;
    @Autowired
    ISVEmployee employeeSV;

    @GetMapping(value = "/")
    public ResponseEntity<DTORSEmployeesSearch> searchWithFilter(@RequestBody DTORQEmployeesSearch employeesSearchRequest) {

        if (!jobSV.exists(employeesSearchRequest.getJob_id())) {
            return new ResponseEntity<>(employeeSV.getEmptyResponse(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity<>(employeeSV.findByJobId(employeesSearchRequest.getJob_id()), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<DTORSEmployeeOperation> addEmployee(@RequestBody DTORQEmployee newEmployeeInfo) {
        if (!employeeSV.isNewRequestValid(newEmployeeInfo)) {
            return new ResponseEntity<>(employeeSV.getEmptyOperationResponse(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity<>(employeeSV.add(newEmployeeInfo), HttpStatus.OK);
    }

    @GetMapping(value = "/workprofile/time/")
    public ResponseEntity<DTORSWorkedHours> getWorkedHours(@RequestBody DTORQEmployeeWorkProfile employeeWorkProfilesSearch) {
        if (employeeWorkProfilesSearch != null && !employeeSV.exists(employeeWorkProfilesSearch.getEmployee_id())) {
            return new ResponseEntity<>(employeeSV.getWorkedHoursEmptyOperationResponse(), HttpStatus.NOT_FOUND);
        }

        if (!employeeSV.isWorkProfileSearchRequestValid(employeeWorkProfilesSearch)) {
            return new ResponseEntity<>(employeeSV.getWorkedHoursEmptyOperationResponse(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity<>(employeeSV.getWorkedHours(employeeWorkProfilesSearch), HttpStatus.OK);
    }

    @PostMapping(value = "/workprofile/time/")
    public ResponseEntity<DTORSEmployeeOperation> registerWorkedHours(@RequestBody DTORQEmployeeWorkedHours employeeWorkedHoursInfo) {
        if (employeeWorkedHoursInfo != null && !employeeSV.exists(employeeWorkedHoursInfo.getEmployee_id())) {
            return new ResponseEntity<>(employeeSV.getEmptyOperationResponse(), HttpStatus.NOT_FOUND);
        }

        if (!employeeSV.isWorkedHoursRequestValid(employeeWorkedHoursInfo)) {
            return new ResponseEntity<>(employeeSV.getEmptyOperationResponse(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity<>(employeeSV.registerWorkedHours(employeeWorkedHoursInfo), HttpStatus.OK);
    }

    @GetMapping(value = "/workprofile/payment/")
    public ResponseEntity<DTORSWorkedHoursPayment> getWorkedHoursPayment(@RequestBody DTORQEmployeeWorkProfile employeeWorkProfilesSearch) {
        if (employeeWorkProfilesSearch != null && !employeeSV.exists(employeeWorkProfilesSearch.getEmployee_id())) {
            return new ResponseEntity<>(employeeSV.getWorkedHoursPaymentEmptyOperationResponse(), HttpStatus.NOT_FOUND);
        }

        if (!employeeSV.isWorkProfileSearchRequestValid(employeeWorkProfilesSearch)) {
            return new ResponseEntity<>(employeeSV.getWorkedHoursPaymentEmptyOperationResponse(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity<>(employeeSV.getWorkedHoursPayment(employeeWorkProfilesSearch), HttpStatus.OK);
    }

}
