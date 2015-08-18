
 public class AVLTree{

   AVLNode root;
    	
   public AVLTree( ){
	   root = null;
   }

   public void insert( char[] c, int count ){
	   root = insert( c,count, root );
   }

   private AVLNode insert(char[] c, int count, AVLNode t ){
	   if( t == null ){
    		t = new AVLNode( c, count, null, null );
	   } else if( ComplexTrigramStorage.compare( c, t.t ) < 0 ){
        	t.left = insert( c,count, t.left );
            if( height( t.left ) - height( t.right ) == 2 )
            	if( ComplexTrigramStorage.compare(c , t.left.t ) < 0 )
            		t = rotLeft( t );
                else
                    t = doubleRotLeft( t );
       } else if( ComplexTrigramStorage.compare( c, t.t) >= 0 ) {
            t.right = insert( c,count, t.right );
            if( height( t.right ) - height( t.left ) == 2 )
            	if( ComplexTrigramStorage.compare(c, t.right.t) > 0 )
            		t = rotRight( t );
            	else
            		t = doubleRotRight( t );
       }
       t.height = max( height( t.left ), height( t.right ) ) + 1;
       return t;
   }

   	private static int height( AVLNode t ){
   		return t == null ? -1 : t.height;
    }

    private static int max( int lhs, int rhs ){
    	return lhs > rhs ? lhs : rhs;
    }

    private static AVLNode rotLeft( AVLNode k2 ){
    	AVLNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    private static AVLNode rotRight( AVLNode k1 ){
    	AVLNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    private static AVLNode doubleRotLeft( AVLNode k3 ){
        k3.left = rotRight( k3.left );
        return rotLeft( k3 );
    }

    private static AVLNode doubleRotRight( AVLNode k1 ){
        k1.right = rotLeft( k1.right );
        return rotRight( k1 );
    }
}

