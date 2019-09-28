package vdee.evalverde.vdee.features.bliblia;

public class BibliaLayout {

    private BibliaActivity bibliaActivity;
    private Listener listener;

    BibliaLayout(BibliaActivity bibliaActivity, Listener listener) {
        this.bibliaActivity = bibliaActivity;
        this.listener = listener;
    }

    interface Listener {

    }
}
