package app.kuwas.android.utils;

import androidx.annotation.IntDef;

/**
 * Created by BrookMG on 5/18/2019 in app.kuwas.android.utils
 * inside the project Kuwas .
 */

public class FabStates {

    @IntDef(value = {STATE_EXPAND, STATE_COLLAPSE, STATE_HIDE, STATE_SHOW})
    public @interface FabState {}

    public static final int STATE_EXPAND = 1;
    public static final int STATE_COLLAPSE = 2;
    public static final int STATE_HIDE = 3;
    public static final int STATE_SHOW = 4;
}
