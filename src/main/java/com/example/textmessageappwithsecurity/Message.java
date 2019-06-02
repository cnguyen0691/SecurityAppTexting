package com.example.textmessageappwithsecurity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min=2)
    private String title;

    @NotNull
    @Size(min=1)
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate postedDate;

    @NotNull
    private String postedBy;

    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    public Message() {
        postedDate = LocalDate.now();
        user = new User();
    }


    public Message(@NotNull @Size(min = 2) String title,
                   @NotNull @Size(min = 1) String content,
                   LocalDate postedDate,
                   @NotNull String postedBy,
                   String image,
                   User user) {
        this.title = title;
        this.content = content;
        this.postedDate = postedDate;
        this.postedBy = postedBy;
        this.image = image;
        this.user=user;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}