package fr.lunki.testlwjgl.engine.graphics.material;

import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.utils.ImageDecoder;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.GL_NEAREST;


public class Texture {
    private String path;
    private float width, height;
    private int textureID;
    //private org.newdawn.slick.opengl.Texture texture;
    private byte[] textureData;

    public Texture(String path) {
        this.path = path;
    }

    public void create() {
        try {
            System.out.println(path);
            String thePath = "res/textures/"+path;
            thePath = thePath.replaceAll("//","/");
            ImageDecoder decoder = new ImageDecoder(thePath,GL_NEAREST);
            /*org.newdawn.slick.opengl.Texture texture = TextureLoader.getTexture(path.split("[.]")[1], Texture.class.getResourceAsStream(path), GL11.GL_NEAREST);
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS,-0.4f);

             */
            width = decoder.getWidth();
            height = decoder.getHeight();
            textureID = decoder.getImageID();
            this.textureData=decoder.getImageData();
        } catch (Exception e) {
            System.err.println("Can't find texture at " + path);
            e.printStackTrace();
        }
    }

    public Vector3f colorAt(int x, int y){
        byte[] texData = textureData;
        int red   = texData[(int) ((y * this.width + x) * 3)];
        int green = texData[(int) ((y * this.width + x) * 3 + 1)];
        int blue  = texData[(int) ((y * this.width + x) * 3 + 2)];
        return new Vector3f( (red > 0) ? reMap(red, 0, 255f, 0, 1f) : reMap(red+256, 0, 255f, 0f, 1f),
                (green > 0) ? reMap(green, 0, 255f, 0, 1f) : reMap(green+256, 0, 255f, 0f, 1f),
                (blue > 0) ? reMap(blue, 0, 255f, 0, 1f) : reMap(blue+256, 0, 255f, 0f, 1f) );

    }
    public static float reMap(float value, float low1, float high1, float low2, float high2) {
        return low2 + (high2 - low2) * (value - low1) / (high1 - low1);
    }

    public void destroy() {
        GL13.glDeleteTextures(textureID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }
}
