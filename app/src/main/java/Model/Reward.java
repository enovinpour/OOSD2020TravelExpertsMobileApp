package Model;

public class Reward {
    int RewardId;
    String RwdName;
    String RwdDesc;
    int PointsCost;

    public Reward(int rewardId, String rwdName, String rwdDesc, int pointsCost) {
        RewardId = rewardId;
        RwdName = rwdName;
        RwdDesc = rwdDesc;
        PointsCost = pointsCost;
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

    public String getRwdDesc() {
        return RwdDesc;
    }

    public void setRwdDesc(String rwdDesc) {
        RwdDesc = rwdDesc;
    }

    public int getPointsCost() {
        return PointsCost;
    }

    public void setPointsCost(int pointsCost) {
        PointsCost = pointsCost;
    }

    @Override
    public String toString() {
        return "Reward Name: " + RwdName
                + "\n Reward Description: " + RwdDesc
                + "\n Points Cost: " + PointsCost;
    }
}
