package st.lowlevel.storo;

import android.support.annotation.NonNull;

import st.lowlevel.storo.model.BaseMethod;
import st.lowlevel.storo.model.Entry;

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
