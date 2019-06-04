package app.kuwas.android.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Px;

import java.util.Date;

/**
 * Created by BrookMG on 5/20/2019 in app.kuwas.android.utils
 * inside the project Kuwas .
 */
public class Utils {

    @SuppressWarnings("SameParameterValue")
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @SuppressWarnings("unused")
    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Function to set margin to a view
     * @param view -> view to be tempered
     * @param l -> left
     * @param t -> top
     * @param r -> right
     * @param b -> bottom
     */
    public static void setMargins(View view, @Px int l, @Px int t, @Px int r, @Px int b) {
        if (view.getLayoutParams() instanceof LinearLayout.MarginLayoutParams) {
            LinearLayout.MarginLayoutParams params = (LinearLayout.MarginLayoutParams) view.getLayoutParams();
            params.setMargins(l, t, r, b);
            view.setLayoutParams(params);
            view.requestLayout();
        }
    }

    /**
     * Function to get the time gap between now and the timestamp given
     * @param fromTimeMil - the milli-sec timestamp value to compute gap from
     * @return the time gap: eg "2d ago" , "just now", "43m ago"
     */
    public static String getTimeGap(long fromTimeMil) {

        long diff = (new Date().getTime()) - fromTimeMil;

        if (diff < DateUtils.MINUTE_IN_MILLIS)
        { return "just now"; }
        else if (diff < DateUtils.HOUR_IN_MILLIS)
        { return Math.round((float) diff/DateUtils.MINUTE_IN_MILLIS) + "m ago"; }
        else if (diff < DateUtils.DAY_IN_MILLIS)
        { return Math.round((float) diff/DateUtils.HOUR_IN_MILLIS) + "h ago"; }
        else if (diff < DateUtils.WEEK_IN_MILLIS)
        { return Math.round((float) diff/DateUtils.DAY_IN_MILLIS) + "d ago"; }
        else if (diff < DateUtils.YEAR_IN_MILLIS)
        { return Math.round((float) diff/DateUtils.WEEK_IN_MILLIS) + "w ago"; }
        else
        { return Math.round((float) diff/DateUtils.YEAR_IN_MILLIS) + "y ago"; }

    }

}
