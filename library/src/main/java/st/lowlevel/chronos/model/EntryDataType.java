package st.lowlevel.chronos.model;

import android.support.annotation.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class EntryDataType<T> implements ParameterizedType {

    private Type mType;

    public EntryDataType(@NonNull Type typeOfT) {
        mType = typeOfT;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] { mType };
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public Type getRawType() {
        return EntryData.class;
    }
}
