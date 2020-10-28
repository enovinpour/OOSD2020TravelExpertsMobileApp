package Model;

import java.io.Serializable;
public class Package implements Serializable{

    private int packageId;
    private String pkgImageMain;
    private String pkgName;
    private String pkgCostDesc;

    public Package(int packageId, String pkgName, String pkgImageMain, String pkgCostDesc) {
        this.pkgImageMain = pkgImageMain;
        this.pkgName = pkgName;
        this.packageId = packageId;
    }

    public Package(int packageId, String pkgName) {
        this.packageId = packageId;
        this.pkgName = pkgName;
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

    public String toString() {
        return pkgName;
    }
}
