public class AVLNode {
	public char[] t;  
    public AVLNode left, right;
    public long sum=0;
    public long max=0;   
    public long rightTreeMax;
    public long rightSum;
    public long leftSum;
    public long leftTreeMax; 
    public long treeSum;
	public int height;
    public int balance; 
    
    AVLNode(char[] c,int count, AVLNode l ,AVLNode r) {      		
    	sum=count;
    	max=count;
    	t=c;
    	left=l;
        right=r;
    }
 
}
