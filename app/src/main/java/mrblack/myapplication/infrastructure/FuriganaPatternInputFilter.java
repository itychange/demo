package mrblack.myapplication.infrastructure;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by nguyenbon on 9/17/16.
 */
public class FuriganaPatternInputFilter implements InputFilter {

    private Pattern mPattern;

    public FuriganaPatternInputFilter(String pattern) {
        try {
            mPattern = Pattern.compile(pattern);
            Log.d(FuriganaPatternInputFilter.class.getSimpleName(), "mPattern onCreated");
        } catch (PatternSyntaxException exception) {
            exception.printStackTrace();

        }

    }

    @Override
    public CharSequence filter(CharSequence source,
                               int sourceStart, int sourceEnd,
                               Spanned destination, int destinationStart,
                               int destinationEnd) {
        String commentToCheck = destination.subSequence(0, destinationStart).
                toString() + source.subSequence(sourceStart, sourceEnd) +
                destination.subSequence(
                        destinationEnd, destination.length()).toString();
        if (mPattern != null) {
            Matcher matcher = mPattern.matcher(commentToCheck);
            // Entered text does not match the pattern
            if (!matcher.matches()) {
                // It does not match partially too
                if (!matcher.hitEnd()) {
                    return "";
                }
            }
        }
        return null;
    }

}
