package soportetest.zeus.data.dto;

public class DTORQEmployeeWorkedHours {

    private int employee_id = 0;
    private int worked_hours = 0;
    private String worked_date = "";

    public DTORQEmployeeWorkedHours() { }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getWorked_hours() {
        return worked_hours;
    }

    public void setWorked_hours(int worked_hours) {
        this.worked_hours = worked_hours;
    }

    public String getWorked_date() {
        return worked_date;
    }

    public void setWorked_date(String worked_date) {
        this.worked_date = worked_date;
    }
}
