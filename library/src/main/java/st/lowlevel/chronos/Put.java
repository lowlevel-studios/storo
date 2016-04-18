package st.lowlevel.chronos;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import st.lowlevel.chronos.model.BaseMethod;
import st.lowlevel.chronos.model.EntryData;

public class Put<T> extends BaseMethod<Boolean> {

    private long expiry = Chronos.NO_EXPIRY;
    private String key;
    private T object;

    Put(@NonNull String key, @NonNull T object) {
        this.key = key;
        this.object = object;
    }

    @Override
    public Boolean execute() {
        EntryData<T> entry = new EntryData<>();
        entry.data = object;
        entry.expiry = expiry;
        try {
            Chronos.internalPut(key, entry);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets the object expiry timestamp
     *
     * @param expiry the expiry timestamp
     */
    public Put setExpiry(long expiry) {
        this.expiry = expiry;
        return this;
    }

    /**
     * Sets the object expiry timestamp
     *
     * @param duration the object duration
     * @param unit the time unit of the duration
     */
    public Put setExpiry(long duration, @NonNull TimeUnit unit) {
        return setExpiry(System.currentTimeMillis() + unit.toMillis(duration));
    }
}
