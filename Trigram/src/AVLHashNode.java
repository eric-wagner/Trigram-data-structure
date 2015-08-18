
public class AVLHashNode {
	public int balance;
    public AVLHashNode left, right;
    AVLTree list;
    int key;
    int height;
           
    long max;
    long sum;

    long leftTreeMax,rightTreeMax;
    long rightTreeSum,leftTreeSum;
        
    AVLHashNode(TrigramAndCount t, int key ,AVLHashNode l ,AVLHashNode r) {      		
    	sum=t.getCount();
    	max=t.getCount();
        list = new AVLTree();
        char[] c;
 	   	try{
    		c=(t.getW1()+" "+ t.getW2()+" "+t.getW3()).substring(6).toCharArray();
    	} catch(Exception e){
    		c= new char[0];
        }
        list.insert(c,t.getCount());
        this.key = key;
        left=l;
        right=r;
    }
    
    public void add(char[] c,int count){
    	sum+=count;
    	max= Math.max(count, max);
    	list.insert(c,count);	
    }
}
