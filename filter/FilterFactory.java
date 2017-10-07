package filter;

import java.util.Arrays;

public class FilterFactory {

    /**
     * Generates an Gaussian Filter
     * Uses an size*size filter -> size should be uneven and > 0
     * @param size
     * @return
     */
    public static Filter gaussianFilter(int size){
        Filter f = new Filter();
        int result[][] = new int[size][size];
        int sigma = 1;
        int sum = 0;
        for(int i = 0; i < size; i++){
            for(int w = 0; w < size; w++){
                result[i][w] = (int) (1/(2*Math.PI*sigma*sigma) * Math.exp(-(i*i + w*w)/2*sigma*sigma));
                sum += result[i][w];
            }
        }
        f.mask = result;
        f.factor = 1/sum;
        return f;
    }

    /**
     * Generates an averaging filter
     * Uses an size*size filter
     * @param size
     * @return
     */
    public static Filter averageFilter(int size){
        Filter f = new Filter();
        int result[][] = new int[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                result[i][j] = 1;
            }
        }
        f.mask = result;
        f.factor = 1 / (size*size);
        return f;
    }

    /**
     * Applies the given filter to the image TODO test this
     * filter has to be smaller than img
     * @param img
     * @param filter
     */
    public static int[][] applyFilter(int[][] img, Filter filter){
        int[][] result = new int[img.length][img[0].length];
        for(int x = filter.mask.length; x < img.length-filter.mask.length; x++){
            for(int y = filter.mask[0].length; y < img[0].length-filter.mask[0].length;y++){
                int sum = 0;
                for(int i = -filter.mask.length/2; i < filter.mask.length/2; i++){
                    for(int q = -filter.mask[0].length/2; q < filter.mask[0].length/2; q++){
                        sum += img[x+i][y+q] * (filter.mask[i + filter.mask.length/2][q + filter.mask[0].length/2]);
                    }
                }
                result[x][y] = (int)filter.factor*sum;
            }
        }
        return result;
    }

    /**
     * Applies a median filter to an image
     * @param img
     * @param filterSize
     * @return
     */
    public static int[][] applyMedianFilter(int [][] img, int filterSize){
        int[][] result = new int[img.length][img[0].length];
        for(int i = filterSize/2; i < img.length-filterSize/2; i++){
            for(int j = filterSize/2; j < img[0].length-filterSize/2; j++){
                result[i][j] = median(img, i, j, filterSize);
            }
        }
        return result;
    }

    /**
     * Calculates the Median-element of a sub-array
     * @param img
     * @param x
     * @param y
     * @param filterSize
     * @return
     */
    private static int median(int[][] img, int x, int y, int filterSize){
        int[] values = new int[filterSize*filterSize];
        int counter = 0;
        int step = filterSize/2;
        for(int i = x-step; i < x+step; i++){
            for(int j = x-step; j < x+step; j++){
                values[counter++] = img[i][j];
            }
        }
        Arrays.sort(values);
        return values[values.length/2];
    }
}
