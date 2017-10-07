package st.lowlevel.storo.rx;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import st.lowlevel.storo.model.BaseMethod;

public class RxStoro2 {

    /**
     * Creates a RxJava 2 observable that will be scheduled on the background thread and observed on the main thread
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
     * Creates a RxJava 2 observable that will execute the passed method
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
