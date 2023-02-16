package cz.cuni.mff.kohut.java;

import java.io.IOException;

public class Main {
    static boolean isLineComplete(String NextWord, Line line) {
        if ((line.getMaxCountOfCharsOnLine() < line.getActuallyCharsOnLine() + NextWord.length() + line.getCountOfWordsOnLine()) && line.getCountOfWordsOnLine() > 0) {
            line.setWordInQueue(true);
            return true;
        } else if ((line.getMaxCountOfCharsOnLine() == line.getActuallyCharsOnLine() + NextWord.length() + line.getCountOfWordsOnLine()) || line.getMaxCountOfCharsOnLine() < NextWord.length()) {
            line.AddWord(NextWord);
            line.setWordInQueue(false);
            return true;
        } else
            return false;
    }

    static int getLengthOfLine(String value) throws NumberFormatException {
        return Integer.parseInt(value);
    }

    public static void main(String[] args) {
        Reader r = new Reader();
        int lineLength;
        try {
            lineLength = getLengthOfLine(r.GetNextWord());
        } catch (NumberFormatException e) {
            System.out.println("Error");
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
        Line line = new Line(lineLength);
        Writer w = new Writer();
        String Word;
        while (!r.isEndOfStream()) {
            Word = r.GetNextWord();
            if (r.isNewArticle()) {
                w.WriteLastLine(line);
                line.Clear();
                line.AddWord(Word);
                w.NewArticle();
            } else {
                if (isLineComplete(Word, line)) {
                    w.WriteLineToStdout(line);
                    line.Clear();
                    if (line.isWordInQueue()) //word was too long to fit to previous line, so we have to add it to new line
                    {
                        line.AddWord(Word);
                        line.setWordInQueue(false);
                    }
                } else
                    line.AddWord(Word);
            }
        }
        if (line.getCountOfWordsOnLine() > 0) //already end of file but there are words in queue
            w.WriteLastLine(line);
    } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}