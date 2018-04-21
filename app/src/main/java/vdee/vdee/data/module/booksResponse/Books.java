package vdee.vdee.data.module.booksResponse;

import java.util.ArrayList;

public class Books
{
    private ArrayList<Book> book;

    public ArrayList<Book> getBook ()
    {
        return book;
    }

    public void setBook (ArrayList<Book> book)
    {
        this.book = book;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [book = "+book+"]";
    }
}

