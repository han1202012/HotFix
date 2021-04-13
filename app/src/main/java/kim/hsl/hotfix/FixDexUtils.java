package kim.hsl.hotfix;

import android.content.Context;

import java.io.File;
import java.util.HashSet;

public class FixDexUtils {

    /**
     * 存储已经加载的若干 Dex 文件
     */
    private static HashSet<File> mLoadedDexes = new HashSet<>();

    /**
     * 加载 Dex 文件
     * @param context
     */
    public static void loadDex(Context context){
        // 修复包可能有多个, 如先后进行了多次修复 , 存在多个修复包 Dex 文件
        // 这些 Dex 文件按照时间顺序进行放置
        // 之前已经将 SD 卡中的 /storage/emulated/0/update.dex 文件拷贝到了
        // 原应用内置存储空间 /data/user/0/kim.hsl.hotfix/app_odex/update.dex

        // /data/user/0/kim.hsl.hotfix/app_odex/ 目录文件
        File filesDir = context.getDir("odex", Context.MODE_PRIVATE);

        // 获取 /data/user/0/kim.hsl.hotfix/app_odex/ 目录下的所有文件
        File[] listFiles = filesDir.listFiles();

        // 过滤文件, 系统打包都是 classes.dex , classes1.dex , classes2.dex 等文件
        // 上传的更新包 update.dex 以 .dex 为结尾
        // 以上面两个条件作为过滤的依据
        for (File file : listFiles){
            if (file.getAbsolutePath().startsWith("classes") ||
                    file.getAbsolutePath().endsWith(".dex")){
                // 符合上述两个条件的添加到 HashSet<File> mLoadedDexes 成员中
                mLoadedDexes.add(file);
            }
        }

        // 将 HashSet<File> mLoadedDexes 成员中的 dex 文件加载到内存中


    }
}
