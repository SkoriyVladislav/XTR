package by.Skoriy.Polynom;

// see http://introcs.cs.princeton.edu/java/92symbolic/Polynomial.java.html

import java.util.Arrays;

public class Polynomial implements Comparable{
    private int[] coef;  // coefficients
    private int deg;     // degree of polynomial (0 for the zero polynomial)


    // a * x^b
    public Polynomial( int coeff, int deg ){
        coef = new int[ deg + 1 ];
        coef[ deg ] = coeff;
        this.deg = degree();
    }

    public Polynomial(int[] coeff){
        coef = coeff;
        if (coeff.length == 0) {
            this.deg = 0;
        } else {
            this.deg = coeff.length - 1;
        }
        simplify();
    }

    private void simplify() {
        int[] newCoef = new int[0];
        for(int i = coef.length; i > 0; i--) {
            if (coef[i - 1] != 0) {
                newCoef = Arrays.copyOf(coef, i);
                break;
            }
        }
        if (newCoef.length == 0) {
            coef = new int[]{0};
            deg = 0;
        } else {
            coef = newCoef;
            deg = coef.length - 1;
        }
    }

    public Polynomial( Polynomial p ){
        coef = new int[ p.coef.length ];
        for( int i = 0; i < p.coef.length; i++ ){
            coef[ i ] = p.coef[ i ];
        }//end for
        deg = p.degree();
    }


    // return the degree of this polynomial (0 for the zero polynomial)
    public int degree(){
        int d = 0;
        for( int i = 0; i < coef.length; i++ )
            if( coef[ i ] != 0 )
                d = i;
        return d;
    }


    // return c = a + b
    public Polynomial plus( Polynomial b ){
        Polynomial a = this;
        Polynomial c = new Polynomial( 0, Math.max( a.deg, b.deg ) );
        for( int i = 0; i <= a.deg; i++ ) c.coef[ i ] += a.coef[ i ];
        for( int i = 0; i <= b.deg; i++ ) c.coef[ i ] += b.coef[ i ];
        c.deg = c.degree();
        return c;
    }


    // return (a - b)
    public Polynomial minus( Polynomial b ){
        Polynomial a = this;
        Polynomial c = new Polynomial( 0, Math.max( a.deg, b.deg ) );
        for( int i = 0; i <= a.deg; i++ ) c.coef[ i ] += a.coef[ i ];
        for( int i = 0; i <= b.deg; i++ ) c.coef[ i ] -= b.coef[ i ];
        c.deg = c.degree();
        return c;
    }


    // return (a * b)
    public Polynomial times( Polynomial b ){
        Polynomial a = this;
        Polynomial c = new Polynomial( 0, a.deg + b.deg );
        for( int i = 0; i <= a.deg; i++ )
            for( int j = 0; j <= b.deg; j++ ) {
                c.coef[i + j] += (a.coef[i] * b.coef[j]);
            }
        c.deg = c.degree();
        return c;
    }


    // get the coefficient for the highest degree
    public int coeff(){return coeff( degree() ); }


    // get the coefficient for degree d
    public int coeff( int degree ){
        if( degree > this.degree() ) throw new RuntimeException( "bad degree" );
        return coef[ degree ];
    }


    /*
     Implement the division as described in wikipedia
        function n / d:
          require d ≠ 0
          q ← 0
          r ← n       # At each step n = d × q + r
          while r ≠ 0 AND degree(r) ≥ degree(d):
          t ← lead(r)/lead(d)     # Divide the leading terms
          q ← q + t
          r ← r − t * d
          return (q, r)
     */
    public Polynomial divides( Polynomial b ){
        Polynomial q = new Polynomial(0,0);
        Polynomial r = new Polynomial(this);
        while(!r.isZero() && r.degree() >= b.degree()){
            int coef = r.coeff() / b.coeff();
            int deg = r.degree() - b.degree();
            Polynomial t = new Polynomial(coef, deg);
            q = q.plus(t);
            r = r.minus(t.times(b));
        }
        return q;
    }

    public Polynomial remainder(Polynomial b) {
        Polynomial q = new Polynomial(0,0);
        Polynomial r = new Polynomial(this);
        while(!r.isZero() && r.degree() >= b.degree()){
            int coef = r.coeff() / b.coeff();
            int deg = r.degree() - b.degree();
            Polynomial t = new Polynomial(coef, deg);
            q = q.plus(t);
            r = r.minus(t.times(b));
        }
        return r;
    }


    // return a(b(x))  - compute using Horner's method
    public Polynomial compose( Polynomial b ){
        Polynomial a = this;
        Polynomial c = new Polynomial( 0, 0 );
        for( int i = a.deg; i >= 0; i-- ){
            Polynomial term = new Polynomial( a.coef[ i ], 0 );
            c = term.plus( b.times( c ) );
        }
        return c;
    }


    // do a and b represent the same polynomial?
    public boolean eq( Polynomial b ){
        Polynomial a = this;
        if( a.deg != b.deg ) return false;
        for( int i = a.deg; i >= 0; i-- )
            if( a.coef[ i ] != b.coef[ i ] ) return false;
        return true;
    }


    // test wether or not this polynomial is zero
    public boolean isZero(){
        for( int i : coef ){
            if( i != 0 ) return false;
        }//end for
        return true;
    }


    // use Horner's method to compute and return the polynomial evaluated at x
    public int evaluate( int x ){
        int p = 0;
        for( int i = deg; i >= 0; i-- )
            p = coef[ i ] + ( x * p );
        return p;
    }


    // differentiate this polynomial and return it
    public Polynomial differentiate(){
        if( deg == 0 ) return new Polynomial( 0, 0 );
        Polynomial deriv = new Polynomial( 0, deg - 1 );
        deriv.deg = deg - 1;
        for( int i = 0; i < deg; i++ )
            deriv.coef[ i ] = ( i + 1 ) * coef[ i + 1 ];
        return deriv;
    }


    // convert to string representation
    public String toString(){
        if( deg == 0 ) return "" + coef[ 0 ];
        if( deg == 1 ) return coef[ 1 ] + "x + " + coef[ 0 ];
        String s = coef[ deg ] + "x^" + deg;
        for( int i = deg - 1; i >= 0; i-- ){
            if( coef[ i ] == 0 ){
                continue;
            }else if( coef[ i ] > 0 ){
                s = s + " + " + ( coef[ i ] );
            }else if( coef[ i ] < 0 ) s = s + " - " + ( -coef[ i ] );
            if( i == 1 ){
                s = s + "x";
            }else if( i > 1 ) s = s + "x^" + i;
        }
        return s;
    }

    public int[] getCoef() {
        return coef;
    }

    public void setCoef(int[] coef) {
        this.coef = coef;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public Polynomial reduction(int baseField) {
        Polynomial p = this;

        Polynomial c = new Polynomial( 0, p.deg + 1);

        for (int i = 0; i <= p.degree(); i++) {
            if (p.coef[i] >= baseField) {
                c.coef[i] = p.coef[i] % baseField;
            } else {
                c.coef[i] = p.coef[i];
            }
        }
        c.deg = c.degree();
        c.coef = Arrays.copyOf(c.coef, c.deg + 1);
        return c;
    }

    public int compareTo(Object o) {
        Polynomial a = this;
        Polynomial b = (Polynomial) o;

        if( a.deg > b.deg ) return 1;
        if( a.deg < b.deg ) return 1;

        for( int i = a.deg; i >= 0; i--)
            if( a.coef[ i ] != b.coef[ i ] ) return a.coef[i] - b.coef[i];

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polynomial that = (Polynomial) o;

        if (deg != that.deg) return false;
        for (int i = 0; i < that.coef.length && i < coef.length; i++) {
            if (coef[i] != that.coef[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 * deg;
        return result;
    }
}