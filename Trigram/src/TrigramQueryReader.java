import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TrigramQueryReader {
	
	TrigramQueryReader(String queryInputFile, TrigramStorage storage, boolean verbose) throws IOException, ImplementationFailException {
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(queryInputFile)));
			
			long totalTime = 0;
			boolean verifiedResults = true;
			int line = -1;
			int currentQuery = 1;		
			
			while (fileReader.ready()) {
				line++;
				String nextLine = fileReader.readLine();
				
				if ((nextLine != null && nextLine.startsWith("#")) || nextLine.isEmpty()) {
					continue;
				}
				
				String[] tokens = nextLine.split("\\s+");
				if (tokens.length != 6 && tokens.length != 8) {
					throw new IOException("Invalid syntax in line " + line + " (" + nextLine + ").");
				}
				
				long queryStart = System.nanoTime();
				QueryResult implementationResult = storage.query(new Trigram(tokens[0], tokens[1], tokens[2]), new Trigram(tokens[3], tokens[4], tokens[5]));
				long queryTime = System.nanoTime() - queryStart;
				totalTime += queryTime;
				
				if (verbose) {
					System.out.println("Answer for query " + currentQuery + " is " + implementationResult + " (computed in " + (queryTime / 1000) + "us).");
				}
				
				if (tokens.length == 8) {
					QueryResult correctResult = new QueryResult(Long.parseLong(tokens[6]), Long.parseLong(tokens[7]));
				
					if (!implementationResult.equals(correctResult)) {
						throw new ImplementationFailException("The returned answer is incorrect. The implementation returned " + implementationResult + ", but expected " + correctResult);
					}
				} else {
					verifiedResults = false;
				}
				
				++currentQuery;
			}
			if (!verifiedResults) {
				System.out.println("WARNING: there were queries for which the returned result was not verified. There may be incorrect results.");
			}
			System.out.println("All queries were answered successfully in " + (totalTime / 1000 / 1000) + "ms.");
		} catch (IOException e) {
			System.out.println("IO Error while reading the query input file: " + e.getMessage());
			throw e;
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
			
		}
	}
	
}
