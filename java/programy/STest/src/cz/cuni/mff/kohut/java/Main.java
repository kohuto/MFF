package cz.cuni.mff.kohut.java;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.useDelimiter("\\A").next(); //read whole input
        List<String> lines = asList(input.split("\n\n"));

        HashMap<String, String[]> map = new HashMap<>();

        List<String> correctAnswers = lines.stream()
                .filter(s -> s.startsWith("single") || s.startsWith("multi"))
                .flatMap(s -> stream(s.split("\n")))
                .filter(s -> s.matches("^\\d+\\..*") || s.startsWith("Answer")).toList();

       for (int i = 0; i < correctAnswers.size(); i += 2) {
            map.put(correctAnswers.get(i).substring(0,correctAnswers.get(i).indexOf(".")), correctAnswers.get(i + 1).substring(8).split(" "));
        }

        List<String[]> studentAnswers = lines.stream()
                .filter(s -> !s.startsWith("single") && !s.startsWith("multi"))
                .map(s -> s.split("\n")).toList();

        for (String[] item : studentAnswers) {
            int point = 0;
            System.out.println(item[0]);
            // Skip the first element
            item = Arrays.copyOfRange(item, 1, item.length);
            for(int i = 0; item.length> i; i++){
                List<String> answeredQuestion = new ArrayList<>();
                var mistakes = 0;
                int indexOfDot = item[i].indexOf(".");
                String key = item[i].substring(0, indexOfDot);
                answeredQuestion.add(key);
                var rest = Arrays.asList(item[i].substring(indexOfDot+2).split(" "));
                var correct = Arrays.asList(map.get(key));

                for (var answer:rest) {
                   if(!correct.contains(answer))
                       mistakes++;
                }
                for (var answer:correct) {
                    if(!rest.contains(answer))
                        mistakes++;
                }
                System.out.println(i+1+". "+mistakes);
                point += mistakes;



            }
            int result = 0;
            switch (point) {
                case 0,1,2 -> result =1;
                case 3,4,5 -> result =2;
                case 6,7,8 -> result =3;
                default -> result = 4;
            }
            System.out.println("Result: " + result+ " ("+point+")"); //TODO: znamka
            System.out.println();
        }
    }
}


