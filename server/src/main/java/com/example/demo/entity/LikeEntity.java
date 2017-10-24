package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "likes", schema = "tanglie", catalog = "")
public class LikeEntity {
    private int id;
    private int postId;
    private int userId;
    private long timestamp;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "post_id")
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LikeEntity that = (LikeEntity) o;

        if (id != that.id) return false;
        if (postId != that.postId) return false;
        if (userId != that.userId) return false;
        if (timestamp != that.timestamp) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + postId;
        result = 31 * result + userId;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}
