package st.lowlevel.storo.rx;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import st.lowlevel.storo.model.BaseMethod;

public class RxStoro {

    /**
     * Creates a RxJava observable that will be scheduled on the background thread and observed on the main thread
     *
     * @param method the Storo method to execute
     * @return Single<T>
     */
    @NonNull
    public static <T> Single<T> async(@NonNull final BaseMethod<T> method) {
        return with(method)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    /**
     * Creates a RxJava observable that will execute the passed method
     *
     * @param method the Storo method to execute
     * @return Single<T>
     */
    @NonNull
    public static <T> Single<T> with(@NonNull final BaseMethod<T> method) {
        return Single.fromCallable(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return method.execute();
            }
        });
    }
}
