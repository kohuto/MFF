package cz.cuni.mff.kohut.java;

public class Main {
    public static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i = i + 1;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivot = partition(array, low, high);
            quickSort(array, low, pivot-1);
            quickSort(array, pivot + 1, high);
        }
    }

    public static int[] makeIntArrayFromArgs(String[] array) throws NumberFormatException{
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array;
        try {
            array = makeIntArrayFromArgs(args);
        }
        catch (NumberFormatException ex){
            System.out.println("Input error");
            return;
        }
        quickSort(array,0,array.length-1);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}