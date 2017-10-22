package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "posts", schema = "tanglie", catalog = "")
public class PostEntity {
    private int id;
    private Integer imageId;
    private String text;
    private Integer saveCount;
    private Integer upvoteCount;
    private Long timestamp;
    private Integer userId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "imageId")
    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "saveCount")
    public Integer getSaveCount() {
        return saveCount;
    }

    public void setSaveCount(Integer saveCount) {
        this.saveCount = saveCount;
    }

    @Basic
    @Column(name = "upvoteCount")
    public Integer getUpvoteCount() {
        return upvoteCount;
    }

    public void setUpvoteCount(Integer upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    @Basic
    @Column(name = "timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "userId")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostEntity that = (PostEntity) o;

        if (id != that.id) return false;
        if (imageId != null ? !imageId.equals(that.imageId) : that.imageId != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (saveCount != null ? !saveCount.equals(that.saveCount) : that.saveCount != null) return false;
        if (upvoteCount != null ? !upvoteCount.equals(that.upvoteCount) : that.upvoteCount != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (saveCount != null ? saveCount.hashCode() : 0);
        result = 31 * result + (upvoteCount != null ? upvoteCount.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
