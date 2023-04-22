package pt.ipvc.base;

public interface EventListener {
    void onSuccess();
    void onFail();
    void onCancel();
}
