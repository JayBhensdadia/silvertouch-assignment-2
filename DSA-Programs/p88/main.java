package p88;

import java.util.Arrays;

class VanEmdeBoasTree {
    private int universeSize;
    private int min, max;
    private VanEmdeBoasTree summary;
    private VanEmdeBoasTree[] cluster;

    public VanEmdeBoasTree(int universeSize) {
        this.universeSize = universeSize;

        if (universeSize == 2) {
            min = -1;
            max = -1;
        } else if (universeSize > 2) {
            int upperClusterSize = (int) Math.ceil(Math.sqrt(universeSize));
            int lowerClusterSize = (int) Math.floor(Math.sqrt(universeSize));

            summary = new VanEmdeBoasTree(upperClusterSize);
            cluster = new VanEmdeBoasTree[upperClusterSize];

            for (int i = 0; i < upperClusterSize; i++) {
                cluster[i] = new VanEmdeBoasTree(lowerClusterSize);
            }
        }
    }

    private int high(int x) {
        return x / (int) Math.floor(Math.sqrt(universeSize));
    }

    private int low(int x) {
        return x % (int) Math.floor(Math.sqrt(universeSize));
    }

    private int index(int x, int y) {
        return x * (int) Math.floor(Math.sqrt(universeSize)) + y;
    }

    public boolean contains(int x) {
        if (universeSize == 2) {
            return x == min || x == max;
        } else {
            return cluster[high(x)].contains(low(x));
        }
    }

    public int successor(int x) {
        if (universeSize == 2) {
            if (x == 0 && max == 1) return 1;
            else return -1;
        } else if (min != -1 && x < min) {
            return min;
        } else {
            int maxLow = cluster[high(x)].max;
            if (maxLow != -1 && low(x) < maxLow) {
                int offset = cluster[high(x)].successor(low(x));
                return index(high(x), offset);
            } else {
                int successorCluster = summary.successor(high(x));
                if (successorCluster == -1) {
                    return -1;
                } else {
                    int offset = cluster[successorCluster].min;
                    return index(successorCluster, offset);
                }
            }
        }
    }

    public int minimum() {
        return min;
    }

    public int maximum() {
        return max;
    }

    public void insert(int x) {
        if (universeSize == 2) {
            if (min == -1) {
                min = max = x;
            } else {
                min = Math.min(min, x);
                max = Math.max(max, x);
            }
        } else {
            if (min == -1) {
                min = max = x;
            } else {
                min = Math.min(min, x);
                max = Math.max(max, x);
            }

            int highX = high(x);
            int lowX = low(x);

            if (cluster[highX].min == -1) {
                summary.insert(highX);
                cluster[highX].min = cluster[highX].max = lowX;
            } else {
                cluster[highX].insert(lowX);
            }
        }
    }

    public void delete(int x) {
        if (universeSize == 2) {
            if (x == min && x == max) {
                min = max = -1;
            }
        } else {
            if (x == min) {
                int firstCluster = summary.minimum();
                x = index(firstCluster, cluster[firstCluster].minimum());
                min = x;
            }

            cluster[high(x)].delete(low(x));

            if (cluster[high(x)].min == -1) {
                summary.delete(high(x));

                if (x == max) {
                    int summaryMax = summary.maximum();
                    if (summaryMax == -1) {
                        max = min;
                    } else {
                        max = index(summaryMax, cluster[summaryMax].maximum());
                    }
                }
            } else if (x == max) {
                max = index(high(x), cluster[high(x)].maximum());
            }
        }
    }

    public static void main(String[] args) {
        VanEmdeBoasTree vebTree = new VanEmdeBoasTree(16);

        vebTree.insert(2);
        vebTree.insert(7);
        vebTree.insert(4);
        vebTree.insert(14);

        System.out.println(vebTree.contains(7)); // true
        System.out.println(vebTree.successor(4)); // 7

        vebTree.delete(7);

        System.out.println(vebTree.contains(7)); // false
        System.out.println(vebTree.successor(4)); // 14
    }
}

