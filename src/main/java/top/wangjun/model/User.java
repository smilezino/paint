package top.wangjun.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String email;

    private String pwd;

    private String avator;

    private String nickname;

    private String intro;

    private String qq;

    private String weixin;

    private String weibo;

    private String bilibili;

    private Byte role;

    @Column(name = "active_code")
    private String activeCode;

    private Byte status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return avator
     */
    public String getAvator() {
        return avator;
    }

    /**
     * @param avator
     */
    public void setAvator(String avator) {
        this.avator = avator;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * @return qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * @param qq
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * @return weixin
     */
    public String getWeixin() {
        return weixin;
    }

    /**
     * @param weixin
     */
    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    /**
     * @return weibo
     */
    public String getWeibo() {
        return weibo;
    }

    /**
     * @param weibo
     */
    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    /**
     * @return bilibili
     */
    public String getBilibili() {
        return bilibili;
    }

    /**
     * @param bilibili
     */
    public void setBilibili(String bilibili) {
        this.bilibili = bilibili;
    }

    /**
     * @return role
     */
    public Byte getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(Byte role) {
        this.role = role;
    }

    /**
     * @return active_code
     */
    public String getActiveCode() {
        return activeCode;
    }

    /**
     * @param activeCode
     */
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    /**
     * @return status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}