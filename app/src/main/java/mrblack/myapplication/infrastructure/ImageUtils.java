package mrblack.myapplication.infrastructure;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by nguyenbon on 6/30/16.
 */
public final class ImageUtils {

    private static final String TAG = "ImageUtils";

    public static Bitmap imageOrientationValidator(Bitmap bitmap, String path) {
        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                default:
                    bitmap = rotateImage(bitmap, 180);
                    break;
            }
        } catch (IOException e) {
            Log.d(TAG, "imageOrientationValidator: "+e.getMessage());
        }

        return bitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError ex) {
            Log.d(TAG, "rotateImage: "+ex.getMessage());
        }
        return bitmap;
    }

    public static byte[] bitmapToBin(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap rotationBinaryBitmap(byte[] data) {
        BitmapFactory.Options bitmap_options = new BitmapFactory.Options();
        bitmap_options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length, bitmap_options);
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(CameraUtils.findFrontFacingCameraID(), info);
        return rotate(realImage, info.orientation);
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.postRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    public static String saveFilePathOfPhoto(Bitmap rotate) {
        byte[] rotatedBinary = ImageUtils.bitmapToBin(rotate);
        File pictureFile = CameraUtils.getOutputMediaFile(CameraUtils.MEDIA_TYPE_IMAGE);
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return null;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(rotatedBinary);
            fos.close();

            Log.d(TAG, pictureFile.getPath());
            return pictureFile.getPath();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
        return null;
    }
    public static Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, null, null);
        return Uri.parse(path);
    }
    public static String getRealPathFromURI(Context context, android.net.Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

}
