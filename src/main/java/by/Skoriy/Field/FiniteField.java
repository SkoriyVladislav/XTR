package by.Skoriy.Field;

import by.Skoriy.Polynom.IrreduciblePolynomials;
import by.Skoriy.Polynom.Polynomial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FiniteField {
    private static Map<Polynomial, Polynomial> field = new HashMap<>();
    private String fileName = "src/main/resources/FiniteField.txt";
    private int base;
    private int degree;
    private int size;

    public FiniteField(int base, int degree) {
        this.base = base;
        this.degree = degree;
        initField();
    }

    public Polynomial trace(Polynomial pl) {
        Polynomial pol = new Polynomial(0 , 0);
        pol = pol.plus(pl);

        Polynomial temp = pl;
        for (int i = 1; i <= degree - 1; i++) {
            for (int j = 0; j < i; j++) {
                temp = FiniteField.times(temp, pl, degree, base);
            }
            pol = FiniteField.plus(pol, temp, base);
        }
        return pol;
    }

    private void initField() {
        Polynomial temp = new Polynomial(1, 0);
        Polynomial first = new Polynomial(1, 1);
        Polynomial unit = new Polynomial(1, 0);

        int i = 1;

        File file = null;

        try {
            file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(file)) {

            writer.write(unit.toString() + " = " + new Polynomial(1, 0).toString() + "\n");
            //field.put(new Polynomial(1, 0), new Polynomial(1, 0));

            for ( ; ; i++) {
                temp = FiniteField.reduction(temp.times(first), degree, base);
                if (temp.equals(unit)) {
                    if (i + 1 != Math.pow(base, degree)) {
                        System.out.println("The field is not complete");
                        writer.write("The field is not complete \n");
                    }
                    break;
                }
                //System.out.println(i);
                writer.write(new Polynomial(1, i).toString() + " = " + temp.toString() + "\n");
                field.put(new Polynomial(1, i), temp);
            }

            writer.write(new Polynomial(0, i).toString() + " = " + new Polynomial(0, 0).toString());
            //field.put(new Polynomial(1, field.size()), new Polynomial(0, 0));

        } catch (IOException e) {
            e.printStackTrace();
        }

        size = i + 1;
    }

    public static Polynomial getPolynomInDegree(Polynomial polynomial,int degreePol, int degreeField, int base) {
        Polynomial temp = polynomial;
        Polynomial first = new Polynomial(1, 1);

        for ( int i = 1; i < degreePol; i++) {
            temp = FiniteField.times(temp, polynomial, degreeField, base);
            //System.out.println(i + " = " + temp);
        }

        return temp;
    }

    private static Polynomial reduction(Polynomial polynomial, int degree, int base) {
        if (polynomial.degree() >= degree) {
            Polynomial temp = IrreduciblePolynomials.getIrreduciblePolynomials(degree);
            return FiniteField.divideByBase(polynomial.remainder(temp), base);
        }

        return  polynomial;
    }

    /*public static Polynomial getPolynomByDegree(int base, int degreeField, int degreePolynom){

        Polynomial temp = new Polynomial(1, 0);
        Polynomial first = new Polynomial(1, 1);
        Polynomial unit = new Polynomial(1, 0);

        for ( int i = 1; ; i++) {
            temp = reduction(temp.times(first));
            if (temp.equals(unit)) {
                if (i + 1 != Math.pow(base, degree)) {
                    writer.write("The field is not complete \n");
                }
                break;
            }
            writer.write(new Polynomial(1, i).toString() + " = " + temp.toString() + "\n");
            field.put(new Polynomial(1, i), temp);
        }
    }*/

    public static Polynomial plus(Polynomial p1, Polynomial p2, int base) {
        Polynomial polynomial = p1.plus(p2);
        return divideByBase(polynomial, base);
    }

    public static Polynomial times(Polynomial p1, Polynomial p2, int degree, int base) {
        Polynomial polynomial = p1.times(p2);
        if (polynomial.degree() >= degree) {
            Polynomial generating = IrreduciblePolynomials.getIrreduciblePolynomials(degree);
            polynomial = polynomial.remainder(generating);
            return simplify(polynomial, base);
        }
        return  polynomial;
    }

    public static Polynomial simplify(Polynomial polynomial, int base) {
        divideByBase(polynomial, base);
        int[] coeff = polynomial.getCoef();

        for(int i = polynomial.degree(); i >= 0; i--) {
            //if (coeff == )
        }
        return polynomial;
    }

    public static Polynomial divideByBase(Polynomial polynomial, int base) {
        int[] arr = polynomial.getCoef();
        for (int i = 0; i < arr.length; i++) {
            arr[i] %= 2;
            if (arr[i] < 0) {
                arr[i] += base;
            }
        }
        polynomial.setCoef(arr);
        return polynomial;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static Map<Polynomial, Polynomial> getField() {
        return field;
    }

    @Override
    public String toString() {
        return "FiniteField{" +
                "fileName='" + fileName + '\'' +
                ", base=" + base +
                ", degree=" + degree +
                '}';
    }
}
