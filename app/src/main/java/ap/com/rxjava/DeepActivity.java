package ap.com.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class DeepActivity extends Activity {

    TextView tv_rx;
    private StringBuffer setText = new StringBuffer();

    private void strJoint(String str) {
        setText.append(str).append("\n");
        tv_rx.setText(setText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep);
        tv_rx = (TextView) findViewById(R.id.tv_rx);
        groupBy();
        cast();
        scan();
    }

    private void scan() {
        Observable.just(1, 2, 3)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                strJoint("scan==>>" + integer);
            }
        });
    }

    //cast操作符类似于map操作符，不同的地方在于map操作符可以通过自定义规则，
    //把一个值A1变成另一个值A2，A1和A2的类型可以一样也可以不一样；而cast操作符主要是做类型转换的，
    //传入参数为类型class，如果源Observable产生的结果不能转成指定的class，
    //则会抛出ClassCastException运行时异常。
    private void cast() {
        Observable.just(3, 6, 9)
                .cast(Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        strJoint("cast==>>" + integer);
                    }
                });
    }

    //groupBy操作符是对源Observable产生的结果进行分组，形成一个类型为GroupedObservable的结果集，
    //GroupedObservable中存在一个方法为getKey()，可以通过该方法获取结果集的Key值（类似于HashMap的key)
    private void groupBy() {
        final Observable<Long> Observable = rx.Observable.timer(1, 1, TimeUnit.SECONDS);
        Observable.map(new Func1<Long, Long>() {
            @Override
            public Long call(Long integer) {
                return Long.valueOf(integer);
            }
        }).groupBy(new Func1<Long, Object>() {
            @Override
            public Object call(Long aLong) {
                return aLong % 3;
            }
        }).subscribe(new Action1<GroupedObservable<Object, Long>>() {
            @Override
            public void call(final GroupedObservable<Object, Long> objectLongGroupedObservable) {
                objectLongGroupedObservable.subscribe(new Action1<Long>() {
                    @Override
                    public void call(final Long aLong) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                strJoint("groupBy==>>" + objectLongGroupedObservable.getKey() + ":" + aLong);
                            }
                        });
                    }
                });
            }
        });
    }
}
