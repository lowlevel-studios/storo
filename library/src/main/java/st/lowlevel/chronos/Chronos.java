package st.lowlevel.chronos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class Chronos {

    public static final long NO_EXPIRY = 0;

    private static SimpleDiskCache mCache;
    private static Gson mGson;
    private static boolean mInitialized;

    /**
     * Initializes the Chronos instance
     *
     * @param builder the builder instance
     */
    static synchronized void initialize(@NonNull ChronosBuilder builder) {
        try {
            File dir = new File(builder.cacheDir, "chronos");
            if (!dir.exists() && !dir.mkdir()) {
                throw new IOException("Cache folder could not be created.");
            }
            mCache = SimpleDiskCache.open(dir, 1, builder.maxSize);
            mGson = builder.gson;
            mInitialized = true;
        } catch (Exception e) {
            throw new RuntimeException("Chronos instance could not be initialized!", e);
        }
    }

    /**
     * Checks if the Chronos instance has been initialized
     *
     * @throws IllegalStateException
     */
    static void failIfNotInitialized() throws IllegalStateException {
        if (!mInitialized) {
            throw new IllegalStateException("Chronos instance is not initialized! You must call initialize() before calling any other methods.");
        }
    }

    /**
     * Gets an object with the given key
     *
     * @param key the key string
     * @param typeOfT the type of the expected object
     * @return the object or null if does not exist
     */
    @Nullable
    static <T> T internalGet(@NonNull String key, @NonNull Type typeOfT) {
        try {
            InputStream is = mCache.getInputStream(key).getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            return mGson.fromJson(isr, typeOfT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Puts an object with the given key
     *
     * @param key the key string
     * @param object the object to store
     * @return true if the object has been stored
     */
    static boolean internalPut(@NonNull String key, @NonNull Object object) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(mCache.openStream(key));
            mGson.toJson(object, osw);
            osw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the used size (in bytes)
     *
     * @return the used size in bytes
     */
    public static long bytesUsed() {
        failIfNotInitialized();
        try {
            return mCache.bytesUsed();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Clears the entire cache
     *
     * @return true if the cache has been cleared
     */
    public static boolean clear() {
        failIfNotInitialized();
        try {
            mCache.clear();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if an object with the given key exists
     *
     * @param key the key string
     * @return true if the object exists
     */
    public static boolean contains(@NonNull String key) {
        failIfNotInitialized();
        try {
            return mCache.contains(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deletes an object with the given key
     *
     * @param key the key string
     */
    public static boolean delete(@NonNull String key) {
        failIfNotInitialized();
        try {
            mCache.delete(key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets an object with the given key
     *
     * @param key the key string
     * @param classOfT the class of the expected object
     * @return ChronosGet
     */
    @NonNull
    public static <T> Get<T> get(@NonNull String key, @NonNull Class<T> classOfT) {
        return new Get<>(key, classOfT);
    }

    /**
     * Gets an object with the given key
     *
     * @param key the key string
     * @param typeOfT the type of the expected object
     * @return ChronosGet
     */
    @NonNull
    public static <T> Get<T> get(@NonNull String key, @NonNull Type typeOfT) {
        return new Get<>(key, typeOfT);
    }

    /**
     * Checks if an object with the given key has expired
     *
     * @param key the key string
     * @return ChronosExpired
     */
    @NonNull
    public static Expired hasExpired(@NonNull String key) {
        failIfNotInitialized();
        return new Expired(key);
    }

    /**
     * Checks if the Chronos instance is initialized
     *
     * @return true if the instance is initialized
     */
    public static boolean isInitialized() {
        return mInitialized;
    }

    /**
     * Puts an object with the given key
     *
     * @param key the key string
     * @param object the object to store
     * @return ChronosPut
     */
    @NonNull
    public static <T> Put<T> put(@NonNull String key, @NonNull T object) {
        failIfNotInitialized();
        return new Put<>(key, object);
    }
}
