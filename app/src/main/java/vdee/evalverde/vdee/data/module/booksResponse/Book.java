package vdee.evalverde.vdee.data.module.booksResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import vdee.evalverde.vdee.data.module.chaptersResponse.Chapter;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChapterPayload;

public class Book
{
    @SerializedName("abbr")
    @Expose
    private String abbr;
    @SerializedName("book_group_id")
    @Expose
    private String bookGroupId;
    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = null;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("next")
    @Expose
    private Next next;
    @SerializedName("ord")
    @Expose
    private String ord;
    @SerializedName("osis_end")
    @Expose
    private String osisEnd;
    @SerializedName("parent")
    @Expose
    private Parent parent;
    @SerializedName("testament")
    @Expose
    private String testament;
    @SerializedName("version_id")
    @Expose
    private String versionId;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getBookGroupId() {
        return bookGroupId;
    }

    public void setBookGroupId(String bookGroupId) {
        this.bookGroupId = bookGroupId;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Next getNext() {
        return next;
    }

    public void setNext(Next next) {
        this.next = next;
    }

    public String getOrd() {
        return ord;
    }

    public void setOrd(String ord) {
        this.ord = ord;
    }

    public String getOsisEnd() {
        return osisEnd;
    }

    public void setOsisEnd(String osisEnd) {
        this.osisEnd = osisEnd;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getTestament() {
        return testament;
    }

    public void setTestament(String testament) {
        this.testament = testament;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("abbr", abbr).append("bookGroupId", bookGroupId).append("chapters", chapters).append("copyright", copyright).append("id", id).append("name", name).append("next", next).append("ord", ord).append("osisEnd", osisEnd).append("parent", parent).append("testament", testament).append("versionId", versionId).toString();
    }
}

