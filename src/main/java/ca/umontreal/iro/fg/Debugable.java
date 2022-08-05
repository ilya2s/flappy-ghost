package ca.umontreal.iro.fg;

/**
 * Interface to keep track of debug mode activation
 */
public interface Debugable {
    boolean isDebug();
    void startDebug();
    void stopDebug();
}
