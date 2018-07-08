package vdee.evalverde.vdee.data.module.versesResponse;

import com.google.gson.annotations.SerializedName;

import vdee.evalverde.vdee.data.module.PathNameId;

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
