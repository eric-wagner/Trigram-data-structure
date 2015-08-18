public class Trigram implements Comparable<Trigram> {

	Trigram(String w1, String w2, String w3) {
		this.w1 = w1;
		this.w2 = w2;
		this.w3 = w3;
	}
	
	String getW1() {
		return w1;
	}
	
	String getW2() {
		return w2;
	}

	String getW3() {
		return w3;
	}
	
	public int compareTo(Trigram other) {
		int firstComparison = w1.compareTo(other.getW1());
		if (firstComparison != 0) {
			return firstComparison;
		}
		
		int secondComparison = w2.compareTo(other.getW2());
		if (secondComparison != 0) {
			return secondComparison;
		}
		
		return w3.compareTo(other.getW3());
	}
	
	public String toString() {
		return "<" + this.w1 + ", " + this.w2 + ", " + this.w3 + ">";
	}

	// Data members.
	private String w1;
	private String w2;
	private String w3;

}
