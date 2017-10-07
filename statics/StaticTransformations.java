package statics;

import java.awt.*;
import java.math.BigInteger;
/**
 * Provides operations to transform whole images
 */
public abstract class StaticTransformations {


    static int colorBlack = new Color(0,0,0).getRGB();
    static int colorWhite = new Color(255,255,255).getRGB();

    /**
     * Transforms an image into a binary representation
     * Everything above threshold is set to white, everything else to black
     * @param image
     * @param threshold
     * @return
     */
    public static int[][] hullSpace(int[][] image, int threshold){
        int white = new Color(255, 255, 255).getRGB();
        int black = new Color(0,0,0).getRGB();
        int[][] img = new int[image.length][image[0].length];
        for(int x = 0; x < img.length;x++){
            for(int y = 0; y < img[0].length; y++){
                if(image[x][y] < threshold){
                    img[x][y] = black;
                }else{
                    img[x][y] = white;
                }
            }
        }
        return img;
    }

    /**
     * Inverts a given Image by inverting every pixel
     * @param img
     * @return
     */
    public static int[][] invertImage(int[][] img){
        int[][] image = new int[img.length][img[0].length];
        for(int x = 0; x < img.length; x++){
            for(int y = 0; y < img[0].length; y++){
                image[x][y] = ~img[x][y];
            }
        }
        return image;
    }


    /**
     * Overrides every occurrence of color with newColor
     * @param img
     * @param color
     * @param newColor
     * @return
     */
    public static int[][] overrideColor(int[][] img, int color, int newColor){
        int result[][] = new int[img.length][img[0].length];
        for(int x = 0; x < img.length; x++){
            for(int y = 0; y < img[0].length; y++){
                if(img[x][y]==color){
                    result[x][y] = newColor;
                }else {
                    result[x][y] = img[x][y];
                }
            }
        }
        return result;
    }

    /**
     * Removes small glitches from an img
     * only able to remove one pixel if its surrounded by different pixel
     * Implements a very basic low-pass filter
     * @param img
     * @return
     */
    public static int[][] removeGlitches(int[][] img){
        int result[][] = new int[img.length][img[0].length];
        for(int x = 1; x < img.length-1;x++){
            for(int y = 1; y < img[0].length-1; y++){
                int sum = 0;
                for(int i = 0; i < 9; i++){
                    if(i == 5) continue; //ignore middle pixel
                    sum += img[x-(i%3)][y-(i/3)];
                }
                if((sum/8) == img[x-1][y-1]) //if the average is img[x-1][y-1] => all pixel are equal
                    result[x][y] = img[x-1][y-1];
            }
        }
        return result;
    }

    /**
     * Tries to find the best Threshold to cut of an image with
     * @param img
     * @return
     */
    public static int[][] findBestContrast(int[][] img){
        BigInteger max = BigInteger.ZERO;
        int[][] maxARR = new int[0][0];
        for(int i = 5; i< 250; i+=5){
            int color = new Color(i,i,i).getRGB();
            BigInteger bi = BigInteger.valueOf(measureDifference(img,hullSpace(img,color)));
            if(bi.compareTo(max) > 0){
                System.out.println("i:" + i);
                System.out.println("bi:"+max.toString());
                max = bi;
                maxARR  = hullSpace(img,color);
            }
        }
        return maxARR;
    }

    /**
     * Measures the difference in RGB-Integers between img1 and img2
     * @param img1
     * @param img2
     * @return
     */
    public static int measureDifference(int[][] img1, int[][] img2){
        int sum1 = sumImageArray(img1);
        int sum2 = sumImageArray(img2);
        return sum1 < sum2? sum2-sum1:sum1-sum2;
    }

    /**
     * Sums up every pixel of an img
     * @param img
     * @return
     */
    private static int sumImageArray(int [][] img){
        int result = 0;
        for(int x = 0; x < img.length;x++) {
            for (int y = 0; y < img[0].length; y++) {
                result += img[x][y];
            }
        }
        return result;
    }


    //OLD stuff


    /**
     * TODO wtf?
     * @param img
     * @return
     */
    public static BigInteger sumOfPixels(int[][] img){
        BigInteger bi = BigInteger.valueOf(0);
        for(int x = 0; x < img.length; x++){
            for(int y = 0; y < img[0].length;y++){
                bi = bi.add(BigInteger.valueOf(-1*img[x][y]));
            }
        }
        return bi.compareTo(bi)<0?bi.multiply(BigInteger.valueOf(-1)):bi;
    }


    /**
     * Calculates the Difference between two images, and returns the resulting image
     * as the deviation from the provided color
     * Prerequisite:
     *  img1 and img2 have to have same dimensions
     *  color has to be a positive integer representing a color value, often gray
     * @param img1
     * @param img2
     * @return
     */
    public static int[][] calcDifferenceBetweenImages(int[][] img1, int[][] img2, int color){
        int[][] result = new int[img1.length][img1[0].length];
        for(int i = 0; i < img1.length; i++){
            for(int q = 0; q < img1[0].length; q++){
                result[i][q] = color + (img2[i][q]-img1[i][q]);
            }
        }
        return result;
    }





}
