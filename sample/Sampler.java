package sample;

import io.ProcessorIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by matematik on 7/9/16.
 */
public class Sampler {

    static int colorBlack = new Color(0,0,0).getRGB();
    static int colorWhite = new Color(255,255,255).getRGB();
    static int colorGreenMesh = new Color(0,255,0).getRGB();

    private final int BLOCK_SIZE = 10;

    //TODO save doesnt work properly
    public void meshMe(String filename){
        BufferedImage bfimg = ProcessorIO.loadImage(filename);
        if(bfimg == null)System.out.println("afd");
        int[][] orgImg = ProcessorIO.bufferedImageToIntArray(bfimg);
        int[][] downsmpledImg = downSamplePictureInBlocks(orgImg, BLOCK_SIZE);
        int[][] resultImg = upSampleBlockPicture(downsmpledImg,BLOCK_SIZE,orgImg);
        BufferedImage bufferedImage = ProcessorIO.intArrayToBufferedImage(downsmpledImg);
        System.out.println(Arrays.toString(downsmpledImg));
        ProcessorIO.writeImageToFile(bufferedImage,"test2/dnfasdf",".jpg");
        ProcessorIO.writeImageToFile(ProcessorIO.intArrayToBufferedImage(resultImg), "test2/fdsasdf",".jpg");
    }

    private int[][] findSignificantPoints(int[][] img){
        //TODO impl
        return null;
    }

    //TODO test me, test me pleeese
    private int[][] upSampleBlockPicture(int[][] blockImg, int blockSize, int[][] orginalPicture){
        int[][] resultPicture = new int[orginalPicture.length][orginalPicture[0].length];
        int temp1 = 0,temp2 = 0, i2 = 0, i3 = 0;
        for(int i = 0; i<resultPicture.length && i2<blockImg.length; i++){
            for(int q = 0; q < resultPicture[0].length && i3<blockImg[0].length; q++){
                temp1++;
                if(i2>=blockImg.length||i3>=blockImg[0].length)
                    System.out.println("indexoutofbounds!");//does it throw here? while accessing blockImg? -Yep, fix it
                resultPicture[i][q] = blockImg[i2][i3];
                if(temp1 == blockSize){
                    i3++; temp1 = 0;
                }
            }
            temp2++;
            if(temp2 == blockSize){
                i2++; temp2 = 0;
            }
        }
        return resultPicture;
    }


    //TODO test mee
    private int[][] downSamplePictureInBlocks(int[][] img, int size){
        int sizeQuad = size*size;
        int height = img[0].length/size;
        int width = img.length/size;
        int[][] blockArray = new int[width][height];
        for(int i = 0; i < height; i++){
            for(int q = 0; q < width; q++){
                int alpha = 0,green = 0,red = 0,blue = 0;
                for(int w = i*size; w < size; w++){
                    for(int s = q*size; s < size; s++){
                        if(w < img.length && s < img[0].length) {
                            int temp = img[w][s];
                            alpha += temp >>> 24;
                            red += temp << 8 >>> 24;
                            green += temp << 16 >>> 24;
                            blue += temp << 24 >>> 24;
                        }
                    }
                }
                alpha /= sizeQuad;
                green /= sizeQuad;
                red /= sizeQuad;
                blue /= sizeQuad;
                blockArray[q][i] = (alpha << 24) & (red << 16) & (green << 8) & blue;
            }
        }
        return blockArray;
    }

}
