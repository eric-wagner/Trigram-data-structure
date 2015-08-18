public class TrigramAndCount extends Trigram {
	TrigramAndCount(String w1, String w2, String w3, int count) {
		super(w1, w2, w3);
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}
	
	private int count;
}
