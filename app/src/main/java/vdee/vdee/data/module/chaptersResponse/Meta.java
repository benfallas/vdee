package vdee.vdee.data.module.chaptersResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("fums_js_include")
    @Expose
    private String fumsJsInclude;
    @SerializedName("fums_noscript")
    @Expose
    private String fumsNoscript;
    @SerializedName("fums_js")
    @Expose
    private String fumsJs;
    @SerializedName("fums_tid")
    @Expose
    private String fumsTid;
    @SerializedName("fums")
    @Expose
    private String fums;

    public String getFumsJsInclude() {
        return fumsJsInclude;
    }

    public void setFumsJsInclude(String fumsJsInclude) {
        this.fumsJsInclude = fumsJsInclude;
    }

    public String getFumsNoscript() {
        return fumsNoscript;
    }

    public void setFumsNoscript(String fumsNoscript) {
        this.fumsNoscript = fumsNoscript;
    }

    public String getFumsJs() {
        return fumsJs;
    }

    public void setFumsJs(String fumsJs) {
        this.fumsJs = fumsJs;
    }

    public String getFumsTid() {
        return fumsTid;
    }

    public void setFumsTid(String fumsTid) {
        this.fumsTid = fumsTid;
    }

    public String getFums() {
        return fums;
    }

    public void setFums(String fums) {
        this.fums = fums;
    }

    @Override
    public String toString() {
        return "Meta: " + " FumsJsInclude: " + getFumsJsInclude()
                + "FumsNoScript: " + getFumsNoscript()
                + "FumsJS" + getFumsJs()
                + "FumsTid: " + getFumsTid()
                + "FUMS: " + getFums();
    }
}