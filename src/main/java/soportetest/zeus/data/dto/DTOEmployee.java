package soportetest.zeus.data.dto;

public class DTOEmployee {

    private int id = 0;
    private String name = "";
    private String last_name = "";
    private String birthdate = "";
    private DTOJob job = new DTOJob();
    private DTOGender gender = new DTOGender();

    public DTOEmployee() { }

    public DTOEmployee(int id,
                       String name,
                       String lastName,
                       String birthdate,
                       int jobId,
                       String jobName,
                       double salary,
                       int genderId,
                       String genderName) {
        this.id = id;
        this.name = name;
        this.last_name = lastName;
        this.birthdate = birthdate;
        this.job.setId(jobId);
        this.job.setName(jobName);
        this.job.setSalary(salary);
        this.gender.setId(genderId);
        this.gender.setName(genderName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public DTOJob getJob() {
        return job;
    }

    public void setJob(DTOJob job) {
        if (job == null) {
            return;
        }

        this.job = job;
    }

    public DTOGender getGender() {
        return gender;
    }

    public void setGender(DTOGender gender) {
        if (gender == null) {
            return;
        }

        this.gender = gender;
    }
}
