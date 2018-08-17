import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private int size;
    private double[] trialResults;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid arguments");
        }
        trials = t;
        size = n;
        trialResults = new double[trials];
        performTrials();
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println(String.format("mean                    = %f", stats.mean()));
        System.out.println(String.format("stddev                  = %f", stats.stddev()));
        System.out.println(
                String.format("95%% confidence interval = [%f, %f]", stats.confidenceLo(), stats.confidenceHi()));
    }

    public double mean() {
        return StdStats.mean(trialResults);
    }

    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    public double confidenceLo() {
        return mean() - threshold();
    }

    public double confidenceHi() {
        return mean() + threshold();
    }

    private void performTrial(int t) {
        Percolation percolation = new Percolation(size);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(size) + 1;
            int col = StdRandom.uniform(size) + 1;
            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
            }
        }
        trialResults[t] = percolation.numberOfOpenSites() / (double) (size * size);
    }

    private void performTrials() {
        for (int t = 0; t < trials; t++) {
            performTrial(t);
        }
    }

    private double threshold() {
        return (1.96 * stddev()) / Math.sqrt(trials);
    }
}