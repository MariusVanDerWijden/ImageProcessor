package art;

import java.awt.*;

public class Kubism {


    static int colorBlack = new Color(0,0,0).getRGB();
    static int colorWhite = new Color(255,255,255).getRGB();

    //TODO currently broken
    public static int[][] imageToKubism(int[][] img){
        int result[][] = new int[img.length][img[0].length];
        for(int x = 1; x < img.length-1;x++){
            for(int y = 1; y < img[0].length-1; y++){
                result[x][y] = img[x][y];
                if(img[x][y] == img[x+1][y]) {
                    if (img[x][y] == img[x][y+1]) {
                        result[x+1][y+1] = img[x][y];
                    }else if(img[x+1][y+1] == img[x][y]){
                        result[x][y+1] = img[x][y];
                    }
                }else if(img[x][y] == img[x][y+1]){
                    if (img[x][y] == img[x+1][y]) {
                        result[x][y+1] = img[x][y];
                    }else if(img[x+1][y+1] == img[x][y]){
                        result[x+1][y] = img[x][y];
                    }
                }
            }
        }
        return result;
    }


    /**
     * Strange artistic thing?
     * @param img
     * @param cubesize
     * @return
     */
    public static int[][] imageToKubismV2(int[][] img, int cubesize){
        int[][] result;
        result = img;
        for(int x = 0; x < img.length-(cubesize-1); x++){
            for(int y = 0; y < img[0].length-(cubesize-1); y++){
                int sum = 0;
                for(int i = 0; i < cubesize; i++){
                    for(int q = 0; q < cubesize; q++){
                        sum += img[x+i][y+q];
                    }
                }
                if(sum > (((cubesize*cubesize)-cubesize)+1)*colorBlack ){
                    //convert to all black
                    for(int i = 0; i < cubesize; i++){
                        for(int q = 0; q < cubesize; q++){
                            result[x+i][y+q] = colorBlack;
                        }
                    }
                }else if( sum < colorBlack*cubesize-1){
                    //convert to all white
                    for(int i = 0; i < cubesize; i++){
                        for(int q = 0; q < cubesize; q++){
                            result[x+i][y+q] = colorWhite;
                        }
                    }
                }

            }
        }
        return result;
    }

}
