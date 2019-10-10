package vdee.evalverde.vdee.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class BiblePayload {

    @SerializedName("direction") @Expose private String direction;
    @SerializedName("type") @Expose private String type;
    @SerializedName("version") @Expose private HashMap<String, BookInfo> version;
    @SerializedName("version_ref") @Expose private String versionRef;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, BookInfo> getVersion() {
        return version;
    }

    public void setVersion(HashMap<String, BookInfo> version) {
        this.version = version;
    }

    public String getVersionRef() {
        return versionRef;
    }

    public void setVersionRef(String versionRef) {
        this.versionRef = versionRef;
    }

}
