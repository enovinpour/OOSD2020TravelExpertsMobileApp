package Model;
/*
Author: Jessy Perreault
Class Name: CPRG-220-A-Open-Source-Web-Apps
Date: November 2020
 */
import java.io.Serializable;

public class Reward implements Serializable {
    int RewardId;
    String RwdName;
    String RwdDescription;
    int PointsCost;
    String ImgName;
    int CustomerId;

    public Reward(int rewardId, String rwdName, String rwdDescription, int pointsCost, String imgName, int customerId) {
        RewardId = rewardId;
        RwdName = rwdName;
        RwdDescription = rwdDescription;
        PointsCost = pointsCost;
        ImgName = imgName;
        CustomerId = customerId;
    }


    public int getRewardId() {
        return RewardId;
    }

    public void setRewardId(int rewardId) {
        RewardId = rewardId;
    }

    public String getRwdName() {
        return RwdName;
    }

    public void setRwdName(String rwdName) {
        RwdName = rwdName;
    }

    public String getRwdDescription() {
        return RwdDescription;
    }

    public void setRwdDescription(String rwdDescription) {
        RwdDescription = rwdDescription;
    }

    public int getPointsCost() {
        return PointsCost;
    }

    public void setPointsCost(int pointsCost) {
        PointsCost = pointsCost;
    }

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    @Override
    public String toString() {
        return "Reward Name: " + RwdName
                + "\n Reward Description: " + RwdDescription
                + "\n Points Cost: " + PointsCost;
    }
}
