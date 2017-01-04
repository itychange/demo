package mrblack.myapplication.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Permission util
 *
 * @author TuanNT
 */
public class PermissionUtil {
    private PermissionUtil() {
        // no instance
    }

    public static boolean isStorePermissionGranted(Context context) {
        return isPermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean isCameraPermissionGranted(Context context) {
        return isPermissionGranted(context, Manifest.permission.CAMERA);
    }

    private static boolean isPermissionGranted(Context context, String permision) {
        if (ContextCompat.checkSelfPermission(context, permision) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
}
