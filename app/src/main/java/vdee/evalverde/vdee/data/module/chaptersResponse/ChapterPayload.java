package vdee.evalverde.vdee.data.module.chaptersResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChapterPayload {

    @SerializedName("chapter") @Expose private String chapter;
    @SerializedName("copyright") @Expose private String copyright;
    @SerializedName("parent") @Expose private Parent parent;
    @SerializedName("label") @Expose private String label;
    @SerializedName("auditid") @Expose private String auditid;
    @SerializedName("osis_end") @Expose private String osisEnd;
    @SerializedName("next") @Expose private Next next;
    @SerializedName("id") @Expose private String id;
    @SerializedName("previous") @Expose private Previous previous;

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAuditid() {
        return auditid;
    }

    public void setAuditid(String auditid) {
        this.auditid = auditid;
    }

    public String getOsisEnd() {
        return osisEnd;
    }

    public void setOsisEnd(String osisEnd) {
        this.osisEnd = osisEnd;
    }

    public Next getNext() {
        return next;
    }

    public void setNext(Next next) {
        this.next = next;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Previous getPrevious() {
        return previous;
    }

    public void setPrevious(Previous previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "ChapterPayload" + " ID: " + getId() + " Chapter: " + getChapter() + " CopryRight" + getCopyright()
                + " Parent" + getParent() + " Label: " + getLabel() + "AuditID: " + getAuditid()
                + " OASISEND" + getOsisEnd() + " NEXT: " + getNext() + " PRevious: " + getPrevious();
    }
}