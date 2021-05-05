import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] result;
    private final double truthPer = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException();
        Percolation per;
        int size = n * n;
        double numberOfOpenSites;
        result = new double[trials];
        for (int i = 0; i < trials; i++) {
            per = new Percolation(n);
            while (!per.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                per.open(row, col);
            }
            numberOfOpenSites = per.numberOfOpenSites();
            result[i] = numberOfOpenSites / size;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(result);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double s = stddev();
        double sqrt = Math.sqrt(result.length);
        return (mean - (truthPer * s / sqrt));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double s = stddev();
        double sqrt = Math.sqrt(result.length);
        return mean + (truthPer * s / sqrt);
    }

    // test client (see below)
    public static void main(String[] args) {
        int no = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);

        PercolationStats perStat = new PercolationStats(no, trial);
        StdOut.println("mean                    = " + perStat.mean());
        StdOut.println("stddev                  = " + perStat.stddev());
        StdOut.println("95% confidence interval = [" + perStat.confidenceLo() + ", " + perStat
                .confidenceHi() + "]");
    }

}
