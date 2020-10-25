package Model;

import java.io.Serializable;
public class Package implements Serializable{

    private Integer pkgImageMain;
    private String pkgName;

    //public Package(Integer pkgImageMain) {
        //this.pkgImageMain = pkgImageMain;
    //}

    public Package(Integer pkgImageMain, String pkgName) {
        this.pkgImageMain = pkgImageMain;
        this.pkgName = pkgName;
    }

    public Integer getPkgImageMain() {
        return pkgImageMain;
    }

    public void setPkgImageMain(Integer pkgImageMain) {
        this.pkgImageMain = pkgImageMain;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
