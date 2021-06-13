package cn.hogrider.shareplatform.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", length = 100)
    private String title;
    @Column
    private Integer price;
//    @Column(name = "details",columnDefinition = "TEXT",length = 65535)
    @Lob
    private String details;
    @Column(name = "tag", length = 100)
    private String tag;
    @Column
    private String username;

    public Integer getId() {
        return id;
    }

    public Task setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public Task setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public Task setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public Task setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Task setUsername(String username) {
        this.username = username;
        return this;
    }
}
