package by.Skoriy.Polynom;

import java.util.HashMap;
import java.util.Map;

public class IrreduciblePolynomials {
    private static Map<Polynomial, Polynomial> irreduciblePolynomials = new HashMap<>();
    static {
        irreduciblePolynomials.put( new Polynomial(1, 1), new Polynomial(1,0)); // x + 1
        irreduciblePolynomials.put( new Polynomial(1, 2), new Polynomial(1,1).plus(new Polynomial(1, 0))); // x^2 + x + 1
        irreduciblePolynomials.put( new Polynomial(1, 3), new Polynomial(1,2).plus(new Polynomial(1, 0))); // x^3 + x^2 + 1
        irreduciblePolynomials.put( new Polynomial(1, 4), new Polynomial(1,1).plus(new Polynomial(1, 0))); // x^4 + x + 1

        irreduciblePolynomials.put( new Polynomial(1, 7), new Polynomial(1,6).plus(
                new Polynomial(1, 5)).plus(
                    new Polynomial(1, 4)).plus(
                        new Polynomial(1, 3)).plus(
                            new Polynomial(1, 2)).plus(
                                new Polynomial(1, 0))); // x^7 + x^6 + x^5 + x^4 + x^3 + x^2 + 1

        irreduciblePolynomials.put(new Polynomial(1, 14), new Polynomial(1,10).plus(
                new Polynomial(1, 6)).plus(
                    new Polynomial(1, 1)).plus(
                        new Polynomial(1, 0))); // x^14+x^10+x^6+x+1

        irreduciblePolynomials.put(new Polynomial(1, 23), new Polynomial(1,5).plus(
                new Polynomial(1, 0))); //x^23+x^5++1

        irreduciblePolynomials.put(new Polynomial(1, 26), new Polynomial(1,6).plus(
                new Polynomial(1, 2)).plus(
                    new Polynomial(1, 1)).plus(
                        new Polynomial(1, 0))); //x^26+x^6+x^2+x+1

        irreduciblePolynomials.put( new Polynomial(1, 35), new Polynomial(1,34).plus(
                new Polynomial(1, 31)).plus(
                        new Polynomial(1, 29)).plus(
                                new Polynomial(1, 26)).plus(
                                        new Polynomial(1, 25)).plus(
                                            new Polynomial(1, 24)).plus(
                                                new Polynomial(1, 22)).plus(
                                                    new Polynomial(1, 21)).plus(
                                                        new Polynomial(1, 13)).plus(
                                                            new Polynomial(1, 10)).plus(
                                                                new Polynomial(1, 7)).plus(
                                                                    new Polynomial(1, 6)).plus(
                                                                        new Polynomial(1, 4)).plus(
                                                                            new Polynomial(1, 2)).plus(
                                                                                new Polynomial(1, 1)).plus(
                                                                                    new Polynomial(1, 0))); // x^35 + x^34 + x^31 + x^29 + x^26 + x^25 + x^24 + x^22 + x^21 + x^13 + x^10 + x^7 + x^6 + x^4 + x^2 + x + 1

        irreduciblePolynomials.put(new Polynomial(1, 36), new Polynomial(1,11).plus(new Polynomial(1, 0))); //x^36+x^11+1


        irreduciblePolynomials.put(new Polynomial(1, 21), new Polynomial(1,2).plus(new Polynomial(1, 0))); //x^21+x^2+1

        irreduciblePolynomials.put(new Polynomial(1, 5), new Polynomial(1,2).plus(new Polynomial(1, 0))); //x^5+x^2+1

        irreduciblePolynomials.put(new Polynomial(1, 6), new Polynomial(1,1).plus(new Polynomial(1, 0))); //x^5+x+1
    }

    public static Polynomial getIrreduciblePolynomials(int degree) {
        return new Polynomial(1, degree).plus(irreduciblePolynomials.get(new Polynomial(1, degree)));
    }
}
