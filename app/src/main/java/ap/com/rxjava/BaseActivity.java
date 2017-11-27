package ap.com.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class BaseActivity extends AppCompatActivity {

    TextView tv_rx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        tv_rx = (TextView) findViewById(R.id.tv_rx);
        test1();
        test2();
//        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
        test11();
        test12();
        test13();
    }

    private void test13() {
        final StringBuffer join = new StringBuffer();
        String[] strings = {"swallow", "8080"};
        Observable.from(strings)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        strJoint("flatMap:" + join);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(String s) {
                        join.append(s);
                    }
                });
    }

    private void test12() {
        final StringBuffer join = new StringBuffer();
        String[] strings = {"swallow", "8080"};
        Observable.from(strings)
                .concatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.just(s)
                                .concatMap(new Func1<String, Observable<String>>() {
                                    @Override
                                    public Observable<String> call(String s) {
                                        Observable<String> observable = Observable.just(s);
                                        observable.concatMap(new Func1<String, Observable<String>>() {
                                            @Override
                                            public Observable<String> call(String s) {
                                                Observable<String> observable = Observable.just(s);
                                                observable.subscribeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new Subscriber<String>() {
                                                            @Override
                                                            public void onCompleted() {
                                                                strJoint("flatMap1:" + join);
                                                            }

                                                            @Override
                                                            public void onError(Throwable throwable) {

                                                            }

                                                            @Override
                                                            public void onNext(String s) {
                                                                join.append(s);
                                                            }
                                                        });
                                                return observable;
                                            }
                                        })
                                                .map(new Func1<String, Integer>() {
                                                    @Override
                                                    public Integer call(String s) {
                                                        if ("swallow".equals(s)) return 1500813;
                                                        return 9507;
                                                    }
                                                })
                                                .subscribeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Subscriber<Integer>() {
                                                    @Override
                                                    public void onCompleted() {
                                                        strJoint("flatMap2:" + join);
                                                    }

                                                    @Override
                                                    public void onError(Throwable throwable) {

                                                    }

                                                    @Override
                                                    public void onNext(Integer s) {
                                                        join.append(s);
                                                    }
                                                });
                                        return observable;
                                    }
                                });
                    }
                })
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        if ("swallow".equals(s)) return 1838313;
                        return 8318;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        strJoint("flatMap3:" + join);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        join.append(integer);
                    }
                });
    }

    private void test11() {
        Observable.just(getApplicationContext().getExternalCacheDir())
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return listFiles(file);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        if (null == file) {
                            strJoint("flatMap:" + "null");
                            return;
                        }
                        strJoint("flatMap:" + file.getAbsolutePath());
                    }
                });
    }

    private Observable<File> listFiles(final File f) {
        if (null != f && f.isDirectory()) {
            return Observable.from(f.listFiles())
                    .flatMap(new Func1<File, Observable<File>>() {
                        @Override
                        public Observable<File> call(File file) {
                            return listFiles(f);
                        }
                    });
        } else {
            return Observable.just(f);
        }
    }

    private void test10() {
        final StringBuffer join = new StringBuffer();
        final String[] mails = new String[]{"Here is an email!", "Another email!", "Yet another email!"};
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(mails[0]);
                subscriber.onCompleted();
                try {
                    /*if (subscriber.isUnsubscribed()) return;*/
                    Random random = new Random();
                    while (true) {
                        String mail = mails[random.nextInt(mails.length)];
                        subscriber.onNext(mail);
                        /*Thread.sleep(1000);*/
                    }
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io())
                //buffer操作符周期性地收集源Observable产生的结果到列表中，
                //并把这个列表提交给订阅者，订阅者处理后，清空buffer列表，
                //同时接收下一次收集的结果并提交给订阅者，周而复始。
                .buffer(3, TimeUnit.SECONDS)
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        strJoint("buffer:" + join);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        join.append(throwable.toString() + "\t");
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        if (null != strings)
                            for (String str : strings) {
                                join.append(str + "\t");
                            }
                    }
                });
    }

    //range
    private void test9() {
        Observable.range(1, 3, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(final Integer aLong) {
                        strJoint("range:" + aLong);
                    }
                });
    }

    //interval
    private void test8() {
        Observable.interval(1, 1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(final Long aLong) {
                        /*strJoint("interval:" + aLong);*/
                    }
                });
    }

    //timer
    private void test7() {
        Observable.timer(1, 1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(final Long aLong) {
                        strJoint("timer:" + aLong);
                    }
                });
    }

    int i = 100;

    //defer
    //而defer是在订阅者订阅时才创建Observable，
    private void test6() {
        Observable<Integer> observable = Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(i);
            }
        });
        i = 200;
        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                strJoint("defer:" + o);
            }
        });
    }

    //just+repeat
    //just操作符是在创建Observable就进行了赋值操作
    private void test5() {
        final StringBuffer join = new StringBuffer();
        Observable.just(8, 5, 9, 4, 3, 4, 4, 6, 1)
                .repeat(2)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return String.valueOf(integer);
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        strJoint("just+repeat:" + join.toString());
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(String integer) {
                        join.append(integer);
                    }
                });
    }

    //from
    private void test4() {
        final StringBuffer join = new StringBuffer();
        Integer[] ints = {1, 8, 3, 8, 3, 1, 3, 8, 3, 1, 8};
        Observable.from(ints)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return String.valueOf(integer);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        join.append(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        strJoint("from:" + join.toString());
                    }
                });
    }

    private void test3() {
        /*Observable.just("数据发送成功!")
                .subscribe(s -> strJoint("test3:" + s));*/
        Observable.just("数据发送成功!")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return 183;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer s) {
                        strJoint("test3:" + s);
                    }
                });
    }

    //map
    private void test2() {
        Observable.just("数据发送成功!")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "-swallow-";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        strJoint("map:" + s);
                    }
                });
    }

    //base
    private void test1() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("数据发送成功!");
                subscriber.onCompleted();
            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                tv_rx.setText(throwable.toString());
            }

            @Override
            public void onNext(String s) {
                strJoint("base:" + s);
            }
        };
        observable.subscribe(subscriber);
    }

    private StringBuffer setText = new StringBuffer();

    private void strJoint(String str) {
        setText.append(str).append("\n");
        tv_rx.setText(setText);
    }
}
