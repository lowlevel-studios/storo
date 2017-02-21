package st.lowlevel.storo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

public class StoroBuilder {

    File cacheDir;
    Gson gson;
    long maxSize;
    double version;

    public enum Storage {
        INTERNAL,
        PREFER_EXTERNAL
    }

    private StoroBuilder(long maxSize) {
        this.maxSize = maxSize;
    }

    @NonNull
    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setVersion(version);
        return builder.create();
    }

    /**
     * Starts the configuration of the Storo instance
     *
     * @param maxSize the maximum size in bytes
     */
    public static StoroBuilder configure(long maxSize) {
        return new StoroBuilder(maxSize);
    }

    /**
     * Initializes the Storo instance
     */
    public synchronized void initialize() {
        if (cacheDir == null) {
            throw new RuntimeException("No cache directory has been specified.");
        }
        if (gson == null) {
            gson = createGson();
        }
        Storo.initialize(this);
    }

    /**
     * Sets the cache directory
     *
     * @param context the application context
     * @param storage the storage to use for the cache
     */
    public StoroBuilder setCacheDirectory(@NonNull Context context, @NonNull Storage storage) {
        switch (storage) {
        case INTERNAL:
            return setCacheDirectory(context.getCacheDir());
        case PREFER_EXTERNAL:
            File dir = context.getExternalCacheDir();
            if (dir == null || !dir.exists() || !dir.canWrite()) {
                dir = context.getCacheDir();
            }
            return setCacheDirectory(dir);
        default:
            throw new IllegalArgumentException("Invalid storage value: " + storage);
        }
    }

    /**
     * Sets the cache directory. It will be created if does not exist
     *
     * @param cacheDir the cache directory
     */
    public StoroBuilder setCacheDirectory(@NonNull File cacheDir) {
        this.cacheDir = cacheDir;
        return this;
    }

    /**
     * Sets the default cache directory
     *
     * @param context the application context
     */
    public StoroBuilder setDefaultCacheDirectory(@NonNull Context context) {
        return setCacheDirectory(context, Storage.INTERNAL);
    }

    /**
     * Sets the Gson instance
     *
     * @param gson the Gson instance
     */
    public StoroBuilder setGsonInstance(@NonNull Gson gson) {
        this.gson = gson;
        return this;
    }

    /**
     * Sets the app version
     *
     * @param version the app version
     */
    public StoroBuilder setVersion(double version) {
        this.version = version;
        return this;
    }
}
