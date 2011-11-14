package ga;

    /*
 * Fitness4.java
 *
 * Something a little more interesting.
 */

/**
 *
 * @author  levenick
 */
public class Fitness4 {
    private static final int ONE=10;
    private static final int PAIR=300;
    private static final int TRIPS=700;
    private static final int FIVE=123456;
    private static final int GENE_LENGTH=8;
    private static final int NUM_GENES=5;

    private static boolean [] has = new boolean[NUM_GENES];

    private static int numberGenesItHas() {
        int count=0;

        for (int i=0; i<NUM_GENES; i++)
            if (has[i])
                count++;

        return count;
    }

    private static boolean hasPair() {
        return has[1] && has[3];
    }

    private static boolean hasTrips() {
        return has[0] && has[2] && has[4];
    }

    private static boolean hasGene(Evaluable chromo, int whichGene) {
        byte [] bits = chromo.getDNA();  // Chromosome needs boolean [] getBits() -- sorry
        int length = bits.length;
        int distance = length/NUM_GENES;
        int start = whichGene * distance;

        for (int i=start; i<start+GENE_LENGTH; i++)
            if (bits[i] == 0)
                return false;

        return true;
    }

    private static void findGenes(Evaluable chromo) {
        for (int i=0; i<NUM_GENES; i++) {
            has[i] = hasGene(chromo, i);
        }
    }

    public static int getValue(Evaluable chromo) {
        findGenes(chromo);

        if (numberGenesItHas() == 1)
            return ONE;
        if (numberGenesItHas() == 5)
            return FIVE;
        if (hasPair())
            return PAIR + numberGenesItHas() * ONE;
        if (hasTrips())
            return TRIPS + numberGenesItHas() * ONE;


        return 1;
    }

    public String toString() {
        return "Fitness4";
    }
}


