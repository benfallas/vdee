package vdee.evalverde.vdee.features.verses;

public class VersesLayout {

    private VersesActivity versesActivity;
    private Listener listener;

    VersesLayout(VersesActivity versesActivity, Listener listener) {
        this.versesActivity = versesActivity;
        this.listener = listener;
    }

    interface Listener { }
}
