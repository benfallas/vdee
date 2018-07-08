package vdee.evalverde.vdee.data.module.chaptersResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapter {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Chapter: " + "Path: " + getPath() + " NAME: " + getName()
                + "ID: " + getId();
    }

}