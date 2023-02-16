package cz.cuni.mff.kohut.java;

class SinglechoiceQuestion extends Question {
    public int getPoints(String[] answers) {
        if (answers.length != 1) {
            return 0;
        }
        for (String correctAnswer : correctAnswers) {
            if (answers[0].equals(correctAnswer)) {
                return 1;
            }
        }
        return 0;
    }
}
