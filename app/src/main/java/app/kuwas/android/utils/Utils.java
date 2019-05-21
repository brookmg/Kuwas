package app.kuwas.android.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Px;

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

}
