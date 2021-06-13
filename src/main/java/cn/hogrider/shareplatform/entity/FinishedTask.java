package cn.hogrider.shareplatform.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class FinishedTask {

    @Id
    private Integer id;
    @Column(name = "title", length = 100)
    private String title;
    @Column
    private Integer price;
    @Lob
    private String details;
    @Column(name = "tag", length = 100)
    private String tag;
    @Column
    private String username;

    public Integer getId() {
        return id;
    }

    public FinishedTask setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FinishedTask setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public FinishedTask setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public FinishedTask setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public FinishedTask setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public FinishedTask setUsername(String username) {
        this.username = username;
        return this;
    }
}
