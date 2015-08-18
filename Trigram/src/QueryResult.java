public class QueryResult {

	QueryResult(long max, long sum) {
		this.max = max;
		this.sum = sum;
	}
	
	public long getSum() {
		return sum;
	}
	
	public long getMax() {
		return max;
	}
	
	public String toString() {
		return "(" + this.getMax() + ", " + this.getSum() + ")";
	}
	
	public boolean equals(QueryResult other) {
		return this.getMax() == other.getMax() && this.getSum() == other.getSum();
	}
	
	private long max;
	private long sum;
	
}
