package space.jain.shubham.testapplication.utils;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;






/**
 * Created by Shubham.claritus on 15/9/16.
 */

public class AppApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
