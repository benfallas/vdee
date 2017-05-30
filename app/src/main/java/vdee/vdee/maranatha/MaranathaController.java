package vdee.vdee.maranatha;

public class MaranathaController {

    private MaranathaActivity mMaranathaActivity;
    private MaranathaLayout mMaranathaLayout;

    public MaranathaController(MaranathaActivity maranathaActivity) {
        mMaranathaActivity = maranathaActivity;
        mMaranathaLayout = new MaranathaLayout(mMaranathaActivity);
    }
}
