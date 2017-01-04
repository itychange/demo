package mrblack.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * Copyright © 2016 Neo-Lab Co.,Ltd.
 * Created by TanHN on 6/2/2016.
 */
public final class KeyboardUtil {

    private KeyboardUtil() {
        // no-op
    }

    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard(@NonNull Activity activity) {
        if (activity.getWindow() == null || activity.getWindow().getDecorView() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * Method used to detect SoftKeyboard is hide or show
     * Please set android:windowSoftInputMode="adjustResize" for the activity before using this.
     *
     * @param activityRootView is a root view of the activity
     */
    public static void setOnVisibilityChangeListener(final View activityRootView, final OnVisibilityChangeListener l) {
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rectContainer = new Rect();
                // r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(rectContainer);

                int heightDiff = activityRootView.getRootView().getHeight() - (rectContainer.bottom - rectContainer.top);
                Resources res = activityRootView.getResources();
                // The status bar is 25dp, use 50dp for assurance
                float maxDiff = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, res.getDisplayMetrics());
                // Lollipop includes button bar in the root. Add height of button bar (48dp) to maxDiff
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    float heightButtonBar = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, res.getDisplayMetrics());
                    maxDiff += heightButtonBar;
                }
                // if more than 100 pixels, its probably a keyboard.
                if (heightDiff > maxDiff) {
                    l.onShowKeyboard();
                } else {
                    l.onHideKeyboard();
                }
            }
        });
    }

    /**
     * Interface is used to know if keyboard is show or hide.
     */
    public interface OnVisibilityChangeListener {
        void onShowKeyboard();

        void onHideKeyboard();
    }
}
