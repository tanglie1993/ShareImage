package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "tanglie", catalog = "")
public class UserEntity {
    private String nickname;
    private int id;
    private String introduction;
    private Byte gender;
    private Integer avatarId;
    private Long registerTimestamp;

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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
    @Column(name = "introduction")
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Basic
    @Column(name = "gender")
    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "avatarId")
    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    @Basic
    @Column(name = "registerTimestamp")
    public Long getRegisterTimestamp() {
        return registerTimestamp;
    }

    public void setRegisterTimestamp(Long registerTimestamp) {
        this.registerTimestamp = registerTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (introduction != null ? !introduction.equals(that.introduction) : that.introduction != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (avatarId != null ? !avatarId.equals(that.avatarId) : that.avatarId != null) return false;
        if (registerTimestamp != null ? !registerTimestamp.equals(that.registerTimestamp) : that.registerTimestamp != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nickname != null ? nickname.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (avatarId != null ? avatarId.hashCode() : 0);
        result = 31 * result + (registerTimestamp != null ? registerTimestamp.hashCode() : 0);
        return result;
    }
}
