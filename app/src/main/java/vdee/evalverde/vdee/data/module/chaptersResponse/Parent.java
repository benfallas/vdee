package vdee.evalverde.vdee.data.module.chaptersResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {

    @SerializedName("book") @Expose private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Parent Book: " + getBook();
    }
}