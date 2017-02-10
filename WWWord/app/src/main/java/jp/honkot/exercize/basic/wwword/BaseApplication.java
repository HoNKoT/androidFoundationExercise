package jp.honkot.exercize.basic.wwword;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import jp.honkot.exercize.basic.wwword.di.AppComponent;
import jp.honkot.exercize.basic.wwword.di.AppModule;
import jp.honkot.exercize.basic.wwword.di.DaggerAppComponent;
import jp.honkot.exercize.basic.wwword.util.Debug;

public class BaseApplication extends Application {
    AppComponent appComponent;

    @NonNull
    public AppComponent getComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        long start = System.currentTimeMillis();
        MultiDex.install(this);
        Debug.Log("BaseApplication MultiDex.install " + (System.currentTimeMillis() - start));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        long start = System.currentTimeMillis();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        Debug.Log("BaseApplication DaggerAppComponent.builder() " + (System.currentTimeMillis() - start));

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
