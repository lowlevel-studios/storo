package st.lowlevel.chronos;

import android.support.annotation.NonNull;

import st.lowlevel.chronos.model.BaseMethod;
import st.lowlevel.chronos.model.Entry;

public class GetEntry extends BaseMethod<Entry> {

    private String key;

    GetEntry(@NonNull String key) {
        this.key = key;
    }

    @Override
    public Entry execute() {
        return Chronos.internalGet(key, Entry.class);
    }
}
