package mrblack.myapplication.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright © 2016 Neo-Lab Co.,Ltd.
 * Created by TanHN on 6/2/2016.
 */
public class StringUtil {

    private static final String TAG = StringUtil.class.getSimpleName();

    public static String getCurrentDateTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    // from dddd-mm-dd HH:mm:ss -> m月dd日 HH:mm
    public static String convertFormatTime(String time) {
        String[] tempDate = time.split(" ")[0].split("-");
        String date = Integer.valueOf(tempDate[1]) + "月" + Integer.valueOf(tempDate[2]) + "日";
        String minute = time.split(" ")[1].substring(0, 5);
        return (date + " " + minute);
    }

    @SuppressWarnings("ConstantConditions")
    public static String getRealPathFromURI(Context context, android.net.Uri uri) {
        if (uri == null) {
            Log.d("-=======", "null");
            return null;
        }
        String url = "";
        String[] projection;
        if (Build.VERSION.SDK_INT >= 19) {
            projection = new String[]{MediaStore.Images.Media.DATA};
        } else {
            projection = new String[]{android.provider.MediaStore.Images.ImageColumns.DATA};
        }

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            cursor.moveToFirst();
            url = cursor.getString(column_index);
            cursor.close();
            Log.d(TAG, "getRealPathFromURI: " + url);
        }
        return url;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // 5月23日（木)
    public static String formatCurrentDatetime() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return month + "月" + dayOfMonth + "日" + " (" + formatDayOfWeek(dayOfWeek) + ")";
    }

    public static String formatDayOfWeek(int dayOfWeek){
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return  "月";
            case Calendar.TUESDAY:
                return  "火";
            case Calendar.WEDNESDAY:
                return  "水";
            case Calendar.THURSDAY:
                return  "木";
            case Calendar.FRIDAY:
                return  "金";
            case Calendar.SATURDAY:
                return  "土";
            case Calendar.SUNDAY:
                return  "日";
            default: return "";
        }
    }

    public static String convertFormatTime2(String date) { //2016-09-13 07:50:30 ==> 2016年07月13日
        String[] formatted = date.split(" ")[0].split("-");
        return formatted[0] + "年" + formatted[1] + "月" + formatted[2] + "日";
    }
    //add time
    public static String convertDayTime(String dateInString, int day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dateInString));
        c.add(Calendar.DATE,day);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date resultday = new Date(c.getTimeInMillis());
        dateInString = sdf.format(resultday);
        return dateInString;
    }

    public static String formatMoney(int money) { //1.2345
        String result = "";
        while (money > 1000) {
            result += "." + String.valueOf(money % 1000);
            money = money % 1000;
        }
        return TextUtils.isEmpty(result) ? String.valueOf(money) : money + result;
    }
}
