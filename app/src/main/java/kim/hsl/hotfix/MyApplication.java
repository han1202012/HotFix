package kim.hsl.hotfix;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);

        // 每次启动应用都先进行修复包加载操作
        FixDexUtils.loadDex(base);

        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
