package soportetest.zeus.data.dto;

import java.util.ArrayList;
import java.util.List;

public class DTORSEmployeesSearch {

    private List<DTOEmployee> employees = new ArrayList<>();
    private boolean success = false;

    public DTORSEmployeesSearch() { }

    public List<DTOEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<DTOEmployee> employees) {
        this.employees = employees;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
