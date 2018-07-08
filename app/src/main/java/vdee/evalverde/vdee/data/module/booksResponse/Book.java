package vdee.evalverde.vdee.data.module.booksResponse;

public class Book
{
    private String id;
    private String book_group_id;
    private String testament;
    private Previous previous;
    private String name;
    private Next next;
    private String abbr;
    private String ord;
    private Parent parent;
    private String osis_end;
    private String copyright;
    private String version_id;
    private String path;


    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getBook_group_id ()
    {
        return book_group_id;
    }

    public void setBook_group_id (String book_group_id)
    {
        this.book_group_id = book_group_id;
    }

    public String getTestament ()
    {
        return testament;
    }

    public void setTestament (String testament)
    {
        this.testament = testament;
    }

    public Previous getPrevious ()
    {
        return previous;
    }

    public void setPrevious (Previous previous)
    {
        this.previous = previous;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Next getNext ()
    {
        return next;
    }

    public void setNext (Next next)
    {
        this.next = next;
    }

    public String getAbbr ()
    {
        return abbr;
    }

    public void setAbbr (String abbr)
    {
        this.abbr = abbr;
    }

    public String getOrd ()
    {
        return ord;
    }

    public void setOrd (String ord)
    {
        this.ord = ord;
    }

    public Parent getParent ()
    {
        return parent;
    }

    public void setParent (Parent parent)
    {
        this.parent = parent;
    }

    public String getOsis_end ()
    {
        return osis_end;
    }

    public void setOsis_end (String osis_end)
    {
        this.osis_end = osis_end;
    }

    public String getCopyright ()
    {
        return copyright;
    }

    public void setCopyright (String copyright)
    {
        this.copyright = copyright;
    }

    public String getVersion_id ()
    {
        return version_id;
    }

    public void setVersion_id (String version_id)
    {
        this.version_id = version_id;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath (String path)
    {
        this.path = path;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", book_group_id = "+book_group_id+", testament = "+testament+", previous = "+previous+", name = "+name+", next = "+next+", abbr = "+abbr+", ord = "+ord+", parent = "+parent+", osis_end = "+osis_end+", copyright = "+copyright+", version_id = "+version_id+", path = "+path+"]";
    }
}

