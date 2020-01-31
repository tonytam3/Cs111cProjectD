import java.util.*;

public class BinarySearchTreeWithDups<T extends Comparable<? super T>> extends BinarySearchTree<T>
		implements SearchTreeInterface<T>, java.io.Serializable {

	public BinarySearchTreeWithDups() {
		super();
	}

	public BinarySearchTreeWithDups(T rootEntry) {
		super(rootEntry);
		setRootNode(new BinaryNode<T>(rootEntry));
	}

	@Override
	public T add(T newEntry) {
		T result = newEntry;
		if (isEmpty()) {
			setRootNode(new BinaryNode<T>(newEntry));
		} else {
			addEntryHelperNonRecursive(newEntry);
		}
		return result;
	}

	// YOUR CODE HERE! THIS METHOD CANNOT BE RECURSIVE.
	private void addEntryHelperNonRecursive(T newEntry) {
		/*
		 * Setting up the necessary variables
		 */
		BinaryNode<T> currentNode = getRootNode();
		BinaryNode<T> previousNode = getRootNode();
		BinaryNode<T> newNode = new BinaryNode<T>(newEntry);

		/*
		 * Searching for the end of the binary search tree.
		 * 
		 * Current will first be tested to see if it's null. Then previousNode is going
		 * to keep track of currentNode's position. Next step was to see where the
		 * currentNode will head next left or right using the compareTo method from
		 * Comparable. If the currentNode.data is greater than or equal to the incoming
		 * newEntry (some positive Integer or zero respectively), then the currentNode
		 * should look at the left child and right child otherwise (some negative
		 * Integer).
		 */

		while (currentNode != null) {

			previousNode = currentNode;

			if (currentNode.getData().compareTo(newEntry) >= 0) {

				currentNode = currentNode.getLeftChild();

			} else {

				currentNode = currentNode.getRightChild();
			}

		}

		/*
		 * The while loop exits after currentNode is set to NULL. After this the
		 * currentNode is set back to the previousNode.
		 * 
		 * Finally it's the matter of comparing the currentData and the newEntry to see
		 * whether to add to the left or right.
		 * 
		 * Side note: reversing the compareTo variables might have been easier to
		 * understand in hindsight.
		 */

		currentNode = previousNode;

		if (currentNode.getData().compareTo(newEntry) >= 0) {
			currentNode.setLeftChild(newNode);
		} else {
			currentNode.setRightChild(newNode);
		}

	}

	// YOUR CODE HERE! THIS METHOD CANNOT BE RECURSIVE.
	// MAKE SURE TO TAKE ADVANTAGE OF THE SORTED NATURE OF THE BST!

	public int countEntriesNonRecursive(T target) {
		int count = 0;
		BinaryNode<T> currentNode = getRootNode();
		while (currentNode != null) {
			T currentData = currentNode.getData();
			if (target.equals(currentData)) {
				count += 1;
				currentNode = currentNode.getLeftChild();
			} else if (target.compareTo(currentData) < 0) {
				currentNode = currentNode.getLeftChild();
			} else {
				currentNode = currentNode.getRightChild();
			}
		}
		return count;
	}

	// YOUR CODE HERE! MUST BE RECURSIVE! YOU ARE ALLOWED TO CREATE A PRIVATE
	// HELPER.
	// MAKE SURE TO TAKE ADVANTAGE OF THE SORTED NATURE OF THE BST!

//	public int countGreaterRecursive(T target) {
//		/*
//		 * Setting variables
//		 */
//		int count = 0;
//		BinaryNode<T> rootNode = getRootNode();
//	
//		/*
//		 * Start the recursive call with the rootNode and the target.
//		 */
//	
//		count = countGreaterRecursiveHelper(rootNode, target);
//	
//		return count;
//	
//	}
//
//	
//	public int countGreaterRecursiveHelper(BinaryNode<T> currentNode, T target) {
//	
//		/*
//		 * each recursive call window will start with a count of zero
//		 */
//	
//		int count = 0;
//		
//		/*
//		 * currentNode.data is tested against the target.
//		 * 
//		 * If the currentNode.data is less than the target (negative number),
//		 * then start a recursive call on the right child node and keep calling
//		 * until currentNode.data is greater than the target.
//		 * 
//		 * Any recursive call that has a node.data less than the target will add
//		 * 0 to the count.
//		 * 
//		 */
//	
//		if (currentNode.getData().compareTo(target) <= 0) {
//	
//			if (currentNode.hasRightChild()) {
//				count += countGreaterRecursiveHelper(currentNode.getRightChild(), target);
//			}
//			
//			/*
//			 * Once the currentNode.data reaches the node where all of it children are
//			 * greater than the target, then every recursive call for the left and right
//			 * child would add 1 to the count
//			 */
//	
//		} else {
//			
//			if(currentNode.hasLeftChild()) {
//				count += countGreaterRecursiveHelper(currentNode.getLeftChild(), target);
//			}
//			
//			if (currentNode.hasRightChild()) {
//				count += countGreaterRecursiveHelper(currentNode.getRightChild(), target);
//			}
//				
//			count++;
//			
//		}
//		
//		return count;
//	
//	}

	public int countGreaterRecursive(T target) {

		int count = 0;
		BinaryNode<T> rootNode = getRootNode();

		count = countGreaterRecursiveHelper(rootNode, target);
		return count;
	}

	private int countGreaterRecursiveHelper(BinaryNode<T> rootNode, T target) {
		int count = 0;
		if (rootNode != null) {
			if (rootNode.getData().compareTo(target) > 0) {
				count = count + 1;
				count = count + countGreaterRecursiveHelper(rootNode.getLeftChild(), target); // if node data smaller
																								// than target do not
																								// visit left child

			}
			count = count + countGreaterRecursiveHelper(rootNode.getRightChild(), target);
		}
		return count;
	}

	// YOUR CODE HERE! MUST USE A STACK!! MUST NOT BE RECURSIVE!
	// MAKE SURE TO TAKE ADVANTAGE OF THE SORTED NATURE OF THE BST!
	public int countGreaterWithStack(T target) {

		/*
		 * Setting up the necessary variables: int counter, rootNode, currentNode as the
		 * cursor, and stack
		 */

		int count = 0;
		BinaryNode<T> rootNode = getRootNode();
		BinaryNode<T> currentNode;
		Stack<BinaryNode<T>> nodeStack = new Stack<BinaryNode<T>>();

		/*
		 * The rootNode is pushed onto the stack
		 */

		nodeStack.push(rootNode);

		/*
		 * In this while loop the top of stack is popped off. If the currentNode.data is
		 * less than or equal to target, add the right child to the stack. This way to
		 * avoid any unnecessary nodes to the stack.
		 *
		 */

		while (!nodeStack.isEmpty()) {
			currentNode = nodeStack.pop();

			if (currentNode.getData().compareTo(target) <= 0) {

				if (currentNode.getRightChild() != null) {
					nodeStack.push(currentNode.getRightChild());
				}

			} else {

				if (currentNode.getLeftChild() != null) {
					nodeStack.push(currentNode.getLeftChild());
				}

				if (currentNode.getRightChild() != null) {
					nodeStack.push(currentNode.getRightChild());
				}

				count++;

			}

		}

		return count;
	}

	// YOUR EXTRA CREDIT CODE HERE! THIS METHOD MUST BE O(n).
	// YOU ARE ALLOWED TO USE A HELPER METHOD. THE METHOD CAN BE ITERATIVE OR
	// RECURSIVE.
	public int countUniqueValues() {
		BinaryNode<T> rootNode = getRootNode();
		T min = rootNode.getData();
		int count = 0;
		Stack<BinaryNode<T>> stack = new Stack<BinaryNode<T>>();
		Stack<BinaryNode<T>> finishedStack = new Stack<BinaryNode<T>>();
		BinaryNode<T> currentNode = new BinaryNode<T>();

		stack.push(rootNode);

		while (!stack.isEmpty()) {
			currentNode = stack.pop();

			if (!finishedStack.isEmpty() && currentNode.hasLeftChild()) {

				if (finishedStack.peek() == currentNode.getLeftChild()) {
					finishedStack.push(currentNode);
				}

			} else {

				if (currentNode.hasRightChild()) {
					stack.push(currentNode.getRightChild());
				}

				if (!currentNode.hasRightChild() && !currentNode.hasLeftChild()) {
					finishedStack.push(currentNode);
				}

				if (currentNode.hasLeftChild() || currentNode.hasRightChild()) {
					stack.push(currentNode);
				}
				if (currentNode.hasLeftChild()) {
					stack.push(currentNode.getLeftChild());
				}
			}

		}

		return count;
	}

	public int getLeftHeight() {
		BinaryNode<T> rootNode = getRootNode();
		if (rootNode == null) {
			return 0;
		} else if (!rootNode.hasLeftChild()) {
			return 0;
		} else {
			return rootNode.getLeftChild().getHeight();
		}
	}

	public int getRightHeight() {
		BinaryNode<T> rootNode = getRootNode();
		if (rootNode == null) {
			return 0;
		} else if (!rootNode.hasRightChild()) {
			return 0;
		} else {
			return rootNode.getRightChild().getHeight();
		}
	}

}
