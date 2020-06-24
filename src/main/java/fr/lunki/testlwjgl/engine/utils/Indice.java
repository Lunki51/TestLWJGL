package fr.lunki.testlwjgl.engine.utils;

public class Indice {

    private int[] tab;
    private boolean activated;

    public Indice(int[] tab, boolean activated) {
        this.tab = tab;
        this.activated = activated;
    }

    public Indice(int[] tab) {
        this.tab = tab;
        this.activated = true;
    }

    public int[] getTab() {
        return tab;
    }

    public boolean isActivated() {
        return activated;
    }

    public void revert() {
        this.activated = !activated;
    }
}
