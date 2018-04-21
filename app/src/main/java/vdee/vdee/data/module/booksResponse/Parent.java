package vdee.vdee.data.module.booksResponse;

public class Parent
{
    private Version version;

    public Version getVersion ()
    {
        return version;
    }

    public void setVersion (Version version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [version = "+version+"]";
    }
}

