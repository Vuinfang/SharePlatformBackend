package cn.hogrider.shareplatform.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer taskID;
    @Column
    private String applicant;
    @Lob
    private String details;

    public Integer getId() {
        return id;
    }

    public Application setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public Application setTaskID(Integer taskID) {
        this.taskID = taskID;
        return this;
    }

    public String getApplicant() {
        return applicant;
    }

    public Application setApplicant(String applicant) {
        this.applicant = applicant;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public Application setDetails(String details) {
        this.details = details;
        return this;
    }
}
