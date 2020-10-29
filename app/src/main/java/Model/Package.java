package Model;

import java.io.Serializable;
import java.sql.Date;

public class Package implements Serializable{

    private int packageId;
    private String pkgImageMain, pkgName, pkgCostDesc;
    private String PkgCurrencyType, PkgFamilyFriendly;
    private String PkgFoodIncluded, PkgHotelIncluded , PkgDesc, PkgLongDesc;
    private String PkgImageMap, PkgLocation, PkgImageHotel, PkgHotelDesc;
    private String pkgFoodDesc, pkgImageFood;
    private double PkgBasePrice, pkgAgencyCommission;
    private String pkgStartDate, pkgEndDate;

    public Package(int packageId, String pkgName, String pkgImageMain, String pkgCostDesc) {
        this.pkgImageMain = pkgImageMain;
        this.pkgName = pkgName;
        this.packageId = packageId;
    }

    public Package(int packageId, String pkgName) {
        this.packageId = packageId;
        this.pkgName = pkgName;
    }

    public Package(int packageId, String pkgName, double pkgBasePrice, String pkgStartDate, String pkgEndDate, double pkgAgencyCommission) {
        this.packageId = packageId;
        this.pkgName = pkgName;
        PkgBasePrice = pkgBasePrice;
        this.pkgStartDate = pkgStartDate;
        this.pkgEndDate = pkgEndDate;
        this.pkgAgencyCommission = pkgAgencyCommission;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPkgImageMain() {
        return pkgImageMain;
    }

    public void setPkgImageMain(String pkgImageMain) {
        this.pkgImageMain = pkgImageMain;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getPkgCostDesc() {
        return pkgCostDesc;
    }

    public void setPkgCostDesc(String pkgCostDesc) {
        this.pkgCostDesc = pkgCostDesc;
    }

    public String getPkgCurrencyType() {
        return PkgCurrencyType;
    }

    public void setPkgCurrencyType(String pkgCurrencyType) {
        PkgCurrencyType = pkgCurrencyType;
    }

    public double getPkgBasePrice() {
        return PkgBasePrice;
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        PkgBasePrice = pkgBasePrice;
    }

    public String getPkgFamilyFriendly() {
        return PkgFamilyFriendly;
    }

    public void setPkgFamilyFriendly(String pkgFamilyFriendly) {
        PkgFamilyFriendly = pkgFamilyFriendly;
    }

    public String getPkgFoodIncluded() {
        return PkgFoodIncluded;
    }

    public void setPkgFoodIncluded(String pkgFoodIncluded) {
        PkgFoodIncluded = pkgFoodIncluded;
    }

    public String getPkgHotelIncluded() {
        return PkgHotelIncluded;
    }

    public void setPkgHotelIncluded(String pkgHotelIncluded) {
        PkgHotelIncluded = pkgHotelIncluded;
    }

    public String getPkgDesc() {
        return PkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        PkgDesc = pkgDesc;
    }

    public String getPkgLongDesc() {
        return PkgLongDesc;
    }

    public void setPkgLongDesc(String pkgLongDesc) {
        PkgLongDesc = pkgLongDesc;
    }

    public String getPkgImageMap() {
        return PkgImageMap;
    }

    public void setPkgImageMap(String pkgImageMap) {
        PkgImageMap = pkgImageMap;
    }

    public String getPkgLocation() {
        return PkgLocation;
    }

    public void setPkgLocation(String pkgLocation) {
        PkgLocation = pkgLocation;
    }

    public String getPkgImageHotel() {
        return PkgImageHotel;
    }

    public void setPkgImageHotel(String pkgImageHotel) {
        PkgImageHotel = pkgImageHotel;
    }

    public String getPkgHotelDesc() {
        return PkgHotelDesc;
    }

    public void setPkgHotelDesc(String pkgHotelDesc) {
        PkgHotelDesc = pkgHotelDesc;
    }

    public String getPkgFoodDesc() {
        return pkgFoodDesc;
    }

    public void setPkgFoodDesc(String pkgFoodDesc) {
        this.pkgFoodDesc = pkgFoodDesc;
    }

    public String toString() {
        return pkgName;
    }

    public String getPkgStartDate() {
        return pkgStartDate;
    }

    public void setPkgStartDate(String pkgStartDate) {
        this.pkgStartDate = pkgStartDate;
    }

    public String getPkgEndDate() {
        return pkgEndDate;
    }

    public void setPkgEndDate(String pkgEndDate) {
        this.pkgEndDate = pkgEndDate;
    }

    public double getPkgAgencyCommission() {
        return pkgAgencyCommission;
    }

    public String getPkgImageFood() {
        return pkgImageFood;
    }

    public void setPkgImageFood(String pkgImageFood) {
        this.pkgImageFood = pkgImageFood;
    }

    public void setPkgAgencyCommission(double pkgAgencyCommission) {
        this.pkgAgencyCommission = pkgAgencyCommission;
    }
}
