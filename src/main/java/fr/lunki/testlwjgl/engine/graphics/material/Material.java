package fr.lunki.testlwjgl.engine.graphics.material;

public class Material {
    private float shineDamper;
    private float reflectivity;
    private Texture texture;
    private boolean created;

    private boolean transparent = false;
    private boolean usingFakeLighting = false;

    public Material(float shineDamper, float reflectivity, Texture texture) {
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.texture = texture;
        this.created=false;
    }

    public boolean isUsingFakeLighting() {
        return usingFakeLighting;
    }

    public void setUsingFakeLighting(boolean usingFakeLighting) {
        this.usingFakeLighting = usingFakeLighting;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public void create(){
        if(!this.isCreated()) texture.create();
        this.created=true;
    }

    public boolean isCreated() {
        return created;
    }

    public void destroy(){
        texture.destroy();
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public Texture getTexture() {
        return texture;
    }
}
