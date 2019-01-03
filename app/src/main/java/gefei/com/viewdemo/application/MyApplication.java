package gefei.com.viewdemo.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.mls.baseProject.application.AppContext;

/**
 * Created by chenxiuxiang on 2018/1/2.
 *
 */

public class MyApplication extends AppContext {
    @Override
    public void init() {
        super.init();
//        SDKInitializer.setCoordType(CoordType.BD09LL);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



}
