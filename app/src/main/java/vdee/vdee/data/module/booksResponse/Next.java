package vdee.vdee.data.module.booksResponse;

public class Next
{
    private Book book;

    public Book getBook ()
    {
        return book;
    }

    public void setBook (Book book)
    {
        this.book = book;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [book = "+book+"]";
    }
}

