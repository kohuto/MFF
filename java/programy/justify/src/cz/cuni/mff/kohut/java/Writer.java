package cz.cuni.mff.kohut.java;

public class Writer {
    public void WriteLineToStdout(Line line) {
        if (line.getCountOfWordsOnLine() > 1) {
            int SpacesOnLine = line.getMaxCountOfCharsOnLine() - line.getActuallyCharsOnLine();
            int SpacesBetweenWords = SpacesOnLine / (line.getCountOfWordsOnLine() - 1);
            int MoreSpaces = SpacesOnLine % (line.getCountOfWordsOnLine() - 1);
            for (int i = 0; i < line.getCountOfWordsOnLine(); i++) {
                System.out.print(line.Get(i));
                if (i < line.getCountOfWordsOnLine() - 1) {
                    String Spaces = " ";

                    System.out.print(Spaces.repeat(SpacesBetweenWords));
                }
                if (i < MoreSpaces)
                    System.out.print(' ');
            }
        } else
            System.out.print(line.Get(0));
        System.out.println();
    }

    public void NewArticle() {
        System.out.println();
    }

    public void WriteLastLine(Line line) {
        if (line.getCountOfWordsOnLine() > 0) {
            for (int i = 0; i < line.getCountOfWordsOnLine() - 1; i++) {
                System.out.printf(line.Get(i));
                System.out.printf(" ");
            }
            System.out.println(line.Get(line.getCountOfWordsOnLine() - 1));
        }
    }
}
