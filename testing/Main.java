package testing;

import art.Kubism;
import io.ProcessorIO;
import sample.Sampler;
import statics.StaticTransformations;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by matematik on 12/1/15.
 */
public class Main {

    static String ordner = "test2/";
    static String filename = "asdf4";
    static String ending = ".jpg";

    public static void main(String[] args){

    }

    public static void mesher(){
        Sampler sampler = new Sampler();
        sampler.meshMe("test2/asdf1.jpg");
    }

    public static void multipleContrastTest(){
        BufferedImage img = ProcessorIO.loadImage(ordner+filename+ending);
        int [][] tempArr = ProcessorIO.bufferedImageToIntArray(img);
        String name = "";
        for(int i = 255; i >= 0; i-=3){
            name+="a";
            int color = new Color(i, i, i).getRGB();
            int [][] tempHull = StaticTransformations.hullSpace(tempArr,color );
            tempHull = StaticTransformations.overrideColor(tempHull,new Color(255,255,255).getRGB(),new Color(0,200,0).getRGB());
            BufferedImage image = ProcessorIO.intArrayToBufferedImage(tempHull,img.getWidth(),img.getHeight());

            ProcessorIO.writeImageToFile(image,ordner+"out/"+filename+"_"+name+i,"jpg");
            System.out.println(i+":"+ StaticTransformations.sumOfPixels(tempHull).toString());
        }
    }

    public static void kubismTest(){
        BufferedImage img = ProcessorIO.loadImage(ordner+filename+ending);
        int [][] tempArr = ProcessorIO.bufferedImageToIntArray(img);
        tempArr = StaticTransformations.hullSpace(tempArr,new Color(60,60,60).getRGB());
        tempArr = Kubism.imageToKubismV2(tempArr,40);
        BufferedImage image = ProcessorIO.intArrayToBufferedImage(tempArr,img.getWidth(),img.getHeight());
        ProcessorIO.writeImageToFile(image,ordner+"out/k"+filename,"jpg");
    }

    public static void contrastTest(){
        BufferedImage img = ProcessorIO.loadImage(ordner+filename+ending);
        int [][] tempArr = ProcessorIO.bufferedImageToIntArray(img);
        int [][] tempHull = StaticTransformations.findBestContrast(tempArr);
        BufferedImage image = ProcessorIO.intArrayToBufferedImage(tempHull,img.getWidth(),img.getHeight());
        ProcessorIO.writeImageToFile(image,ordner+"out/c"+filename,"jpg");
    }

    public static void removeGlitchesTest(){
        BufferedImage img = ProcessorIO.loadImage("asdf.jpg");
        int[][] imgArr = ProcessorIO.bufferedImageToIntArray(img);
        imgArr = StaticTransformations.hullSpace(imgArr,new Color(128,128,128).getRGB());
        int[][] oldImage;
        int i = 0;
        do{
            oldImage = imgArr;
            imgArr = StaticTransformations.removeGlitches(imgArr);
            System.out.println("adfs"+i++);
        }while (StaticTransformations.measureDifference(oldImage,imgArr)>2);
        img = ProcessorIO.intArrayToBufferedImage(imgArr,img.getWidth(),img.getHeight());
        ProcessorIO.writeImageToFile(img,"output6","png");
    }

    public static void invertTest(){
        BufferedImage img = ProcessorIO.loadImage(ordner+filename+ending);
        int [][] tempArr = ProcessorIO.bufferedImageToIntArray(img);
        int [][] invertedImage = StaticTransformations.invertImage(tempArr);
        BufferedImage image = ProcessorIO.intArrayToBufferedImage(invertedImage,img.getWidth(),img.getHeight());
        ProcessorIO.writeImageToFile(image,ordner+"out/inv"+filename,"jpg");
    }

    public static void setColorTest(){
        BufferedImage img = ProcessorIO.loadImage(ordner+filename+ending);
        int [][] tempArr = ProcessorIO.bufferedImageToIntArray(img);
        int [][] colorImage = StaticTransformations.hullSpace(tempArr,new Color(128,128,128).getRGB());
        colorImage = StaticTransformations.overrideColor(colorImage,new Color(255,255,255).getRGB(),new Color(0,255,0).getRGB());
        BufferedImage image = ProcessorIO.intArrayToBufferedImage(colorImage,img.getWidth(),img.getHeight());
        ProcessorIO.writeImageToFile(image,ordner+"out/inv"+filename,"jpg");
    }
}
