package st.lowlevel.chronos;

import android.support.annotation.NonNull;

import st.lowlevel.chronos.model.BaseMethod;
import st.lowlevel.chronos.model.Entry;

public class Expired extends BaseMethod<Boolean> {

    private String key;

    Expired(@NonNull String key) {
        this.key = key;
    }

    @Override
    public Boolean execute() {
        Entry entry = new GetEntry(key).execute();
        if (entry == null) {
            return null;
        }
        return entry.hasExpired();
    }
}
