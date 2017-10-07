package preprocessor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProcessorIO {

    public static BufferedImage loadImage(String filename){
        try{
            File f = new File(filename);
            if(!f.exists())return null;
            BufferedImage img = ImageIO.read(f);
            if(img!=null)return img;
            return new BufferedImage(0,0,BufferedImage.TYPE_3BYTE_BGR);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static int[][] bufferedImageToIntArray(BufferedImage img){
        int[][] result = new int[img.getWidth()][img.getHeight()];
        for(int x = 0; x < result.length;x++){
            for(int y = 0; y < result[0].length; y++){
                result[x][y] = img.getRGB(x,y);
            }
        }
        return result;
    }

    public static BufferedImage intArrayToBufferedImage(int[][] img){
        return intArrayToBufferedImage(img,img.length,img[0].length);
    }

    public static BufferedImage intArrayToBufferedImage(int[][] img,int width,int height){
        BufferedImage oldImg = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        for(int x = 0; x < img.length;x++){
            for(int y = 0; y < img[0].length; y++){
                oldImg.setRGB(x,y,img[x][y]);
            }
        }
        return oldImg;
    }

    public static boolean writeImageToFile(BufferedImage img, String filename, String ending){
        try{
            String f = filename+"."+ending;
            File file = new File(f);
            if(file.exists()){
                System.out.println("File already exists");
                return false;
            }
            if(ImageIO.write(img, ending, file)) {
                System.out.println("Successfully written to File");
                return true;
            }
            System.out.println("Write unsuccessful");
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
