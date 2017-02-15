package com.bluetree.util;

public abstract class StatUtil {

    public static double sum(double[] xs) {
        int n = xs.length;
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum = sum + xs[i];
        }
        return sum;
    }

    // sum(xi) / n
    public static double mean(double[] xs) {
        int n = xs.length;
        return sum(xs) / n;
    }

    // sum((xi-mean)2) / (n-1)
    public static double var(double[] xs) {
        int n = xs.length;
        double mean = mean(xs);
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum = sum + ((xs[i] - mean) * (xs[i] - mean));
        }
        return sum / (double) (n - 1);
    }

    // sqrt(var)
    public static double stdev(double[] xs) {
        return Math.sqrt(var(xs));
    }

    // (mean-riskless) / stdev
    public static double sharp(double mean, double riskless, double stdev) {
        return (mean - riskless) / stdev;
    }

    // 
    public static double ratio(double isharp, double sharp) {
        if (isharp > 0) {
            return sharp / (Math.abs(sharp) + isharp);
        } else {
            return (sharp - 2 * isharp)
                    / (Math.abs(sharp - 2 * isharp) - isharp);
        }
    }

    // n*sum(xi*yi) - sum(xi)*sum(yi) / n*sum(xi*xi) - sum(xi)*sum(xi)
    public static double beta(double[] xs, double[] ys) {
        int n = xs.length;
        double sumxy = 0.0;
        double sumx = 0.0;
        double sumy = 0.0;
        double sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            sumxy += xs[i] * ys[i];
            sumx += xs[i];
            sumy += ys[i];
            sumx2 += xs[i] * xs[i];
        }
        double beta = (n * sumxy - sumx * sumy) / (n * sumx2 - sumx * sumx);
        return beta;
    }

    // sum(yi)*sum(xi*xi) - sum(xi)*sum(xi*yi) / n*sum(xi*xi) - sum(xi)*sum(xi)
    public static double alpha(double[] xs, double[] ys) {
        int n = xs.length;
        double sumxy = 0.0;
        double sumx = 0.0;
        double sumy = 0.0;
        double sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            sumxy += xs[i] * ys[i];
            sumx += xs[i];
            sumy += ys[i];
            sumx2 += xs[i] * xs[i];
        }
        double alpha = (sumy * sumx2 - sumx * sumxy)
                / (n * sumx2 - sumx * sumx);
        return alpha;
    }

    public static double betaBear(double[] xs, double[] ys) {
        int n = xs.length;
        int m = 0;
        double sumxy = 0.0;
        double sumx = 0.0;
        double sumy = 0.0;
        double sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            if (xs[i] < 0.0) {
                sumxy += xs[i] * ys[i];
                sumx += xs[i];
                sumy += ys[i];
                sumx2 += xs[i] * xs[i];
                m++;
            }
        }
        double beta = (m * sumxy - sumx * sumy) / (m * sumx2 - sumx * sumx);
        return beta;
    }

    public static double alphaBear(double[] xs, double[] ys) {
        int n = xs.length;
        int m = 0;
        double sumxy = 0.0;
        double sumx = 0.0;
        double sumy = 0.0;
        double sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            if (xs[i] < 0.0) {
                sumxy += xs[i] * ys[i];
                sumx += xs[i];
                sumy += ys[i];
                sumx2 += xs[i] * xs[i];
                m++;
            }
        }
        double alpha = (sumy * sumx2 - sumx * sumxy)
                / (m * sumx2 - sumx * sumx);
        return alpha;
    }

    public static double betaBull(double[] xs, double[] ys) {
        int n = xs.length;
        int m = 0;
        double sumxy = 0.0;
        double sumx = 0.0;
        double sumy = 0.0;
        double sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            if (xs[i] > 0.0) {
                sumxy += xs[i] * ys[i];
                sumx += xs[i];
                sumy += ys[i];
                sumx2 += xs[i] * xs[i];
                m++;
            }
        }
        double beta = (m * sumxy - sumx * sumy) / (m * sumx2 - sumx * sumx);
        return beta;
    }

    public static double alphaBull(double[] xs, double[] ys) {
        int n = xs.length;
        int m = 0;
        double sumxy = 0.0;
        double sumx = 0.0;
        double sumy = 0.0;
        double sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            if (xs[i] > 0.0) {
                sumxy += xs[i] * ys[i];
                sumx += xs[i];
                sumy += ys[i];
                sumx2 += xs[i] * xs[i];
                m++;
            }
        }
        double alpha = (sumy * sumx2 - sumx * sumxy)
                / (m * sumx2 - sumx * sumx);
        return alpha;
    }

}