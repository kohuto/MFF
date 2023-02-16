package cz.cuni.mff.kohut.java;

class MultichoiceQuestion extends Question {
    public int getPoints(String[] answers) {
        int points = 0;
        for (String correctAnswer : correctAnswers) {
            for (String answer : answers) {
                if (answer.equals(correctAnswer)) {
                    points++;
                    break;
                }
            }
        }
        return points;
    }
}

