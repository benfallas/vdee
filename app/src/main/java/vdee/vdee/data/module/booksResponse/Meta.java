package vdee.vdee.data.module.booksResponse;

public class Meta
{
    private String fums_noscript;
    private String fums_js_include;
    private String fums_js;
    private String fums;
    private String fums_tid;

    public String getFums_noscript ()
    {
        return fums_noscript;
    }

    public void setFums_noscript (String fums_noscript)
    {
        this.fums_noscript = fums_noscript;
    }

    public String getFums_js_include ()
    {
        return fums_js_include;
    }

    public void setFums_js_include (String fums_js_include)
    {
        this.fums_js_include = fums_js_include;
    }

    public String getFums_js ()
    {
        return fums_js;
    }

    public void setFums_js (String fums_js)
    {
        this.fums_js = fums_js;
    }

    public String getFums ()
    {
        return fums;
    }

    public void setFums (String fums)
    {
        this.fums = fums;
    }

    public String getFums_tid ()
    {
        return fums_tid;
    }

    public void setFums_tid (String fums_tid)
    {
        this.fums_tid = fums_tid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [fums_noscript = "+fums_noscript+", fums_js_include = "+fums_js_include+", fums_js = "+fums_js+", fums = "+fums+", fums_tid = "+fums_tid+"]";
    }
}

