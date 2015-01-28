package demovideoready.sampleapp.com.demovideoready;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;

/**
 * Created by ig on 5/1/15.
 */
public class MyMediaController extends MediaController {

    private FrameLayout anchorView;


    public MyMediaController(Context context, FrameLayout anchorView)
    {
        super(context);
        this.anchorView = anchorView;
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld)
    {
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) anchorView.getLayoutParams();
        lp.setMargins(0, 0, 0, yNew);

        anchorView.setLayoutParams(lp);
        anchorView.requestLayout();
    }
}
