package cz.cuni.mff.kohut.java;

import java.util.ArrayList;
import java.util.List;

public class Line {
    public Line(int maxCharsOnLine) {
        MaxCharsOnLine = maxCharsOnLine;
    }

    public void AddWord(String Word) {
        if (Word.length() > 0)
            WordsOnLine.add(Word);
        ActuallyCharsOnLine += Word.length();
    }

    public void Clear() {
        WordsOnLine.clear();
        ActuallyCharsOnLine = 0;
    }

    public String Get(int i) {
        return WordsOnLine.get(i);
    }

    public boolean isWordInQueue() {
        return WordInQueue;
    }

    public void setWordInQueue(boolean wordInQueue) {
        WordInQueue = wordInQueue;
    }

    public int getActuallyCharsOnLine() {
        return ActuallyCharsOnLine;
    }

    public int getMaxCountOfCharsOnLine() {
        return MaxCharsOnLine;
    }

    public int getCountOfWordsOnLine() {
        return WordsOnLine.size();
    }

    private int ActuallyCharsOnLine;
    private int MaxCharsOnLine;
    private boolean WordInQueue;
    private List<String> WordsOnLine = new ArrayList<>();
}
