package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "favorites", schema = "tanglie", catalog = "")
public class FavoriteEntity {
    private long timestamp;
    private int id;
    private int postId;
    private int userId;

    @Id
    @Column(name = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoriteEntity that = (FavoriteEntity) o;

        if (timestamp != that.timestamp) return false;
        if (id != that.id) return false;
        if (postId != that.postId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + id;
        result = 31 * result + postId;
        result = 31 * result + userId;
        return result;
    }
}
