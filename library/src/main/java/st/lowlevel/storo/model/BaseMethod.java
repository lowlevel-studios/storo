package st.lowlevel.storo.model;

import android.support.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseMethod<T> {

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
