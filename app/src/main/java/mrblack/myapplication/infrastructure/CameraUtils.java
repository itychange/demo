package mrblack.myapplication.infrastructure;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by nguyenbon on 8/29/16.
 */
public final class CameraUtils {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String FILE_NAME = "vn.neolab.photosharing.photo";
    private static final double ASPECT_TOLERANCE = 0.1;

    /**
     * Check if this device has a camera
     */
    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /**
     * Create a file Uri for saving an image or video
     */
    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), FILE_NAME);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static int findFrontFacingCameraID() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = android.hardware.Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
            android.hardware.Camera.getCameraInfo(i, info);
            if (info.facing == android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    public static Camera.Size getOptimalPreviewSize(int displayOrientation,
                                                    int width,
                                                    int height,
                                                    Camera.Parameters parameters) {
        double targetRatio = (double) width / height;
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = height;

        if (displayOrientation == 90 || displayOrientation == 270) {
            targetRatio = (double) height / width;
        }

        // Try to find an size match aspect ratio and size

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;

            if (Math.abs(ratio - targetRatio) <= ASPECT_TOLERANCE) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        // Cannot find the one match the aspect ratio, ignore
        // the requirement

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;

            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        return (optimalSize);
    }

    public static Camera.Size getBestAspectPreviewSize(int displayOrientation,
                                                       int width,
                                                       int height,
                                                       Camera.Parameters parameters) {
        return (getBestAspectPreviewSize(displayOrientation, width, height,
                parameters, 0.0d));
    }

    public static Camera.Size getBestAspectPreviewSize(int displayOrientation,
                                                       int width,
                                                       int height,
                                                       Camera.Parameters parameters,
                                                       double closeEnough) {
        double targetRatio = (double) width / height;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        if (displayOrientation == 90 || displayOrientation == 270) {
            targetRatio = (double) height / width;
        }

        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();

        Collections.sort(sizes,
                Collections.reverseOrder(new SizeComparator()));

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;

            if (Math.abs(ratio - targetRatio) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(ratio - targetRatio);
            }

            if (minDiff < closeEnough) {
                break;
            }
        }

        return (optimalSize);
    }

    private static class SizeComparator implements
            Comparator<Camera.Size> {
        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            int left = lhs.width * lhs.height;
            int right = rhs.width * rhs.height;

            if (left < right) {
                return (-1);
            } else if (left > right) {
                return (1);
            }

            return (0);
        }
    }
}
