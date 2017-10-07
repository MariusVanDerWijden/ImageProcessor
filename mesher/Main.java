package mesher;

import java.awt.*;

/**
 * Created by matematik on 7/9/16.
 */
public class Main {

    static int colorBlack = new Color(0,0,0).getRGB();
    static int colorWhite = new Color(255,255,255).getRGB();
    static int colorGreenMesh = new Color(0,255,0).getRGB();


    public static void main(String[] args){
        /*
        Old code to test shit
        System.out.println(Integer.toBinaryString(colorBlack)+"  "+Integer.toBinaryString(colorWhite));
        int temp = colorGreenMesh;
        int alpha = temp >>> 24;
        int green = temp << 16 >>> 24;
        int red = temp << 8 >>> 24;
        int blue = temp << 24 >>> 24;
        System.out.println(alpha+" "+red+" "+green+" "+blue);
        */

        Mesher mesher = new Mesher();
        mesher.meshMe("test2/asdf1.jpg");
    }
}
