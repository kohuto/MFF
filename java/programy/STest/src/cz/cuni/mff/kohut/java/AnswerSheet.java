package cz.cuni.mff.kohut.java;

import java.util.ArrayList;

class AnswerSheet {
    private String name;
    private String[] answers;
    private ArrayList<Question> questions;

    public AnswerSheet(String line, ArrayList<Question> questions) {
        String[] parts = line.split(":");
        name = parts[0];
        answers = parts[1].split(" ");
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public int getPoints(int questionNumber) {
        return questions.get(questionNumber).getPoints(answers);
    }

    public int getPoints() {
        int points = 0;
        for (int i = 0; i < questions.size(); i++) {
            points += getPoints(i);
        }
        return points;
    }

    public String getGrade() {
        int points = getPoints();
        if (points >= questions.size() * 0.8) {
            return "A";
        } else if (points >= questions.size() * 0.6) {
            return "B";
        } else if (points >= questions.size() * 0.4) {
            return "C";
        } else if (points >= questions.size() * 0.2) {
            return "D";
        } else {
            return "F";
        }
    }
}

