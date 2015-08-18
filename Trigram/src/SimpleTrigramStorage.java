import java.util.ArrayList;

public class SimpleTrigramStorage implements TrigramStorage {
	
	SimpleTrigramStorage() {
		trigrams = new ArrayList<TrigramAndCount>();
	}
	
	@Override
	public void insert(TrigramAndCount trigram) {
		trigrams.add(trigram);
	}

	@Override
	public void build() {
		// Intentionally left empty.
	}
	
	@Override
	public QueryResult query(Trigram trigram1, Trigram trigram2) {
		long max = 0;
		long sum = 0;
		
		for (TrigramAndCount trigram : trigrams) {
			if (trigram1.compareTo(trigram) <= 0 && trigram.compareTo(trigram2) <= 0) {
				max = Math.max(max, trigram.getCount());
				sum += trigram.getCount();
			}
		}
		
		return new QueryResult(max, sum);
	}

	protected ArrayList<TrigramAndCount> trigrams;
}
