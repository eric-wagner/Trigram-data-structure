import java.io.IOException;

public class TrigramMain {

	private static void testStorage(TrigramStorage storage, String trigramFilename, String queryFilename, boolean verbose) {
		try {
			TrigramFileReader tfr = new TrigramFileReader(trigramFilename, storage, verbose);
		} catch (IOException e) {
			System.out.println("Error while reading the trigrams. Traceback:");
			e.printStackTrace();
			System.exit(-1);
		}
		try {
			TrigramQueryReader tqr = new TrigramQueryReader(queryFilename, storage, verbose);
		} catch (IOException e) {
			System.out.println("Error while reading the queries. Traceback:");
			e.printStackTrace();
			System.exit(-1);
		} catch (ImplementationFailException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}
	
	static boolean tower=false;
	
	public static void main(String[] args) {
		/*if (args.length < 2) {
			System.out.println("Wrong usage. Please supply the necessary parameters.");
			System.out.println();
			System.out.println("Usage: java TrigramMain [-v] <TrigramFile> <QueryFile>");
			System.exit(-1);
		} else {*/
			boolean verbose = false;
			String trigramFile;
			String queryFile;
			/*if (args[0].equals("-v")) {
				verbose = true;				
				trigramFile = args[1];
				queryFile = args[2];
			*/	
				if(tower){
					trigramFile = "C:\\Users\\Public\\Documents\\workspace\\Trigram\\input\\ulysses.in"; //args[1];
					queryFile = "C:\\Users\\Public\\Documents\\workspace\\Trigram\\input\\ulysses-queries1.in"; //args[2];
				} else {
					trigramFile = "C:\\Users\\Eric\\workspace\\Trigram\\input\\largeinput.in";
					queryFile = "C:\\Users\\Eric\\workspace\\Trigram\\input\\test.in";
				}
			/*} else {
				trigramFile = args[0];
				queryFile = args[1];
			}*/
			testStorage(new ComplexTrigramStorage(), trigramFile, queryFile, verbose);
		}
	}

