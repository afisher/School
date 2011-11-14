package ga;

/**
 * Fitness1.java   --  created on Oct 26, 2011, 11:11:22 AM
 * @author levenick
 */

public class Fitness1 {


    public static int getValue(Evaluable chromo) {
        byte [] bits = chromo.getDNA();
        return countOnes(bits);
    }

    static int countOnes(byte[] bits) {
        int count=0;

        for (int i=0; i<bits.length; i++) {
            if (bits[i]==1) {
                count++;
            }
        }

        return count;
    }



}
