package project2;

import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	//The BST root
    private Node<AnyType> root;
    
    //Class constructor of BST
    public BinarySearchTree() {
		root = null;
	}
    
    //Basic node stored in unbalanced binary search trees
    private static class Node<AnyType> {
    	//Node data and children
        AnyType element;           
        Node<AnyType> left; 
        Node<AnyType> right;
        
        //Constructor of leaf
        Node (AnyType elem) {
            this(elem, null, null);
        }

        //Constructor of non leaf
        Node (AnyType elem, Node<AnyType> lt, Node<AnyType> rt) {
            element  = elem;
            left = lt;
            right = rt;
        }
    }
	
    //Insert node
    public void insert(AnyType x) {
        root = insert(x, root);
    }
    
    //Insert node logic
    private Node<AnyType> insert(AnyType data, Node<AnyType> node) {
    	//If null, return new leaf node with the data value 
        if (node == null)
            return new Node<>(data, null, null);
        
        //Compare to each element 
        int compareResult = data.compareTo(node.element);
         
        //If less than node value, recursively call as left child
        if (compareResult < 0)
            node.left = insert(data, node.left);
        //If greater than node value, recursively call as right child
        else if (compareResult > 0)
            node.right = insert(data, node.right);
        
        //Duplicate; do nothing
        else;  
        return node;
    }
    
    //Remove node
    public void remove(AnyType x) {
        root = remove(x, root);
    }
    
    //Remove node logic
    private Node<AnyType> remove(AnyType data, Node<AnyType> node) {
    	//Item not found; do nothing
        if (node == null)
            return node;  
        
        //Compare to each element 
        int compareResult = data.compareTo(node.element);
        
        //If less than node value, recursively call as left child
        if (compareResult < 0)
            node.left = remove(data, node.left);
        //If greater than node value, recursively call as right child
        else if (compareResult > 0)
            node.right = remove(data, node.right);
        
        //Two children
        else if (node.left != null && node.right != null) {
        	//Find element of least node greater than current node
            node.element = findMin(node.right).element;
            //Recursively call the right child to find that element
            node.right = remove(node.element, node.right);
        }
        //Make node left or right child based on if left is null
        else 
            node = (node.left != null) ? node.left : node.right;
        return node;
    }
    
    //Make tree empty
    public void makeEmpty() {
        root = null;
    }
    
    //Is tree empty?
    public boolean isEmpty() {
        return root == null;
    }
    
    //Find the smallest item in the tree.
    public AnyType findMin() throws Exception {
    	//Throw exception if empty
        if (isEmpty())
            throw new Exception();
        
        //Call helper method starting from root's element
        return findMin(root).element;
    }
    
    private Node<AnyType> findMin(Node<AnyType> node) {
    	//If null, do nothing
        if (node == null)
            return null;
        
        //If left is null, we found the minimum
        else if (node.left == null)
            return node;
        //Recursively call left child to find minimum
        return findMin(node.left);
    }

    //Find the largest item in the tree.
    public AnyType findMax() throws Exception {
    	//Throw exception if empty
        if (isEmpty())
            throw new Exception();
        
        //Call helper method starting from root's element
        return findMax(root).element;
    }
    
    private Node<AnyType> findMax(Node<AnyType> node){
    	//Run if not null
        if (node != null) {
        	//Find rightmost node and return
            while (node.right != null)
                node = node.right;
        }
        return node;
    }
    
    /*
    //See if element exists in BST
    public boolean contains(AnyType data){
        return contains(data, root );
    }
    
    //Internal method to find an item in a subtree.
    private boolean contains(AnyType data, Node<AnyType> node) {
    	//If null or not found, return false
        if (node == null)
            return false;
            
        //Compare data to node element 
        int compareResult = data.compareTo(node.element);
            
        //If less, recursively call left child
        if (compareResult < 0)
            return contains(data, node.left);
        //If greater, recursively call right child
        else if (compareResult > 0)
            return contains(data, node.right);
        //Found, return true
        else
            return true;
    }
    */
    
   //Print the tree contents in sorted order.
   public void printTree() {
	   //If empty, say it's empty
       if(isEmpty())
           System.out.println("Empty tree");
       
       //Print contents starting from root node
       else
           printTree(root);
   }
   
   private void printTree(Node<AnyType> node) {
	   //Print in an in-order sort
       if (node != null) {
           printTree(node.left);
           System.out.println(node.element);
           printTree(node.right);
       }
   }
   
   public int nodeCount() {
	   return nodeCount(root);
   }
   
   private int nodeCount(Node<AnyType> t) {
	   //If root is null, 0 nodes
	   if (t == null)
		   return 0;
	   //Else, root nodes plus nodes in left and right subtrees is node count
	   else
		   return 1 + nodeCount(t.left) + nodeCount(t.right);
   }
   
   public boolean isFull() {
	   return isFull(root);
   }
   
   private boolean isFull(Node<AnyType> t) {
	   //Node is null, BST is full
	   if (t == null)
		   return true;
	   //Node has no children, BST is full
	   else if (t.right == null && t.left == null)
		   return true;
	   
	   //Uneven, BST isn't full
	   else if (t.left == null || t.right == null)
		   return false;
	   
	   //If has both children
	   else {
		   //See if subtrees are full
		   boolean rightFull = isFull(t.right);
		   boolean leftFull = isFull(t.left);
		   return leftFull && rightFull;
	   }
   }
   
   public boolean compareStructure(BinarySearchTree<AnyType> t) {
	   return compareStructure(root, t.root);
   }
   
   private boolean compareStructure(Node<AnyType> t1, Node<AnyType> t2) {
	   //If both nodes are null, same structure
	   if (t1 == null && t2 == null)
		   return true;
	   
	   //If only one or other null, not same structure
	   else if (t1 == null || t2 == null)
		   return false;
	   
	   else {
		   //Go down left and right to see if structure is same
		   boolean leftEqual = compareStructure(t1.left, t2.left);
		   boolean rightEqual = compareStructure(t1.right, t2.right);
		   
		   //Structure must equal
		   return leftEqual && rightEqual;
	   }	   	   
   }
   
   public boolean equals(BinarySearchTree<AnyType> t) {
	   return equals(root, t.root);
   }
   
   private boolean equals(Node<AnyType> t1, Node<AnyType> t2) {
	   //If both nodes are null, equal
	   if (t1 == null && t2 == null)
		   return true;
	   
	   //If only one or other null, not equal
	   else if (t1 == null || t2 == null)
		   return false;
	   
	   else {
		   //Go down left and right to see if subtrees equal
		   boolean leftEqual = equals(t1.left, t2.left);
		   boolean rightEqual = equals(t1.right, t2.right);
		   
		   //Structure and values must equal
		   return leftEqual && rightEqual && (t1.element.compareTo(t2.element) == 0);
	   }
   }
   
   
   public Node<AnyType> copy() {
	   return copy(root);
   }
   
   private Node<AnyType> copy(Node<AnyType> t) {
	   //If null, return null
	   if (t == null)
		   return null;
	   
	   else {
		   //Temporary node at part of tree
		   Node<AnyType> copyNode = new Node<AnyType>(t.element);
		   
		   //Copy left and right children
		   copyNode.left = copy(t.left);
		   copyNode.right = copy(t.right);
		   return copyNode;
	   }
   }
   
   public Node<AnyType> mirror() {
	   return mirror(root);
   }
   
   private Node<AnyType> mirror(Node<AnyType> t) {
	   //If node is null
	   if (t == null)
		   return null;
	   
	   else {
		   //One node at tree, another node to swam
		   Node<AnyType> temp = mirror(t.left);
		   
		   //Swap left and right nodes
		   t.left = mirror(t.right);
		   t.right = temp;
		   return t;
	   }
   }
   
   public boolean isMirror(BinarySearchTree<AnyType> t) {
	   return isMirror(t.copy(), t.mirror());
   }
   
   public boolean isMirror(BinarySearchTree<AnyType> t1, BinarySearchTree<AnyType> t2) {
	   return isMirror(t1.root, t2.root);
   }
   
   private boolean isMirror(Node<AnyType> t1, Node<AnyType> t2) {
	   //Both are null, true
	   if (t1 == null && t2 == null)
		   return true;
	   
	   //If one of two are not equal, false
	   else if (t1 == null || t2 == null)
		   return false;

	   else {
		   //Recursive mirror with left and right children of each other
		   boolean mirror1 = isMirror(t1.left, t2.right);
		   boolean mirror2 = isMirror(t1.right, t2.left);
		   
		   //See if mirror image equal
		   return mirror1 && mirror2 && (t1.element.compareTo(t2.element) == 0);
	   }
   }
   
   private Node<AnyType> find(AnyType elem) {
	   Node<AnyType> t = root;
	   
	   //While node not null, find
	   while (t != null) {	   
		   //If found, return, else go left or right based on current element
		   if (elem.compareTo(t.element) == 0)
			   return t;
		   else if (elem.compareTo(t.element) < 0)
			   t = t.left;
		   else
			   t = t.right;
			   
	   }
	   return null;
   }
   
   private Node<AnyType> findParent(Node<AnyType> t, AnyType elem) {
	   Node<AnyType> parent = null;
	   
	   //While children exist, iterate
	   while (t != null) {	   
		   //Update parent and current node based on values
		   if (elem.compareTo(t.element) < 0) {
			   parent = t;
			   t = t.left;
		   }
		   else if (elem.compareTo(t.element) > 0) {
			   parent = t;
			   t = t.right;
		   }
		   else
			   break;
	   }
	   return parent;
   }
   
   public void rotateRight(AnyType elem) {
	   //Find node to rotate and its parent
	   Node<AnyType> elemToRotate = find(elem);
	   Node<AnyType> parent = findParent(root, elem);
	   
	   //New node in place
	   Node<AnyType> elemLeftChild = null;
	   if (elemToRotate != null)
		   elemLeftChild = elemToRotate.left;
	   
	   //If node to rotate and left child are not null
	   if (elemToRotate != null && elemLeftChild != null) {	
		   //Set left child of element to rotate to the left child's right
		   elemToRotate.left = elemLeftChild.right;
		   
		   //If not at root element, we need to point parent to new child
		   if (elem.compareTo(root.element) != 0 && parent != null) {
			   //If parent has valid left child
			   if (parent.left != null) {
				   if (parent.left.element.compareTo(elem) == 0)
					   parent.left = elemLeftChild;
			   }
			   //If parent has valid right child
			   if (parent.right != null) {
				   if (parent.right.element.compareTo(elem) == 0)
					   parent.right = elemLeftChild;
			   }
		   } 
		   //Put new node in the place
		   elemLeftChild.right = elemToRotate;
		   
		   //If root was rotated, update root
		   if (elem.compareTo(root.element) == 0)
			   root = elemLeftChild;
	   }
   }
   
   public void rotateLeft(AnyType elem) {
	 //Find node to rotate and its parent
	   Node<AnyType> elemToRotate = find(elem);
	   Node<AnyType> parent = findParent(root, elem);
	   
	   //New node in place
	   Node<AnyType> elemRightChild = null;
	   if (elemToRotate != null)
		   elemRightChild = elemToRotate.right;
	   
	   //If node to rotate and right child are not null
	   if (elemToRotate != null && elemRightChild != null) {
		   //Set right child of element to rotate to the right child's left
		   elemToRotate.right = elemRightChild.left;
		   
		   //If not at root element, we need to point parent to new child
		   if (elem.compareTo(root.element) != 0 && parent != null) {
			   //If parent has valid left child
			   if (parent.left != null) {
				   if (parent.left.element.compareTo(elem) == 0)
					   parent.left = elemRightChild;
			   }
			   //If parent has valid right child
			   if (parent.right != null) {
				   if (parent.right.element.compareTo(elem) == 0 && parent.right != null)
				   parent.right = elemRightChild;
			   }
		   } 	   
		   //Put new node in the place
		   elemRightChild.left = elemToRotate;
		   
		   //If root was rotated, update root
		   if (elem.compareTo(root.element) == 0)
			   root = elemRightChild;
	   }
   }
   
   public void printLevels() {
	   printLevels(root);
   }
   
   public void printLevels2(Node<AnyType> t) {
	   printLevels(root);
   }
   
   private void printLevels(Node<AnyType> t) {
	   //If null, no levels
	   if (t == null)
		   return;
	   
	   //Queue to store each level
	   Queue<Node> nodes = new LinkedList<Node>();
	   nodes.add(t);
	   int level = 0;
	   
	   //Go until queue empty
	   while (!nodes.isEmpty()) {
		   //Number of nodes per level
		   int nodeCount = nodes.size();
		   System.out.print("Level " + level + ": ");
		   
		   while (nodeCount > 0) {
			   //Remove node and print at level
			   Node curr = nodes.remove();
			   System.out.print(curr.element + " ");
			   
			   //Add children
			   if (curr.left != null)
				   nodes.add(curr.left);
			   if (curr.right != null)
				   nodes.add(curr.right);
			   
			   //One less this level
			   --nodeCount;
		   }
		   //Go to next level
		   System.out.println();
		   ++level;
	   }
	   System.out.println();
   }
}

class Test {
	public static void main(String[] args) throws Exception {      
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        int[] nums = {5, 3, 8, 1, 4};
        for (int i=0; i<nums.length; i++)
        	t.insert((Integer) nums[i]);
        
        BinarySearchTree<Integer> t2 = new BinarySearchTree<>();
        int[] nums2 = {10, 5, 15, 2, 7};
        for (int i=0; i<nums2.length; i++) 
        	t2.insert((Integer) nums2[i]);
        
        BinarySearchTree<Integer> t3 = new BinarySearchTree<>();
        int[] nums3 = {10, 5, 15, 2};
        for (int i=0; i<nums3.length; i++) 
        	t3.insert((Integer) nums3[i]);
        
        //nodeCount test
        System.out.println("Nodes in tree t1: " + t.nodeCount() + "\n");
        System.out.println("Nodes in tree t3: " + t3.nodeCount() + "\n" + "\n");
      
        //isFull test
        System.out.println("Is tree t1 full? " + t.isFull() + "\n");
        System.out.println("Is tree t3 full? " + t3.isFull() + "\n" + "\n");
        
        //compareStructure test
        System.out.println("Trees t1 and t2 same structure? " + t.compareStructure(t2) + "\n");
        System.out.println("Trees t1 and t3 same structure? " + t.compareStructure(t3) + "\n" + "\n");
        
        //printLevels test (of a copy)
        System.out.println("Copy of tree t1: ");
        t.printLevels2(t.copy());
        
        //equals test
        System.out.println("Trees t1 and t2 equal? " + t.equals(t2) + "\n");
        System.out.println("Trees t1 and itself equal? " + t.equals(t) + "\n" + "\n");
        
        //mirror test
        System.out.println("Mirror of tree t1: ");
        t.printLevels2(t.mirror());
        
        //isMirror test
        System.out.println("Trees t1 copy and mirror image mirror? " + t.isMirror(t) + "\n");
        System.out.println("Trees t1 and t2 mirror? " + t.isMirror(t, t2) + "\n" + "\n");
        
        //printLevels test 
        System.out.println("Tree t1 levels: ");
        t.printLevels();
        
        BinarySearchTree<Integer> t4 = new BinarySearchTree<>();
        int arr[] = {100, 50, 150, 40, 38, 45};
        
        for (int i=0; i<arr.length; i++)
        	t4.insert((Integer) arr[i]);
        t4.printLevels();
        
        //rotateRight at root
        System.out.println("Rotate 100 Right (t4): ");
        t4.rotateRight(100);
        t4.printLevels();
        
        //rotateLeft at root
        System.out.println("Rotate 50 Left (t4): ");
        t4.rotateLeft(50);
        t4.printLevels();
        
        //rotateLeft at subtree
        System.out.println("Rotate 40 Left (t4): ");
        t4.rotateLeft(40);
        t4.printLevels();
        
        //rotateRight at root
        System.out.println("Rotate 100 Right (t4): ");
        t4.rotateRight(100);
        t4.printLevels();
        
        //rotateRight at root
        System.out.println("Rotate 50 Right (t4): ");
        t4.rotateRight(50);
        t4.printLevels();
        
        //rotateRight at subtree
        System.out.println("Rotate 40 Right (t4): ");
        t4.rotateRight(40);
        t4.printLevels();
        
        //rotateLeft at subtree (do nothing b/c no right child)
        System.out.println("Rotate 40 Left (t4): ");
        t4.rotateLeft(40);
        t4.printLevels();
        
        //rotateLeft at subtree
        System.out.println("Rotate 38 Left (t4): ");
        t4.rotateLeft(38);
        t4.printLevels();
        /*
        for (int i = 2; i < NUMS; i+=2) {
             if(!t.contains(i))
                 System.out.println("Find error1!");
        }
        for (int i = 1; i < NUMS; i+=2) {
            if (t.contains(i))
                System.out.println("Find error2!");
        } */
	}
}