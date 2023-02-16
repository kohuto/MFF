package cz.cuni.mff.kohut.java;

import java.util.ArrayList;

abstract class Question {
    protected String[] correctAnswers;

    public void setIdQuestion(int idQuestion) {
        IdQuestion = idQuestion;
    }

    private int IdQuestion; //TODO: prirad cislo otazky

    public void setCorrectAnswers(String[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public abstract int getPoints(String[] answers);
}
