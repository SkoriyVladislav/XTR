package by.Skoriy.Field;

import by.Skoriy.Polynom.IrreduciblePolynomials;
import by.Skoriy.Polynom.Polynomial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FiniteField {
    //private SortedMap<Polynomial, Polynomial> field;
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
                temp = reduction(temp.times(first));
                if (temp.equals(unit)) {
                    if (i + 1 != Math.pow(base, degree))
                    writer.write("The field is not complete \n");
                    break;
                }
                writer.write(new Polynomial(1, i).toString() + " = " + temp.toString() + "\n");
                //field.put(new Polynomial(1, i), temp);
            }

            writer.write(new Polynomial(0, i).toString() + " = " + new Polynomial(0, 0).toString());
            //field.put(new Polynomial(1, field.size()), new Polynomial(0, 0));

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

    @Override
    public String toString() {
        return "FiniteField{" +
                "fileName='" + fileName + '\'' +
                ", base=" + base +
                ", degree=" + degree +
                '}';
    }
}
