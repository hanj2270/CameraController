package camera.hj.cameracontroller.controller.entity;

import java.io.Serializable;

/**
 * Created by user on 2017/12/30.
 * 运动的信息
 */
public class SportItemEntity implements Serializable {
    private int sportImgIcon;
    private String sportItemName;
    private int sportId;

    private String videoUrl;

    public SportItemEntity(){}

    public SportItemEntity(int sportId, String sportItemName, int sportImgIcon, String videoUrl){
        this.sportId=sportId;
        this.sportItemName=sportItemName;
        this.sportImgIcon=sportImgIcon;
        this.videoUrl=videoUrl;
    }

    public int getSportImgIcon() {
        return sportImgIcon;
    }

    public void setSportImgIcon(int sportImgIcon) {
        this.sportImgIcon = sportImgIcon;
    }

    public String getSportItemName() {
        return sportItemName;
    }

    public void setSportItemName(String sportItemName) {
        this.sportItemName = sportItemName;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
