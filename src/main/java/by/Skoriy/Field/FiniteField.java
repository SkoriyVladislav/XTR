package by.Skoriy.Field;

import by.Skoriy.Polynom.IrreduciblePolynomials;
import by.Skoriy.Polynom.Polynomial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.SortedMap;

public class FiniteField {
    private HashMap<Polynomial, Polynomial> field = new HashMap<>();
    private String fileName;
    private int base;
    private int degree;
    private int size;

    public FiniteField(int base, int degree, String fileName) {
        this.base = base;
        this.degree = degree;
        this.fileName = fileName;
        initField();
    }

    public Polynomial tr(Polynomial pl) {
        Polynomial pol = new Polynomial(0 , 0);
        pol = pol.plus(pl);

        Polynomial temp = pl;
        for (int i = 1; i <= degree - 1; i++) {
            for (int j = 0; j < i; j++) {
                temp = reductionMult(temp.times(temp));
            }
            pol = reductionAdd(pol.plus(temp));
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
            field.put(new Polynomial(1, 0), new Polynomial(1, 0));

            for ( ; ; i++) {
                temp = reduction(temp.times(first));
                if (temp.equals(unit)) {
                    if (i + 1 != Math.pow(base, degree))
                    writer.write("The field is not complete \n");
                    break;
                }
                writer.write(new Polynomial(1, i).toString() + " = " + temp.toString() + "\n");
                field.put(new Polynomial(1, i), temp);
            }

            writer.write(new Polynomial(0, i).toString() + " = " + new Polynomial(0, 0).toString());
            field.put(new Polynomial(1, field.size()), new Polynomial(0, 0));

        } catch (IOException e) {
            e.printStackTrace();
        }

        size = i + 1;
    }

    private Polynomial reduction(Polynomial polynomial) {

        if (polynomial.degree() >= degree) {

            Polynomial temp = IrreduciblePolynomials.getIrreduciblePolynomials(degree);

            return polynomial.minus(new Polynomial(1, degree)).plus(temp).reduction(base);
        }

        return  polynomial;
    }

    private Polynomial reductionAdd(Polynomial polynomial) {
        int[] arr = polynomial.getCoef();
        for (int i = 0; i < arr.length; i++) {
            arr[i] %= 2;
            if (arr[i] < 0) {
                arr[i] += base;
            }
        }
        polynomial.setCoef(arr);
        return  polynomial;
    }

    private Polynomial reductionMult(Polynomial polynomial) {

        if (polynomial.degree() >= degree) {

            Polynomial temp = IrreduciblePolynomials.getIrreduciblePolynomials(degree);

            return polynomial.divides(temp);
        }

        return  polynomial;
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

    public HashMap<Polynomial, Polynomial> getField() {
        return field;
    }

    public void setField(HashMap<Polynomial, Polynomial> field) {
        this.field = field;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
