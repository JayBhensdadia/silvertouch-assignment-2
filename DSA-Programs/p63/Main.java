class PersistentSegmentTreeNode {
    int value;
    PersistentSegmentTreeNode left, right;

    PersistentSegmentTreeNode(int value) {
        this.value = value;
    }

    PersistentSegmentTreeNode(int value, PersistentSegmentTreeNode left, PersistentSegmentTreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

class PersistentSegmentTree {
    private static final int DEFAULT_SEGMENT_SIZE = 1000000;
    private PersistentSegmentTreeNode[] versions;

    public PersistentSegmentTree(int[] array) {
        versions = new PersistentSegmentTreeNode[array.length * 4];
        build(0, array.length - 1, array, 0);
    }

    private PersistentSegmentTreeNode build(int start, int end, int[] array, int version) {
        if (start == end) {
            return new PersistentSegmentTreeNode(array[start]);
        }

        int mid = (start + end) / 2;
        PersistentSegmentTreeNode leftChild = build(start, mid, array, version);
        PersistentSegmentTreeNode rightChild = build(mid + 1, end, array, version);

        return new PersistentSegmentTreeNode(leftChild.value + rightChild.value, leftChild, rightChild);
    }

    public int query(int version, int start, int end, int queryStart, int queryEnd) {
        return query(versions[version], start, end, queryStart, queryEnd);
    }

    private int query(PersistentSegmentTreeNode node, int start, int end, int queryStart, int queryEnd) {
        if (node == null || queryEnd < start || queryStart > end) {
            return 0;
        }

        if (queryStart <= start && queryEnd >= end) {
            return node.value;
        }

        int mid = (start + end) / 2;
        int leftValue = query(node.left, start, mid, queryStart, queryEnd);
        int rightValue = query(node.right, mid + 1, end, queryStart, queryEnd);

        return leftValue + rightValue;
    }

    public int update(int version, int start, int end, int index, int newValue) {
        versions[version] = update(versions[version], start, end, index, newValue);
        return version + 1;
    }

    private PersistentSegmentTreeNode update(PersistentSegmentTreeNode node, int start, int end, int index, int newValue) {
        if (node == null) {
            node = new PersistentSegmentTreeNode(0);
        }

        if (start == end) {
            return new PersistentSegmentTreeNode(newValue);
        }

        int mid = (start + end) / 2;
        if (index <= mid) {
            PersistentSegmentTreeNode leftChild = update(node.left, start, mid, index, newValue);
            node = new PersistentSegmentTreeNode(node.value, leftChild, node.right);
        } else {
            PersistentSegmentTreeNode rightChild = update(node.right, mid + 1, end, index, newValue);
            node = new PersistentSegmentTreeNode(node.value, node.left, rightChild);
        }

        return node;
    }

    public int updateRange(int version, int start, int end, int updateStart, int updateEnd, int delta) {
        versions[version] = updateRange(versions[version], start, end, updateStart, updateEnd, delta);
        return version + 1;
    }

    private PersistentSegmentTreeNode updateRange(PersistentSegmentTreeNode node, int start, int end,
                                                 int updateStart, int updateEnd, int delta) {
        if (node == null) {
            node = new PersistentSegmentTreeNode(0);
        }

        if (start > end || updateStart > end || updateEnd < start) {
            return node;
        }

        if (updateStart <= start && updateEnd >= end) {
            return new PersistentSegmentTreeNode(node.value + delta * (end - start + 1), node.left, node.right);
        }

        int mid = (start + end) / 2;
        PersistentSegmentTreeNode leftChild = updateRange(node.left, start, mid, updateStart, updateEnd, delta);
        PersistentSegmentTreeNode rightChild = updateRange(node.right, mid + 1, end, updateStart, updateEnd, delta);

        return new PersistentSegmentTreeNode(leftChild.value + rightChild.value, leftChild, rightChild);
    }
}

public class Main {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};
        PersistentSegmentTree pst = new PersistentSegmentTree(array);

        int version1 = pst.update(0, 0, array.length - 1, 2, 10);
        int version2 = pst.updateRange(version1, 0, array.length - 1, 1, 3, 5);

        System.out.println(pst.query(0, 0, array.length - 1, 1, 3)); 
        System.out.println(pst.query(version1, 0, array.length - 1, 1, 3)); 
        System.out.println(pst.query(version2, 0, array.length - 1, 1, 3)); 
    }
}
