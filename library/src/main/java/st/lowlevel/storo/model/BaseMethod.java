package st.lowlevel.storo.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseMethod<T> {

    /**
     * Creates an observable that will be scheduled on the background thread and observed on the main thread
     *
     * @return Observable<T>
     */
    @NonNull
    public Observable<T> async() {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    T result = execute();
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes the method on a background thread
     *
     * @param callback the callback to call upon completion
     */
    public void async(@Nullable final Callback<T> callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                T result = execute();
                if (callback != null) {
                    callback.onResult(result);
                }
            }
        });
        executor.shutdown();
    }

    /**
     * Executes the method on the main thread
     *
     * @return the method result
     */
    public abstract T execute();
}
