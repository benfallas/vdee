package vdee.evalverde.vdee.data.module.booksResponse;

public class Version
{
    private String id;
    private String name;
    private String path;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getPath ()
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
        return "ClassPojo [id = "+id+", name = "+name+", path = "+path+"]";
    }
}

