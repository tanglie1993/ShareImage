package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "images", schema = "tanglie", catalog = "")
public class ImagesEntity {
    private Integer userId;
    private int id;
    private Long timestamp;
    private Double bytes;
    private Integer width;
    private Integer height;
    private String format;

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    @Basic
    @Column(name = "timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "bytes")
    public Double getBytes() {
        return bytes;
    }

    public void setBytes(Double bytes) {
        this.bytes = bytes;
    }

    @Basic
    @Column(name = "width")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Basic
    @Column(name = "height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImagesEntity that = (ImagesEntity) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (bytes != null ? !bytes.equals(that.bytes) : that.bytes != null) return false;
        if (width != null ? !width.equals(that.width) : that.width != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;
        if (format != null ? !format.equals(that.format) : that.format != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (bytes != null ? bytes.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        return result;
    }
}
