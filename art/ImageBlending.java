package art;

import java.util.ArrayList;

public class ImageBlending {

    public static int[][] blendLaplace(int[][] img1, int[][] img2){
        return null;
    }

    private static int[][] reduce(int[][] img, float a){
        int[][] newLevel = new int[img.length/2][img[0].length/2];
        float[][] weights = initializeWeights(a);
        for(int i = 0; i < newLevel.length; i++){
            for(int j = 0; j < newLevel[0].length; j++){
                if(i-2<0||j-2<0)
                    newLevel[i][j] = img[i][j];
                else{
                    float sum = 0.f;
                    for(int u = -2; u < 2; u++)
                        for(int v  = -2; v < 2; v++)
                            sum += weights[u][v] * img[i+u][j+v];
                    newLevel[i][j] = (int)sum;
                }
            }
        }
        return null;
    }

    private static float[][] initializeWeights(float a){
        float[][] weights = new float[5][5];
        float b = 1/4;
        float c = 1/4-a/2;
        float[] weightA = new float[]{c,b,a,b,c};
        float[] weightB = weightA;
        for(int i = 0; i < weights.length; i++)
            for(int j = 0; j < weights.length; j++)
                weights[i][j] = weightA[i]*weightB[j];
        return  weights;
    }

    private static int[][] binominalFilter(int [][] img){
        return reduce(img,3/8);
    }

    private static int[][] gaussianFilter(int [][] img){
        return reduce(img, 2/5);
    }

    private class LaplacePyramid{
        ArrayList<int[][]> levels;
    }
}
