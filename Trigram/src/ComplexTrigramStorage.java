import java.util.Arrays;

public class ComplexTrigramStorage implements TrigramStorage{

	AVLHashTree tree;
	
	
	public ComplexTrigramStorage() {
		tree  =  new AVLHashTree();
	}
	
	@Override
	public void insert(TrigramAndCount trigram) {
		tree.insert(trigram);			
	}

	@Override
	public void build() {
		root=tree.root;
		calcValues(tree.root);		
	}
		
	
	
	/******************************************
	 * Calculates the maxims and sums in each
	 * AVLHashNode.
	 *******************************************/
	
	private void calcValues(AVLHashNode n){
		if(n==null)
			return;
		
		calcAVL(n.list.root);
		
		calcValues(n.left);
		calcValues(n.right);
		
		if(n.left==null){
			n.leftTreeMax=0;
			n.leftTreeSum=0;
		} else {
			n.leftTreeMax = (int) max(n.left.max, n.left.leftTreeMax, n.left.rightTreeMax);
			n.leftTreeSum = n.left.leftTreeSum+n.left.rightTreeSum+n.left.sum;
		}	
		if (n.right==null){
			n.rightTreeMax=0;
			n.rightTreeSum=0;
		} else {
			n.rightTreeMax = (int) max(n.right.max, n.right.leftTreeMax, n.right.rightTreeMax);
			n.rightTreeSum = n.right.leftTreeSum+n.right.rightTreeSum+n.right.sum;
		}	
	}
	
	
	
	/******************************************
	 * Calculates the maxims and sums in each
	 * AVLNode.
	 *******************************************/
	
	private void calcAVL(AVLNode n){
		if(n==null)
			return;
		
		calcAVL(n.right);
		calcAVL(n.left);
		
		if(n.left==null){
			if(n.right==null){
				n.treeSum=n.sum;
			} else {
				n.treeSum= n.sum + n.right.treeSum;
			}
		} else if (n.right==null){	
			n.treeSum= n.sum + n.left.treeSum;
		} else {
			n.treeSum= n.sum + n.left.treeSum + n.right.treeSum;
		}
		
		if(n.left==null){
			n.leftTreeMax=n.max;
			n.leftSum=0;
		} else {
			n.leftTreeMax =(int) max(n.max,n.left.leftTreeMax, n.left.rightTreeMax);
			n.leftSum=n.left.leftSum+n.left.rightSum+n.left.sum;
		}
		
		if(n.right==null){
			n.rightTreeMax=n.max;
			n.rightSum=0;
		} else {
			n.rightTreeMax =(int) max(n.max, n.right.leftTreeMax, n.right.rightTreeMax);
			n.rightSum=n.right.leftSum+n.right.rightSum+n.right.sum;
		}
		
	}
	
	

	/******************************************
	 * Searches for all the trigrams that are
	 * lexicographically smaller than the first
	 * input trigram.
	 *******************************************/
	
	private void searchSmallerInNode(AVLNode n) {
		while(n!=null){
			f2= compare(c2,n.t);
			if(f2>0){
				sum+=n.leftSum+n.sum;
				max= max(max, n.leftTreeMax);
				n=n.right;
			} else if (f2<0) {
				n=n.left;
			} else {
				sum+=n.sum;
				max= max(max, n.max);
				n=n.left;
			}	
		}	
	}
	
	
	
	/******************************************
	 * Searches for all the trigrams that are
	 * lexicographically bigger than the first
	 * input trigram.
	 *******************************************/

	private void searchBiggerInNode(AVLNode n) {
		while(n!=null){
			f1= compare(c1,n.t);
			if(f1<0){
				sum+=n.rightSum+n.sum;
				max= max(max, n.rightTreeMax);
				n=n.left;
			} else if (f1>0) {
				n=n.right;
			} else {
				sum+=n.sum;
				max= max(max, n.max);
				n=n.right;
			}	
		}			
	}

	
	
	/******************************************
	 * Searches for the of the first and 
	 * second hash values and add up the sums of
	 * the nodes in between, as well as 
	 * calculating the maximum of the counts in
	 * between. 
	 *******************************************/
	
	private int f1,f2;
	
	private void searchNode(AVLNode n,boolean hasPrevPassedAndRightChild, boolean hasPrevPassedAndLeftChild){
		if(n==null)
			return;
	
		f1 = compare(c1,n.t);
		f2 = compare(c2,n.t);

		if (f1>0){
			searchNode(n.right,false,false);
		} else if (f2<0){
			searchNode(n.left,false,false);
		} else if (f1==0) {
			sum+=n.sum;
			max= max(max, n.max);
			searchNode(n.right,true,false);
		} else if (f2==0) {
			sum+=n.sum;
			max= max(max, n.max);
			searchNode(n.left,false,true);
		} else {
			max =  max(max, n.max);
			sum += n.sum;
			
			if(hasPrevPassedAndRightChild){	
				sum += n.leftSum;
				max =  max(max, n.leftTreeMax);				
			} else{
				searchNode(n.left,false,true);
			}
			if(hasPrevPassedAndLeftChild){
				sum += n.rightSum;
				max =  max(max, n.rightTreeMax);
			}else{
				searchNode(n.right,true,false);
			}			
		}
	}
	
	
	
	/******************************************
	 * Searches for the Node of the first and 
	 * second hash values and add up the sums of
	 * the nodes in between, as well as 
	 * calculating the maximum of the counts in
	 * between. 
	 *******************************************/
	
	long sum,max;
	
	private void search(AVLHashNode n,boolean hasPrevPassedAndRightChild, boolean hasPrevPassedAndLeftChild){
		if(n==null)
			return;
	
		if (hash1>n.key){
			search(n.right,false,false);
		} else if (hash2<n.key){
			search(n.left,false,false);
		} else if (hash1==n.key) {
			searchBiggerInNode(n.list.root);
			search(n.right,true,false);
		} else if (hash2==n.key) {
			searchSmallerInNode(n.list.root);	
			search(n.left,false,true);
		} else {
			max =  max(max, n.max);
			sum += n.sum;	
			if(hasPrevPassedAndRightChild){
				sum += n.leftTreeSum;
				max =  max(max, n.leftTreeMax);
			} else {
				search(n.left,false,true);
			}
			
			if(hasPrevPassedAndLeftChild){
					sum += n.rightTreeSum;
					max =  max(max, n.rightTreeMax);
				
			} else {
				search(n.right,true,false);
			}		
		}	
	}
	
	
	
	/******************************************
	 * Searches for the Node where the real 
	 * search starts in case that both input
	 * trigrams where hashed to the same code.
	 *******************************************/
	
	private void search(AVLHashNode n) {
		if(n==null)
			return;
		
		if(n.key>hash1)
			search(n.left);		
		else if(n.key<hash1)
			search(n.right);
		else
			searchNode(n.list.root,false,false);
	}
	
	
	
	/******************************************
	 * Compares two Strings and returns:
	 * ->  0  , if both Strings are equal
	 * ->  1  ,	if s1 is lexicographically
	 * 			bigger than s2
	 * -> -1  ,	if s1 is lexicographically
	 * 			smaller than s2
	 ******************************************/
	
	private static int min;
	private static int j;
	
	public static int compare(char[] s1, char[] s2) {
		min = min(s1.length, s2.length);
		j=0;
		while(j<min){
			if(s1[j]!=s2[j]){
				if(s1[j]==32)
					return -1;
				if(s2[j]==32)
					return 1;
				if(s1[j]>s2[j])
					return 1;
				else
					return -1;
			}
			j++;
		}
		if(s1.length==s2.length)
			return 0;
		else if(s1.length>s2.length)
			return 1;
		else
			return -1;	
	}
	
	
	
	/******************************************
	 * The Math.max() respectively Math.min()
	 * functions rewritten and reduced to
	 * what is needed
	 ******************************************/
	
	private static int min(int one, int two) {
        return one < two ? one : two;
    }
	
	private long max(long one, long two) {
        return one < two ? two : one;
    }
	
    private long max(long one, long two, long three) {
        if (one < two) {
            if (two < three) {
                return three;
            } else {
                return two;
            }
        } else {
            if (one < three) {
                return three;
            } else {
                return one;
            }
        }
    }
    
    
    
	/******************************************
	 * Initializes the search for a query by
	 * hashing the 2 input strings and starting
	 * the search with the part of the input
	 * that didn't influence the hash code
	 ******************************************/
	
	static private final char[] empty=new char[0];
	private AVLHashNode root;
	private char[] c1,c2;
	private int hash1;
	private int hash2;
	
	@Override
	public QueryResult query(Trigram trigram1, Trigram trigram2) {
		
		max = 0;
		sum = 0;
		
		c1 = (trigram1.getW1()+" "+trigram1.getW2()+" "+trigram1.getW3()).toCharArray();
		c2 = (trigram2.getW1()+" "+trigram2.getW2()+" "+trigram2.getW3()).toCharArray();
		
		hash1 = hash(c1);
		hash2 = hash(c2);
		

		if(c1.length>6)
			c1 =Arrays.copyOfRange(c1, 6, c1.length);
		else
			c1=empty;
		if(c2.length>6)
			c2 = Arrays.copyOfRange(c2, 6, c2.length);
		else
			c2=empty;
			
		if(hash1 != hash2)
			search(root,false,false);
		else
			search(root);
		
		return new QueryResult(max,sum);
	}
	
	
	
	/*****************************************
	 * Returns a hash value so that
	 * a lexicographically smaller input
	 * produces a smaller or equal value
	 * and a lexicographically bigger input 
	 * produces a bigger  or equal value.
	 *****************************************/
	
	static private final int X[] = {79235168,2085136,54872,1444,38,1};
	static private final int K = -(int)  Math.pow(2, 31);
	static private int lenght;
	static private int temp;
	static private int c;
	static private int k;
	
	public static int hash(char[] s){
		
		temp=K;
		lenght = s.length;
		if (lenght>6)
			lenght=6;
		k=0;
		while(k<lenght){
			c = s[k];
			if(c>64){						// in case of letter
				temp+=X[k++]*(c-86);
			} else if(c==32) {				// in case of space
				k++;
			} else {						// in case of number
				temp+=X[k++]*(c-47);
			}
		}
		return temp;
	}
}
