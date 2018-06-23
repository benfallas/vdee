package vdee.vdee.data.module.versesResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersesPayload {

    @SerializedName("parent") @Expose private VersesParent parent;
    @SerializedName("reference") @Expose private String mReference;
    @SerializedName("text") @Expose private String mText;
    @SerializedName("label") @Expose private String label;
    @SerializedName("auditid") @Expose private String auditid;
    @SerializedName("verse") @Expose private String chapter;
    @SerializedName("copyright") @Expose private String copyright;
    @SerializedName("osis_end") @Expose private String osisEnd;
    @SerializedName("next") @Expose private VersesNext next;
    @SerializedName("id") @Expose private String id;
    @SerializedName("lastverse") @Expose private String lastVerse;
    @SerializedName("previous") @Expose private VersesPrevious previous;

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

    public VersesParent getParent() {
        return parent;
    }

    public String getReference() {
        return mReference;
    }

    public void setReference(String reference) {
        mReference = reference;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getLastVerse() {
        return lastVerse;
    }

    public void setLastVerse(String lastVerse) {
        this.lastVerse = lastVerse;
    }

    public void setParent(VersesParent parent) {
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

    public VersesNext getNext() {
        return next;
    }

    public void setNext(VersesNext next) {
        this.next = next;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VersesPrevious getPrevious() {
        return previous;
    }

    public void setPrevious(VersesPrevious previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "ChapterPayload" + " ID: " + getId() + " Chapter: " + getChapter() + " CopryRight" + getCopyright()
                + " Parent" + getParent() + " Label: " + getLabel() + "AuditID: " + getAuditid()
                + " OASISEND" + getOsisEnd() + " NEXT: " + getNext() + " PRevious: " + getPrevious();
    }

}
