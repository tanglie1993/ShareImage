package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "upvotes", schema = "tanglie", catalog = "")
public class UpvoteEntity {
    private long timestamp;
    private int postId;
    private int userId;
    private int id;

    @Basic
    @Column(name = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "postId")
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpvoteEntity that = (UpvoteEntity) o;

        if (timestamp != that.timestamp) return false;
        if (postId != that.postId) return false;
        if (userId != that.userId) return false;
        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + postId;
        result = 31 * result + userId;
        result = 31 * result + id;
        return result;
    }
}
