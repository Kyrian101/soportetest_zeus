package soportetest.zeus.data.dto;

public class DTORSWorkedHoursPayment {

    private Double payment = null;
    private boolean success = false;

    public DTORSWorkedHoursPayment() { }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
