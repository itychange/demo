/*
package mrblack.myapplication.infrastructure;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mrblack.myapplication.R;


*/
/**
 * Created by nguyenbon on 6/30/16.
 *//*

public final class AppUtils {
    private final static String FURIGANA_PATTERN = "^[\\p{Hiragana}ー]+";


    public static boolean checkClassId(EditText editText) {
        if (!checkEditTextIsEmpty(editText)) {
            return false;
        }
        String classId = editText.getText().toString().trim();
        if (classId.length() < 5 || classId.length() > 5) {
           // editText.setError(editText.getContext().getString(R.string.text_class_id_invalid));
            editText.requestFocus();
            editText.setSelection(editText.length());
            return false;
        }
        return true;
    }

    public static boolean checkEditTextIsEmpty(EditText editText, String errorMessage) {
        if (TextUtils.isEmpty(errorMessage)) {
           // errorMessage = editText.getContext().getString(R.string.text_the_field_is_required);
        }
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setError(errorMessage);
            editText.requestFocus();
            editText.setSelection(editText.length());
            return false;
        }
        return true;
    }

    public static boolean checkEditTextIsEmpty(EditText editText) {
        return checkEditTextIsEmpty(editText, null);
    }

    public static boolean checkEditTextIsPassword(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            //editText.setError(editText.getContext().getString(R.string.text_please_enter_your_password));
            editText.requestFocus();
            editText.setSelection(editText.length());
            return false;
        }

        if (editText.getText().toString().trim().length() < 6) {
            editText.setError(editText.getContext().getString(R.string.text_the_password_must_be_min_6_characters));
            editText.requestFocus();
            editText.setSelection(editText.length());
            return false;
        }
        return true;
    }

    public static boolean checkEditTextIsEmail(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setError(editText.getContext().getString(R.string.text_please_enter_your_email));
            editText.requestFocus();
            editText.setSelection(editText.length());
            return false;
        }

        if (!isValidateMail(editText.getText().toString().trim())) {
            editText.setError(editText.getContext().getString(R.string.text_the_field_must_be_a_valid_email_address));
            editText.requestFocus();
            editText.setSelection(editText.length());
            return false;
        }
        return true;
    }

    public static boolean checkEditTextIsPhone(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setError(editText.getContext().getString(R.string.text_please_specify_a_valid_phone_number));
            editText.requestFocus();
            editText.setSelection(editText.length());
            return false;
        }
        return true;
    }

    public static boolean isValidateMail(String mail) {
        Pattern pat = Pattern
                .compile("^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$");
        Matcher matcher = pat.matcher(mail);
        return matcher.matches();
    }

    public static boolean isValidatePassword(String password) {
        Pattern pat = Pattern
                .compile("^(?=(?:.*[a-z])+)(?=(?:.*[A-Z])+)(?=(?:.*\\d)+)(?=(?:.*[!@#$%^&*-+/\\-'?:.\\[\\](){}~_])+).{6,}$");
        Matcher matcher = pat.matcher(password);
        return matcher.matches();
    }

    public static void setFuriganaPatternInputFilter(EditText editText) {
        LoggerUtil.LogDebug(AppUtils.class.getSimpleName(), "setFuriganaPatternInputFilter");
        editText.setFilters(new InputFilter[]{new FuriganaPatternInputFilter(FURIGANA_PATTERN) {
        }});
    }

    public static void hiddenFunctionBySettingValue(View view, int value) {
        if (value == 1) { // on
            view.setEnabled(true);
            view.setAlpha(1.0f);
        } else {
            view.setEnabled(false);
            view.setAlpha(0.5f);
        }
    }
}
*/
