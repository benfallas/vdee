package vdee.evalverde.vdee.data.module.versesResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersesResponse {

    @SerializedName("response") @Expose
    private VersesBodyResponse response;

    public VersesBodyResponse getResponse() {
        return response;
    }

    public void setResponse(VersesBodyResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "VersesResponse " + getResponse();
    }

}
