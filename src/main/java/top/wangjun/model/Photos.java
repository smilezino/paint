package top.wangjun.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Photos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer user;

    private Integer album;

    private String name;

    private String filename;

    private String filepath;

    private Integer width;

    private Integer height;

    private String thumb;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "download_count")
    private Integer downloadCount;

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
     * @return user
     */
    public Integer getUser() {
        return user;
    }

    /**
     * @param user
     */
    public void setUser(Integer user) {
        this.user = user;
    }

    /**
     * @return album
     */
    public Integer getAlbum() {
        return album;
    }

    /**
     * @param album
     */
    public void setAlbum(Integer album) {
        this.album = album;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * @param filepath
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * @return width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * @param thumb
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * @return view_count
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * @param viewCount
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * @return download_count
     */
    public Integer getDownloadCount() {
        return downloadCount;
    }

    /**
     * @param downloadCount
     */
    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
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