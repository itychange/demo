package mrblack.myapplication.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;


public class MathUtil {
    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }

    public static int getScreenWith(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static boolean isRectOverlapRect(View view1, View view2) {
        int view1X = view1.getLeft();
        int view1Y = view1.getTop();
        int view1Width = view1.getWidth();
        int view1Height = view1.getHeight();

        int view2X = view2.getLeft();
        int view2Y = view2.getTop();
        int view2Width = view2.getWidth();
        int view2Height = view2.getHeight();

        if (view1X + view1Width < view2X
                || view1X > view2X + view2Width
                || view1Y + view1Height < view2Y
                || view1Y > view2Y + view2Height) return false;

        return true;
    }
}
