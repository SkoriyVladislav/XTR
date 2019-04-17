package by.Skoriy;

import by.Skoriy.Field.FiniteField;
import by.Skoriy.Polynom.Polynomial;

public class Main {
    public static int N = 31;
    public static int M = 5;
    //public static int POWER_ALPHA = 42799; // (2^21 - 1)/49 = 42799   (2^14-1)/43 = 381
    public static int BASE = 2;

    public static Polynomial betta = new Polynomial(1, 16).plus(
            new Polynomial(1, 13).plus(
                    new Polynomial(1, 12)).plus(
                    new Polynomial(1, 11)).plus(
                    new Polynomial(1, 9)).plus(
                    new Polynomial(1, 8)).plus(
                    new Polynomial(1, 7)).plus(
                    new Polynomial(1, 5)).plus(
                    new Polynomial(1, 4)).plus(
                    new Polynomial(1, 3)).plus(
                    new Polynomial(1, 1))); // 1x^16 + 1x^13 + 1x^12 + 1x^11 + 1x^9 + 1x^8 + 1x^7 + 1x^5 + 1x^4 + 1x^3 + 1x

    public static void main(String[] args) {
        int[][] H1 = getH(Main.getBetta(1, M, 2), 1);
        int[][] H3 = getH(Main.getBetta(1, M, 2), 3);
        int[][] H5 = getH(Main.getBetta(1, M, 2), 5);

        System.out.println("H1 = ");
        printH(H1, M, N);
        System.out.println();
        DistanceUtil.getDistance(H1, M, N);
        System.out.println();

        System.out.println("H3 = ");
        printH(H3, M, N);
        System.out.println();
        DistanceUtil.getDistance(H3, M, N);
        System.out.println();

        System.out.println("H5 = ");
        printH(H5, M, N);
        System.out.println();
        DistanceUtil.getDistance(H5, M, N);
        System.out.println();


        int[][] H1H3 = new int[2 * M][N];
        for (int i = 0; i < M; i++) {
            H1H3[i] = H1[i];
            H1H3[i + M] = H3[i];
        }
        System.out.println("H1H3 = ");
        printH(H1H3, 2 * M, N);
        System.out.println();
        DistanceUtil.getDistance(H1H3, 2 * M, N);
        System.out.println();

        int[][] H1H3H5 = new int[3*M][N];
        for (int i = 0; i < M; i++) {
            H1H3H5[i] = H1[i];
            H1H3H5[i + M] = H3[i];
            H1H3H5[i + 2*M] = H5[i];
        }
        System.out.println("H1H3H5 = ");
        printH(H1H3H5, 3 * M, N);
        System.out.println();
        DistanceUtil.getDistance(H1H3H5, 3 * M, N);
        System.out.println();
    }


    private static int[][] getH(Polynomial betta, int order) {
        int[][] H = new int[M][N];
        H[0][0] = 1;

        for (int j = 1; j < N; j++) {
            Polynomial tempBetta = FiniteField.getPolynomInDegree(betta, j * order, M, BASE);
            int[] bettaCoef = tempBetta.getCoef();
            for (int i = 0; i < M; i++) {
                try {
                    H[i][j] = bettaCoef[i];
                } catch (ArrayIndexOutOfBoundsException e) {
                    H[i][j] = 0;
                }
            }
        }

        return H;
    }

    private static void printH(int[][] H1, int countRow, int countColumn) {
        for (int i = 0; i < countRow; i++) {
            int sum = 0;
            for (int j = 0; j < countColumn; j++) {
                sum += H1[i][j];
                System.out.print(H1[i][j]);
            }
            System.out.print("  = " + sum);
            System.out.println();
        }
    }

    private static Polynomial getBetta(int powerAlpha, int m, int base) {
        /*public static Polynomial betta = new Polynomial(1, 16).plus(
                new Polynomial(1, 13).plus(
                        new Polynomial(1, 12)).plus(
                        new Polynomial(1, 11)).plus(
                        new Polynomial(1, 9)).plus(
                        new Polynomial(1, 8)).plus(
                        new Polynomial(1, 7)).plus(
                        new Polynomial(1, 5)).plus(
                        new Polynomial(1, 4)).plus(
                        new Polynomial(1, 3)).plus(
                        new Polynomial(1, 1))); // 1x^16 + 1x^13 + 1x^12 + 1x^11 + 1x^9 + 1x^8 + 1x^7 + 1x^5 + 1x^4 + 1x^3 + 1x*/
        return FiniteField.getPolynomInDegree(new Polynomial(1, 1), powerAlpha, m, base);
    }

    /*
        Polynomial zero = new Polynomial( 0, 0 );

        Polynomial p1 = new Polynomial( 1, 3 );
        Polynomial p2 = new Polynomial( 2, 2 );
        Polynomial p3 = new Polynomial( 4, 0 );*/
    //Polynomial p = p1.plus( p2 ).plus( p3 );   // 1x^3 + 2x^2 + 4

    //FiniteField finiteField = new FiniteField(2,21, "src/main/resources/FiniteField.txt");

        /* Polynomial p = new Polynomial(1,6).plus(
                new Polynomial(1, 5)).plus(
                new Polynomial(1, 4)).plus(
                new Polynomial(1, 3)).plus(
                new Polynomial(1, 2)).plus(
                new Polynomial(1, 1)).plus(
                new Polynomial(1, 0)); // 1x^6 + 1x^5 + 1x^4 + 1x^2 + 1x + 1 */

        /*Polynomial q1 = new Polynomial( 3, 2 );
        Polynomial q2 = new Polynomial( 5, 0 );
        Polynomial q = q1.plus( q2 );                     // 3x^2 + 5*/


        /*Polynomial r = p.plus( q );
        Polynomial s = p.times( q );
        Polynomial t = p.compose( q );

        System.out.println( "zero(x)     = " + zero );
        System.out.println( "p(x)        = " + p );
        System.out.println( "q(x)        = " + q );
        System.out.println( "p(x) + q(x) = " + r );
        System.out.println( "p(x) * q(x) = " + s );
        System.out.println( "p(q(x))     = " + t );
        System.out.println( "0 - p(x)    = " + zero.minus( p ) );
        System.out.println( "p(3)        = " + p.evaluate( 3 ) );
        System.out.println( "p'(x)       = " + p.differentiate() );
        System.out.println( "p''(x)      = " + p.differentiate().differentiate() );

        p.divides( q );*/

        /*Polynomial p2 = FiniteField.reductionMult(p.times(p), 7, 2);
        Polynomial p4 = FiniteField.reductionMult(p2.times(p2), 7, 2);
        Polynomial p8 = FiniteField.reductionMult(p4.times(p4), 7, 2);
        Polynomial p16 = FiniteField.reductionMult(p8.times(p8), 7, 2);
        Polynomial p32 = FiniteField.reductionMult(p16.times(p16), 7, 2);
        Polynomial p64 = FiniteField.reductionMult(p32.times(p32), 7, 2);

        Polynomial pol = FiniteField.reductionAdd(p.plus(p2).plus(p4).plus(p8).plus(p16).plus(p32).plus(p64), 2);
        System.out.println("След элемента " + pol);

        int k = 8;

        Polynomial q = p4;
        Polynomial q2 = FiniteField.reductionMult(q.times(q), 7, 2);
        Polynomial q4 = FiniteField.reductionMult(q2.times(q2), 7, 2);
        Polynomial q8 = FiniteField.reductionMult(q4.times(q4), 7, 2);
        Polynomial q16 = FiniteField.reductionMult(q8.times(q8), 7, 2);
        Polynomial q32 = FiniteField.reductionMult(q16.times(q16), 7, 2);
        Polynomial q64 = FiniteField.reductionMult(q32.times(q32), 7, 2);
        Polynomial qol = FiniteField.reductionAdd(q.plus(q2).plus(q4).plus(q8).plus(q16).plus(q32).plus(q64), 2);
        System.out.println("След элемента в 8ст " + qol);

        Polynomial m = new Polynomial(1,3).plus(
                        new Polynomial(1, 1)); // 1x^3 + 1x
        int b = 3;
        Polynomial b1 = FiniteField.reductionMult(p2.times(p), 7, 2);
        Polynomial b2 = FiniteField.reductionMult(b1.times(b1), 7, 2);
        Polynomial b4 = FiniteField.reductionMult(b2.times(b2), 7, 2);
        Polynomial b8 = FiniteField.reductionMult(b4.times(b4), 7, 2);
        Polynomial b16 = FiniteField.reductionMult(b8.times(b8), 7, 2);
        Polynomial b32 = FiniteField.reductionMult(b16.times(b16), 7, 2);
        Polynomial b64 = FiniteField.reductionMult(b32.times(b32), 7, 2);
        Polynomial bol = FiniteField.reductionAdd(b1.plus(b2).plus(b4).plus(b8).plus(b16).plus(b32).plus(b64), 2);
        System.out.println("След элемента в 3ст " + bol);

        int key = b*k;
        Polynomial k1 = FiniteField.reductionMult(p8.times(p4), 7, 2);
        Polynomial k2 = FiniteField.reductionMult(k1.times(k1), 7, 2);
        Polynomial k4 = FiniteField.reductionMult(k2.times(k2), 7, 2);
        Polynomial k8 = FiniteField.reductionMult(k4.times(k4), 7, 2);
        Polynomial k16 = FiniteField.reductionMult(k8.times(k8), 7, 2);
        Polynomial k32 = FiniteField.reductionMult(k16.times(k16), 7, 2);
        Polynomial k64 = FiniteField.reductionMult(k32.times(k32), 7, 2);
        Polynomial kol = FiniteField.reductionAdd(k1.plus(k2).plus(k4).plus(k8).plus(k16).plus(k32).plus(k64), 2);
        System.out.println("След элемента в 24ст " + kol);

        Polynomial e1 = FiniteField.reductionMult(kol.times(bol), 7, 2);
        Polynomial e = FiniteField.reductionMult(kol.times(m),7,2);
        System.out.println("зафишр сообщ " + e);


        Polynomial obr = new Polynomial(1,5).plus(
                new Polynomial(1, 4)).plus(
                new Polynomial(1, 2)).plus(
                new Polynomial(1, 1)).plus(
                new Polynomial(1, 0));  // 1x^6 + 1x^5 + 1x^4 + 1x^3 + 1x

        *//*for (Map.Entry<Polynomial, Polynomial> entry : finiteField.getField().entrySet()) {
            Polynomial temp = FiniteField.reductionMult(entry.getValue().times(kol),7,2);
            //System.out.println(temp);
            if (ed.eq(temp)) {
                obatn = entry.getValue();
            }
        }*//*

        System.out.println(obr);
        System.out.println("расшифр сообщ " + FiniteField.reductionMult(obr.times(e),7,2));*/
}
