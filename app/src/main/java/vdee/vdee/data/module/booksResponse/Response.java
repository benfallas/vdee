package vdee.vdee.data.module.booksResponse;

import java.util.ArrayList;

public class Response
{
    private ArrayList<Book> books;

    private Meta meta;

    public ArrayList<Book> getBooks ()
    {
        return books;
    }

    public void setBooks (ArrayList<Book> books)
    {
        this.books = books;
    }

    public Meta getMeta ()
    {
        return meta;
    }

    public void setMeta (Meta meta)
    {
        this.meta = meta;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [books = "+books+", meta = "+meta+"]";
    }
}

