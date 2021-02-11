package soportetest.zeus.data.dto;

public class DTORSWorkedHours {

    private Integer total_worked_hours = null;
    private boolean success = false;

    public DTORSWorkedHours() { }

    public Integer getTotal_worked_hours() {
        return total_worked_hours;
    }

    public void setTotal_worked_hours(Integer total_worked_hours) {
        this.total_worked_hours = total_worked_hours;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
