package cz.cuni.mff.kohut.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader {
    int CountOfEnters;
    private boolean isEndOfStream = false;
    private boolean isNewArticle;
    public boolean thereWasEnter = false;

    BufferedReader input;
    public Reader() {
        input = new BufferedReader(new InputStreamReader(System.in));
    }

    private char getNextChar() throws IOException {
        int ch = input.read();
        if (ch == -1)
            isEndOfStream = true;
        return (char) ch;
    }

    public String GetNextWord() throws IOException {
        isNewArticle = false;
        StringBuilder Word = new StringBuilder();
        char NextChar = getNextChar();
        if(thereWasEnter)
            CountOfEnters = 1;
        else
            CountOfEnters = 0;
        thereWasEnter = false;

        while (Character.isWhitespace(NextChar) && !isEndOfStream) {
            if (NextChar == '\n')
                CountOfEnters++;
            if (CountOfEnters == 2)
                isNewArticle = true;
            NextChar = getNextChar();
        }

        while (!Character.isWhitespace(NextChar) && !isEndOfStream) {
            Word.append(NextChar);
            NextChar = getNextChar();
            if (NextChar == '\n')
                thereWasEnter = true;
        }
        if (isEndOfStream)
            return "";
        else
            return Word.toString();
    }

    public boolean isEndOfStream() {
        return isEndOfStream;
    }

    public boolean isNewArticle() {
        return isNewArticle;
    }

}