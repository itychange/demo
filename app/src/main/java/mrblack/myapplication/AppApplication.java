package mrblack.myapplication;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by PC on 1/2/2017.
 */
public class AppApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
