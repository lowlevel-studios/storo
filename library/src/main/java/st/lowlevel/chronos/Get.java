package st.lowlevel.chronos;

import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import st.lowlevel.chronos.model.BaseMethod;
import st.lowlevel.chronos.model.EntryData;
import st.lowlevel.chronos.model.EntryDataType;

public class Get<T> extends BaseMethod<T> {

    private boolean ignoreExpiry;
    private String key;
    private Type typeOfT;

    Get(@NonNull String key, @NonNull Type typeOfT) {
        this.key = key;
        this.typeOfT = typeOfT;
    }

    @Override
    public T execute() {
        if (!ignoreExpiry) {
            Boolean expired = Chronos.hasExpired(key).execute();
            if (expired != null && expired) {
                return null;
            }
        }
        EntryData<T> entry = Chronos.internalGet(key, new EntryDataType<>(typeOfT));
        if (entry == null) {
            return null;
        }
        return entry.data;
    }

    /**
     * Set to true to ignore the expiration time
     *
     * @param ignore true to ignore the expiration time
     */
    public Get<T> setIgnoreExpiry(boolean ignore) {
        ignoreExpiry = ignore;
        return this;
    }
}
