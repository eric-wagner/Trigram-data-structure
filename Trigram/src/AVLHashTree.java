
public class AVLHashTree {
	AVLHashNode root;
	
	   public AVLHashTree( ){
		   root = null;
	   }

	   public void insert( TrigramAndCount x ){
		   int key = ComplexTrigramStorage.hash((x.getW1()+" "+x.getW2()+" "+x.getW3()).toCharArray());
		   root = insert( x, key, root );
	   }

	   private AVLHashNode insert( TrigramAndCount x,int key, AVLHashNode t ){
		   if( t == null ){
	    		t = new AVLHashNode( x, key, null, null );
		   } else if ( key < t.key ){
	        	t.left = insert( x,key, t.left );
	            if( height( t.left ) - height( t.right ) == 2 )
	            	if( key < t.left.key)
	            		t = rotLeft( t );
	                else
	                    t = doubleRotLeft( t );
	       } else if( key > t.key) {
	            t.right = insert( x,key , t.right );
	            if( height( t.right ) - height( t.left ) == 2 )
	            	if( key > t.right.key )
	            		t = rotRight( t );
	            	else
	            		t = doubleRotRight( t );
	       } else {
	    	   char[] c;
	    	   try{
	       		c=(x.getW1()+" "+ x.getW2()+" "+x.getW3()).substring(6).toCharArray();
	       		} catch(Exception e){
	       		c= new char[0];
	           }
	        	t.add(c,x.getCount());
	       }
		   
	       t.height = max( height( t.left ), height( t.right ) ) + 1;
	       return t;
	   }

	   	private static int height( AVLHashNode t ){
	   		return t == null ? -1 : t.height;
	    }

	    private static int max( int lhs, int rhs ){
	    	return lhs > rhs ? lhs : rhs;
	    }

	    private static AVLHashNode rotLeft( AVLHashNode k2 ){
	    	AVLHashNode k1 = k2.left;
	        k2.left = k1.right;
	        k1.right = k2;
	        k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
	        k1.height = max( height( k1.left ), k2.height ) + 1;
	        return k1;
	    }

	    private static AVLHashNode rotRight( AVLHashNode k1 ){
	    	AVLHashNode k2 = k1.right;
	        k1.right = k2.left;
	        k2.left = k1;
	        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
	        k2.height = max( height( k2.right ), k1.height ) + 1;
	        return k2;
	    }

	    private static AVLHashNode doubleRotLeft( AVLHashNode k3 ){
	        k3.left = rotRight( k3.left );
	        return rotLeft( k3 );
	    }

	    private static AVLHashNode doubleRotRight( AVLHashNode k1 ){
	        k1.right = rotLeft( k1.right );
	        return rotRight( k1 );
	    }
}
