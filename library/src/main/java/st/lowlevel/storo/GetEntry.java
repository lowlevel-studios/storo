package st.lowlevel.storo;

import android.support.annotation.NonNull;

import st.lowlevel.storo.model.BaseMethod;
import st.lowlevel.storo.model.Entry;

public class GetEntry extends BaseMethod<Entry> {

    private String key;

    GetEntry(@NonNull String key) {
        this.key = key;
    }

    @Override
    public Entry execute() {
        return Storo.internalGet(key, Entry.class);
    }
}
