package art;

import java.util.ArrayList;

public class ImageBlending {

    /**
     * blends two images together, uses stencil as an indicator, where the blending should be
     * img1.length should be equal to img2.length
     * @param img1
     * @param img2
     * @param stencil indicates how many percent of img1 should be visible (between 0 and 1)
     * @param a parameter for gaussian pyramid
     * @return
     */
    public static int[][] blendLaplace(int[][] img1, int[][] img2, float[][] stencil, float a){
        ResolutionPyramid left = laplacianPyramid(img1,a);
        ResolutionPyramid right = laplacianPyramid(img2,a);
        int[][] result = new int[img1.length][img1[0].length];
        for(int i = left.levels.size(); i > 0 ;i--){
            int[][] tmpLeft = left.levels.get(i);
            int[][] tmpRight = right.levels.get(i);
            for(int x = 0; x < tmpLeft.length; x++){
                for(int y = 0; y < tmpRight.length; y++){
                    int tmp = (int) (stencil[x][y] * tmpLeft[x][y] + 1-stencil[x][y] * tmpRight[x][y]);
                    result[x][y] += tmp;
                }
            }
        }
        return result;
    }

    /**
     * Returns a laplace pyramid (every level is bandpass)
     * @param img
     * @param a
     * @return
     */
    private static ResolutionPyramid laplacianPyramid(int[][] img, float a){
        ResolutionPyramid res = new ResolutionPyramid();
        ResolutionPyramid gauss = gaussianPyramid(img,a);
        for(int[][] x: gauss.levels){
            int[][] tmp = expand(x,a);
            res.levels.add(subtract(x,tmp));
        }
        res.levels.add(gauss.levels.get(gauss.levels.size()-1));
            //last level of laplace pyramid is last of Gauss pyramid
        return res;
    }

    /**
     * Subtracts every pixel from img2 off img1
     * @param img1
     * @param img2
     * @return
     */
    private static int[][] subtract(int img1[][], int img2[][]){
        int[][] res = img1;
        for(int i = 0; i < img1.length; i++){
            for(int j = 0; j < img1[0].length; j++){
                res[i][j] -= img2[i][j];
            }
        }
        return res;
    }

    /**
     * Generates a gaussian Pyramid (every level is low-pass)
     * @param img
     * @param a
     * @return
     */
    private static ResolutionPyramid gaussianPyramid(int[][] img, float a){
        ResolutionPyramid res = new ResolutionPyramid();
        res.levels.add(img);
        int[][] tmp = img;
        do{
            tmp = reduce(tmp,a);
            res.levels.add(tmp);
        }while (tmp.length > 1 && tmp[0].length > 1);
        return res;
    }

    /**
     * Expand operation, interpolates picture on bigger surface
     * @param img
     * @param a
     * @return
     */
    private static int[][] expand(int[][] img, float a){
        int[][] newLevel = new int[img.length*2][img[0].length*2];
        float[][] weights = initializeWeights(a);
        for(int i = 0; i < img.length; i++){
            for(int j = 0; j < img[0].length; j++){
                for(int u = -2; u < 2; u++){
                    for(int v = -2; v < 2; v++){
                        newLevel[i+u][j+v] += weights[2+u][2+v] * img[i][j];
                    }
                }
            }
        }
        return newLevel;
    }

    /**
     * Reduce operation, reduces the given image to 1/4
     * @param img
     * @param a
     * @return
     */
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
                            sum += weights[2+u][2+v] * img[i+u][j+v];
                    newLevel[i][j] = (int)sum;
                }
            }
        }
        return null;
    }

    /**
     * initializes the weights for the reduce/expand operations
     * @param a
     * @return
     */
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

    private static class ResolutionPyramid{
        ArrayList<int[][]> levels = new ArrayList<>();
    }
}
