package vdee.vdee.data.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vdee.vdee.data.module.chaptersResponse.Book;

public class PathNameId {

    @SerializedName("path") @Expose private String mPath;
    @SerializedName("name") @Expose private String mName;
    @SerializedName("id") @Expose private String mId;

    public String getPath() {
        return mPath;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public String toString() {
        return "PathNameId: Path: " + getPath() + " ID: " + getId() + " Name: " + getName();
    }
}
