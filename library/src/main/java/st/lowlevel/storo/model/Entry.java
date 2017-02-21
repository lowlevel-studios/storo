package st.lowlevel.storo.model;

public class Entry {

    public long expiry;

    /**
     * Check if the entry has expired
     *
     * @return true if the entry has expired
     */
    public boolean hasExpired() {
        return (expiry > 0) && (System.currentTimeMillis() >= expiry);
    }
}
