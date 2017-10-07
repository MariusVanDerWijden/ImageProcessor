package morphology;

import java.awt.*;

public class StructuringElement {

    private int[][] element;
    private Point middle;
    private int maxDistanceFromMiddle;

    public StructuringElement(int[][] elem, Point middle){
        element = elem;
        this.middle = middle;
        calcMaxDistance();
    }

    /**
     * Returns a new Image opened by the structuring element
     * @param img
     * @param newColor
     * @param oldColor
     * @return
     */
    public int[][] opening(int[][] img, int newColor, int oldColor){
        int[][] newImg = erosion(img,newColor,oldColor);
        dilation(img,newImg,newColor,oldColor);
        return newImg;
    }

    /**
     * Returns a new Image closed by the structuring element
     * @param img
     * @param newColor
     * @param oldColor
     * @return
     */
    public int[][] closing(int[][] img, int newColor, int oldColor){
        int[][] newImg = dilation(img,newColor,oldColor);
        erosion(img,newImg,newColor,oldColor);
        return newImg;
    }


    /**
     * Returns a new image eroded with the given structuring element
     * @param img
     * @param newColor
     * @param oldColor
     * @return
     */
    public int[][] erosion(int[][] img, int newColor, int oldColor){
        int[][] res = new int[img.length][img[0].length];
        erosion(img,res, newColor, oldColor);
        return res;
    }

    /**
     * Erodes the img with the given structuring element
     * @param img
     * @param newImg
     * @param newColor
     * @param oldColor
     */
    public void erosion(int[][] img, int[][] newImg, int newColor, int oldColor){
        for(int i = 0; i < img.length-element.length;i++){
            for(int j = 0; j < img[0].length - element[0].length; j++){
                if(covers(img,new Point(i,j),newColor))
                    newImg[i+middle.x][i+middle.y] = newColor;
                else
                    newImg[i+middle.x][i+middle.y] = oldColor;
            }
        }
    }

    /**
     * Returns true if the image with the structuring element overlaid at the point p
     * has only color components
     * @param img
     * @param p
     * @param color
     * @return
     */
    private boolean covers(int[][] img, Point p, int color){
        for(int i = 0; i < element.length; i++){
            for(int j = 0; j < element.length; j++){
                if(element[i][j] == 1 && img[i+p.x][j+p.y] != color)
                    return false;
            }
        }
        return true;
    }

    /**
     * Constructs a new image and dilates it with the given structuring element
     * @param img
     * @param newColor
     * @param oldColor
     * @return
     */
    public int[][] dilation(int[][] img, int newColor, int oldColor){
        int[][] res = new int[img.length][img[0].length];
        dilation(img,res,newColor,oldColor);
        return res;
    }

    /**
     * Dilate the image img with the given structuring element
     * @param img
     * @param newImg
     * @param newColor
     */
    public void dilation(int[][] img, int[][] newImg, int newColor,int oldColor){
        for(int[] i:newImg)
            for(int j:i)
                j = oldColor;
        for(int i = maxDistanceFromMiddle; i < img.length-maxDistanceFromMiddle; i++){
            for(int j = maxDistanceFromMiddle; j < img[0].length-maxDistanceFromMiddle;j++){
                if(img[i][j] == newColor)
                    spray(newImg,new Point(i,j),newColor);
            }
        }
    }

    /**
     * Sprays the given color on the image
     * @param img
     * @param p
     * @param color
     */
    private void spray(int[][] img, Point p, int color){
        for(int i = 0; i < element.length; i++){
            for(int j = 0; j < element[0].length; j++){
                if(element[i][j] == 1)
                    img[p.x-middle.x+i][p.y-middle.y+j] = color;
            }
        }
    }

    /**
     * Calculates the maximal distance from the point in the middle
     * If the
     */
    private void calcMaxDistance(){
        int max = middle.x;
        if(max < middle.y)
            max = middle.y;
        if(max < element.length -middle.x)
            max = element.length -middle.x;
        if(max < element[0].length -middle.y)
            max = element[0].length -middle.y;
        maxDistanceFromMiddle = max;
    }
}
