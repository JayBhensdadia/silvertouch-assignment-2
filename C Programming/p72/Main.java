import java.util.Arrays;

public class Main {

    public static Complex[] fft(Complex[] x) {
        int n = x.length;

        if (n == 1) {
            return x;
        }

        Complex[] even = new Complex[n / 2];
        Complex[] odd = new Complex[n / 2];
        for (int i = 0; i < n / 2; i++) {
            even[i] = x[2 * i];
            odd[i] = x[2 * i + 1];
        }

        Complex[] evenFFT = fft(even);
        Complex[] oddFFT = fft(odd);

        Complex[] result = new Complex[n];
        for (int i = 0; i < n / 2; i++) {
            double angle = -2 * Math.PI * i / n;
            Complex twiddle = new Complex(Math.cos(angle), Math.sin(angle));

            result[i] = evenFFT[i].add(twiddle.multiply(oddFFT[i]));
            result[i + n / 2] = evenFFT[i].subtract(twiddle.multiply(oddFFT[i]));
        }

        return result;
    }

    public static void main(String[] args) {
        Complex[] x = {
                new Complex(1, 0),
                new Complex(2, 0),
                new Complex(3, 0),
                new Complex(4, 0)
        };

        Complex[] result = fft(x);

        System.out.println("FFT Result: " + Arrays.toString(result));
    }
}

class Complex {
    private final double real;
    private final double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imag + other.imag);
    }

    public Complex subtract(Complex other) {
        return new Complex(this.real - other.real, this.imag - other.imag);
    }

    public Complex multiply(Complex other) {
        return new Complex(
                this.real * other.real - this.imag * other.imag,
                this.real * other.imag + this.imag * other.real
        );
    }

    @Override
    public String toString() {
        return "(" + real + " + " + imag + "i)";
    }
}
