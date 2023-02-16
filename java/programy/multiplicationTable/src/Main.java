

public class Main {
    public static void main(String[] args) {
        int parameter = Integer.parseInt(args[0]);
        int countOfDigitMaxValue = parameter == 0 ? 1 : (int) Math.floor(Math.log10(parameter * 10)) + 1;
        for (int i = 1; i <= 10; i++) {
            int product = i * parameter;
            int countOfDigitProduct = parameter == 0 ? 1 : ((int) Math.floor(Math.log10(product)) + 1);
            int countOfSpaceFromRight = countOfDigitMaxValue - countOfDigitProduct + 1;
            String spacesFromRight = " ".repeat(countOfSpaceFromRight);
            String spacesFromLeft = i > 9 ? "" : " ";
            System.out.println(spacesFromLeft + i + " * " + parameter + " =" + spacesFromRight + product);
        }
    }
}

