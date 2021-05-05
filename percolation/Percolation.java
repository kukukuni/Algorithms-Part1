import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] state;
    private WeightedQuickUnionUF uf = null;
    private WeightedQuickUnionUF uf2 = null;
    private int n = 0;
    private int numberOfOpenSites = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        int size = n * n + 2;
        uf = new WeightedQuickUnionUF(size);
        uf2 = new WeightedQuickUnionUF(size - 1);
        state = new boolean[size];

        for (int i = 1; i < size; i++)
            state[i] = false;
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();

        int location1D = (row - 1) * n + col;

        if (isOpen(row, col)) {
            return;
        }
        else {
            state[location1D] = true;
            numberOfOpenSites += 1;

            if (row == 1) {
                uf.union(0, location1D);
                uf2.union(0, location1D);
                if (n != 1 && isOpen(row + 1, col)) {
                    uf.union(col + row * n, location1D);
                    uf2.union(col + row * n, location1D);
                }
                else if (n == 1) {
                    uf.union(n * n + 1, location1D);
                }
            }
            else if (row == n) {
                if (isOpen(row - 1, col)) {
                    uf.union(col + (row - 2) * n, location1D);
                    uf2.union(col + (row - 2) * n, location1D);
                }
                uf.union(n * n + 1, location1D);
            }
            else {
                if (isOpen(row - 1, col)) {
                    uf.union(col + (row - 2) * n, location1D);
                    uf2.union(col + (row - 2) * n, location1D);
                }
                if (isOpen(row + 1, col)) {
                    uf.union(col + row * n, location1D);
                    uf2.union(col + row * n, location1D);
                }
            }

            if (col != 1 && isOpen(row, col - 1)) {
                uf.union(location1D - 1, location1D);
                uf2.union(location1D - 1, location1D);
            }
            if (col != n && isOpen(row, col + 1)) {
                uf.union(location1D + 1, location1D);
                uf2.union(location1D + 1, location1D);
            }
        }

    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();
        int location1D = (row - 1) * n + col;
        return state[location1D];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();
        int location1D = (row - 1) * n + col;
        return uf2.find(0) == uf2.find(location1D) ? true : false;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        boolean isPercolatie = uf.find(0) == uf.find(n * n + 1) ? true : false;
        return isPercolatie;
    }

    public static void main(String[] args) {
    }

}
