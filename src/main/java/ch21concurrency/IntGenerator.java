package ch21concurrency;

public abstract class IntGenerator {
    private volatile boolean cancel = false;
    public abstract int next();

    public void cancel() {
        cancel = true;
    }

    public boolean isCancel() {
        return cancel;
    }
}
