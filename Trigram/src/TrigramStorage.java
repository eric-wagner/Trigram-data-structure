public interface TrigramStorage {
	
	public void insert(TrigramAndCount trigram);
	
	public void build();
	
	public QueryResult query(Trigram trigram1, Trigram trigram2);

}
