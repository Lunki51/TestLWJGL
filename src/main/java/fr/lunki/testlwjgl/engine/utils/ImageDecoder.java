package fr.lunki.testlwjgl.engine.utils;

import com.sun.imageio.plugins.png.PNGImageWriter;
import com.sun.imageio.plugins.png.PNGImageWriterSpi;
import javafx.scene.paint.Color;
import org.lwjgl.BufferUtils;
import sun.awt.image.PNGImageDecoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengles.GLES20.GL_CLAMP_TO_EDGE;

public class ImageDecoder {

    private int width;
    private int height;
    private int imageID;
    private byte[] imageData;

    public ImageDecoder(String path,float params){
        try{
            BufferedImage image = ImageIO.read(new File(path));
            this.width=image.getWidth();
            this.height=image.getHeight();
            int[] data= image.getData().getPixels(0,0,width,height,(int[]) null);
            byte[] byteData = new byte[data.length];

            for(int i=0;i<data.length;i++){
                byteData[i]= (byte) data[i];
            }

            this.imageData = byteData;
            ByteBuffer buffer = BufferUtils.createByteBuffer(byteData.length);
            buffer.put(byteData);
            buffer.flip();

            glEnable(GL_TEXTURE_2D);
            this.imageID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D,this.imageID);
            glPixelStorei(GL_UNPACK_ALIGNMENT,1);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            if(image.getType()==6 ||image.getType()==2){
                glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,this.width,this.height,0,GL_RGBA,GL_UNSIGNED_BYTE,buffer);
            }else{
                glTexImage2D(GL_TEXTURE_2D,0,GL_RGB,this.width,this.height,0,GL_RGB,GL_UNSIGNED_BYTE,buffer);
            }

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Cant read "+path);
        }
    }

    public byte[] getImageData() {
        return imageData;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getImageID() {
        return imageID;
    }
}
