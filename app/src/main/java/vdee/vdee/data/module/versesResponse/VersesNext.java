package vdee.vdee.data.module.versesResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import vdee.vdee.data.module.PathNameId;

public class VersesNext {

    @SerializedName("verse") PathNameId mPathnameIds;

    public PathNameId getPathNameId() {
        return mPathnameIds;
    }

    public void setPathNameIds(PathNameId pathNameIds) {
        mPathnameIds = pathNameIds;
    }

    @Override
    public String toString() {
       return "Verses NEXT: " + getPathNameId();
    }
}
