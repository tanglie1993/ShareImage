package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "images", schema = "tanglie", catalog = "")
public class ImageEntity {
    private int id;
    private long timestamp;
    private double bytes;
    private int width;
    private int height;
    private String format;
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
    @Column(name = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "bytes")
    public double getBytes() {
        return bytes;
    }

    public void setBytes(double bytes) {
        this.bytes = bytes;
    }

    @Basic
    @Column(name = "width")
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Basic
    @Column(name = "height")
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Basic
    @Column(name = "format")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Basic
    @Column(name = "user_id")
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

        ImageEntity that = (ImageEntity) o;

        if (id != that.id) return false;
        if (timestamp != that.timestamp) return false;
        if (Double.compare(that.bytes, bytes) != 0) return false;
        if (width != that.width) return false;
        if (height != that.height) return false;
        if (format != null ? !format.equals(that.format) : that.format != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        temp = Double.doubleToLongBits(bytes);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
