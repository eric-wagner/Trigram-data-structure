import java.util.Random;

public class RandomTest
{
    public static int getIntVal(char val) {
        if (val == 0) return 0;
        else return val < 87 ? val - 48 + 1 : val - 97 + 11; // ASCII: 0..9 starts at 48, a..z starts at 97, index 0 for next word
    }

    public static char getCharVal(int val) {
        return val <= 10 ? (char) (val + 48 - 1) : (char) (val + 87 - 1); // ASCII: 0..9 starts at 48, a..z starts at 97, index 0 for next word 
    }

    public static String outputTri(Trigram tri) {
        return "("+tri.getW1()+","+tri.getW2()+","+tri.getW3()+")"+ (tri instanceof TrigramAndCount ? ", count("+((TrigramAndCount) tri).getCount()+")" : "");
    }
    
    public static void main(String[] args) {
        int maxWord = 15;
        int maxTris = 1000000;
        int maxReqs = 10000000;
        int steps = 10;
        ComplexTrigramStorage tris = new ComplexTrigramStorage();
        Random rand = new Random();
        String[] w = new String[3];
        
        for (int i = 0; i < maxTris; i++) {
            int length = 0;
            
            
            for (int z = 0; z < 3; z++) {
                w[z] = "";
                length = rand.nextInt(maxWord) + 1;
                int j = 0;
                for (j = 0; j < length; j++) {
                    w[z]+= getCharVal(rand.nextInt(36)+1);
                }
            }
            
            int count = rand.nextInt(1000 + 1);
            
            TrigramAndCount tri = new TrigramAndCount(w[0],w[1], w[2], count);
            if (i % (maxTris / steps) == 0) System.out.println("Inserting #"+i+": "+outputTri(tri));
            tris.insert(tri);
        }
        tris.build();
        System.out.println("Built");
        w = new String[6];
        for (int i = 0; i < maxReqs; i++) {
            int length = 0;
            
            for (int z = 0; z < w.length; z++) {
                w[z] = "";
                length = rand.nextInt(maxWord) + 1;
                for (int j = 0; j < length; j++) {
                    w[z]+= getCharVal(rand.nextInt(36)+1);
                }
            }
            Trigram tri1 = new Trigram(w[0], w[1], w[2]);
            Trigram tri2 = new Trigram(w[3], w[4], w[5]);
            if (i % (maxReqs / steps) == 0) System.out.println("At request #"+i);
//             System.out.println("New Query: "+ThreeGrammStorage.outputTri(tri1)+" and "+ThreeGrammStorage.outputTri(tri2));
            tris.query(tri1, tri2);}
    }
}
