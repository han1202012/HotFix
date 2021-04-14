package kim.hsl.hotfix;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyPermissions.requestPermissions(
                this,
                "权限申请原理对话框 : 描述申请权限的原理",
                100,

                // 下面是要申请的权限 可变参数列表
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        );

        findViewById(R.id.test_exception).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建测试类
                HotFixTest hotFixTest = new HotFixTest();
                // 调用产生异常的方法
                hotFixTest.test();
            }
        });

        findViewById(R.id.hotfix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进行热修复
                hotFix();
            }
        });
    }

    private void hotFix() {
        // 拷贝的目的文件目录
        // /data/user/0/kim.hsl.hotfix/app_odex/update.dex
        File targetDir = this.getDir("odex", Context.MODE_PRIVATE);
        // 拷贝的目的文件名称
        String targetName = "update.dex";

        // 准备目的文件, 将 Dex 文件从 SDK 卡拷贝到此文件中
        String filePath = new File(targetDir, targetName).getAbsolutePath();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        // 准备输入流, 读取 SD 卡文件
        InputStream is = null;
        // 准备输出流, 输出到目的文件
        FileOutputStream os = null;

        try {
            // 读取 SD 卡跟目录的 /storage/emulated/0/update.dex 文件
            is = new FileInputStream(new File(Environment.getExternalStorageDirectory(), targetName));
            // 输出到目标文件
            os = new FileOutputStream(filePath);

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }

            // 进行后续操作
            FixDexUtils.loadDex(this);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭 IO 流
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}