package ap.com.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class LoodingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looding);
//        Observable.timer(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                .map(new Func1<Long, Object>() {
//                    @Override
//                    public Object call(Long aLong) {
//                        startActivity(new Intent(LoodingActivity.this, MainActivity.class));
//                        finish();
//                        return null;
//                    }
//                }).subscribe();

    }
}
