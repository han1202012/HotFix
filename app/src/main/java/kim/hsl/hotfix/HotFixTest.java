package kim.hsl.hotfix;

import android.util.Log;

public class HotFixTest {
    public void test(){
        if (false) {
            // 直接抛出异常
            throw new RuntimeException();
        }
        Log.i("HotFixTest", "HotFixTest 执行成功");
    }
}