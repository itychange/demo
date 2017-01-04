package mrblack.myapplication.baseactivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by PC on 12/12/2016.
 */

public class BaseActivity extends Activity {
    private static final int PERMISSION_ACCESS_EXTERNAL_STORAGE = 102;
    public final String TAG = this.getClass().getSimpleName();
    private boolean isGrantPermission = false;

    public boolean isGrantPermission() {
        return isGrantPermission;
    }
    public void setGrantPermission(boolean grantPermission) {
        isGrantPermission = grantPermission;
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAllPermission();
    }

    public void checkAllPermission() {
        checkWriteExternalPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    isGrantPermission = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    isGrantPermission = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    protected void checkWriteExternalPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            isGrantPermission = false;
            // You need to ask the user to enable the permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_ACCESS_EXTERNAL_STORAGE);
        } else {
            isGrantPermission = true;
        }
    }
}
