import java.util.Arrays;

public class LinearBox {
    public static double determinant(double[][] mat) {
        int len = mat.length;
        if(len == 1) {
            return mat[0][0];
        }
        double sum = 0;
        int coef = 1;
        double[][] sub = new double[len-1][len-1];
        for(int i = 0; i < len; i++) {
            //remove columns and rows belonging to mat[0][i] (so, remove column i and row 0)
            for(int j = 0; j < len-1; j++) {
                System.arraycopy(mat[j+1], 0, sub[j], 0, i);
                System.arraycopy(mat[j+1], i+1, sub[j], i, (len-i - 1));
            }
            sum += coef * mat[0][i] * determinant(sub);
            coef*=-1;
        }
        return sum;
    }

    private static void printMatrix(double[][] mat) {
        System.out.println(Arrays.deepToString(mat).replace("], ", "]\n"));
    }

    private static double[][] fromList(double... vals) {
        int len = (int) Math.round(Math.sqrt(vals.length));
        double[][] mat = new double[len][len];
        for(int i = 0; i < vals.length; i++) {
            mat[i / len][i % len] = vals[i];
        }
        return mat;
    }

    public static double magnitude(double[] vec) {
        double sum = 0;
        for(double d : vec) {
            sum+=(d*d);
        }
        return Math.sqrt(sum);
    }

    public static double dot(double[] vec1, double[] vec2) {
        double sum = 0;
        for(int i = 0; i < vec1.length; i++) {
            sum+=vec1[i]*vec2[i];
        }
        return sum;
    }

    public static double[] vectorAdd(double[] vec1, double[] vec2) {
        double[] out = new double[vec1.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = vec1[i] + vec2[i];
        }
        return out;
    }

    //Returns vec1-vec2;
    public static double[] vectorSubtract(double[] vec1, double[] vec2) {
        return vectorAdd(vec1, vectorScale(vec2, -1));
    }

    public static double[] vectorScale(double[] vec, double scalar) {
        double[] out = new double[vec.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = scalar * vec[i];
        }
        return out;
    }

    public static double distanceToPlane(double[] point, double[] normal, double[] pointOnPlane) {
        double[] f = vectorSubtract(point, pointOnPlane);
        return dot(normal, f) / magnitude(normal);
    }

    public static double[][] matrixMultiply(double[][] mat, double[] vec) {
        //multiplying a matrix by a column vector is just a specific case of general matrix multiplication.
        return matrixMultiply(mat, columnToMatrix(vec));
    }

    public static double[][] matrixMultiply(double[][] mat1, double[][] mat2) {
        final int n = mat1[0].length;
        if(n != mat2.length) {
            return null; //can't dot product if vecs of different dimension
        }
        final int h = mat1.length;
        final int w = mat2[0].length;
        int sum;
        double[][] product = new double[h][w];
        for(int i = 0; i < product.length; i++) {
            for(int j = 0; j < product[0].length; j++) {
                sum = 0;
                for(int k = 0; k < n; k++) {
                    sum+=mat1[i][k]*mat2[k][j];
                }
                product[i][j]=sum;
            }
        }
        return product;
    }

    public static double[][] columnToMatrix(double[] colVec) {
        double[][] mat = new double[colVec.length][1];
        for(int i = 0; i < mat.length; i++) {
            mat[i][0] = colVec[i];
        }
        return mat;
    }

    public static void main(String[] args) {
        System.out.println(determinant(fromList(
                1,   4,   7,   -2,
                0,   3,   2,   1,
                -4,  4,   0,   5,
                3,  -1,   1,   2
                )));
        System.out.println(distanceToPlane(new double[] {0, 0, 0}, new double[] {1, 1, 0}, new double[] {0, 1, 0}));
        printMatrix(matrixMultiply(
                fromList(6, 2, 4,
                        -1, 4, 3,
                        -2, 9, 3
                        ),
                new double[] {4, -2, 1}
        ));
    }
}