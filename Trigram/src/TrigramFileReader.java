import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TrigramFileReader {
	
	TrigramFileReader(String trigramInputFile, TrigramStorage storage, boolean verbose) throws IOException {
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(trigramInputFile)));
			
			long insertionStart = System.nanoTime();
			long rawInsertionTime = 0;
			while (fileReader.ready()) {
				String nextLine = fileReader.readLine();
				
				if (nextLine != null && nextLine.startsWith("#")) {
					continue;
				}
				
				String[] tokens = nextLine.split("\\s+");
								
				if (tokens.length != 4) {
					throw new IOException("Invalid syntax.");
				}
				
				long rawInsertionStart = System.nanoTime();
				storage.insert(new TrigramAndCount(tokens[0], tokens[1], tokens[2], Integer.parseInt(tokens[3])));
				rawInsertionTime += (System.nanoTime() - rawInsertionStart);
			}
			long totalInsertionTime = (System.nanoTime() - insertionStart);

			if (verbose) {
				System.out.println("All trigrams were successfully inserted in " + totalInsertionTime / 1000 / 1000 + "ms (" + rawInsertionTime / 1000 / 1000 + "ms raw).");
			}
			
			long buildStart = System.nanoTime();
			storage.build();
			long totalBuildTime = (System.nanoTime() - buildStart);
			
			if (verbose) {
				System.out.println("The data structure was built succesfully in " + totalBuildTime / 1000 / 1000 + "ms.");
			}
			totalInsertionTime = (System.nanoTime() - insertionStart);
			
			System.out.println("Inserted all items and built data structure in " + totalInsertionTime / 1000 / 1000 + "ms.");
			
		} catch (IOException e) {
			System.out.println("IO Error while reading the trigram input file: " + e.getMessage());
			throw e;
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
		}
	}

}
