import java.util.Arrays;

class Node {
int[] point;
Node left, right;

Node(int[] point) {
	this.point = point;
	left = right = null;
}
}

class Main {
int k = 2;

Node newNode(int[] point) {
	return new Node(point);
}

Node insertRec(Node root, int[] point, int depth) {
	if (root == null) {
	return newNode(point);
	}

	int cd = depth % k;
	if (point[cd] < root.point[cd]) {
	root.left = insertRec(root.left, point, depth + 1);
	} else {
	root.right = insertRec(root.right, point, depth + 1);
	}

	return root;
}

Node insert(Node root, int[] point) {
	return insertRec(root, point, 0);
}

int min3(int x, int y, int z) {
	return Math.min(Math.min(x, y), z);
}

int findMinRec(Node root, int d, int depth) {
	if (root == null) {
	return Integer.MAX_VALUE;
	}

	int cd = depth % k;
	if (cd == d) {
	if (root.left == null) {
		return root.point[d];
	}
	return min3(root.point[d], findMinRec(root.left, d, depth + 1), findMinRec(root.right, d, depth + 1));
	}

	return min3(root.point[d], findMinRec(root.left, d, depth + 1), findMinRec(root.right, d, depth + 1));
}

int findMin(Node root, int d) {
	return findMinRec(root, d, 0);
}

public static void main(String[] args) {
	KDTree tree = new KDTree();
	Node root = null;
	int[][] points = {{30, 40}, {5, 25}, {70, 70}, {10, 12}, {50, 30}, {35, 45}};

	for (int[] point : points) {
	root = tree.insert(root, point);
	}

	System.out.println("Minimum of 0'th dimension is " + tree.findMin(root, 0));
	System.out.println("Minimum of 1'th dimension is " + tree.findMin(root, 1));
}
}

