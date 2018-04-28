package vdee.vdee.data.module.chaptersResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChaptersResponse {

    @SerializedName("response") @Expose private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ChaptersResponse" + getResponse();
    }

}