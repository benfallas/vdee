package vdee.evalverde.vdee.views.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class VdeeChaptersGrid extends GridView {

    public VdeeChaptersGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VdeeChaptersGrid(Context context) {
        super(context);
    }

    public VdeeChaptersGrid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
