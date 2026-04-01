import java.util.*;
import java.io.*;

public class WordCounter {

    public static int processText(StringBuffer text, String stopWord)
            throws InvalidStopwordException, TooSmallText {

        String[] words = text.toString().split("\\s+");
        int totalCount = 0;
        int stopCount = 0;
        boolean foundStopword = false;

        for (String word : words) {
            word = word.replaceAll("^[^A-Za-z0-9']+|[^A-Za-z0-9']+$", "");

            if (word.matches("[A-Za-z0-9']+")) {
                totalCount++;

                if (!foundStopword) {
                    stopCount++;
                }

                if (stopWord != null && word.equals(stopWord)) {
                    foundStopword = true;
                }
            }
        }

        if (totalCount < 5) {
            throw new TooSmallText("Only found " + totalCount + " words.");
        }

        if (stopWord == null) {
            return totalCount;
        }

        if (!foundStopword) {
            throw new InvalidStopwordException("Couldn't find stopword: " + stopWord);
        }

        return stopCount;
    }

    public static StringBuffer processFile(String path) throws EmptyFileException {
        Scanner sc = null;
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                sc = new Scanner(new File(path));
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Enter new filename:");
                path = input.nextLine();
            }
        }

        StringBuffer text = new StringBuffer();

        while (sc.hasNextLine()) {
            text.append(sc.nextLine());
            if (sc.hasNextLine()) {
                text.append(" ");
            }
        }

        sc.close();

        if (text.length() == 0) {
            throw new EmptyFileException(path + " was empty");
        }

        return text;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option = 0;

        while (option != 1 && option != 2) {
            System.out.println("Enter 1 to process a file or 2 to process text:");
            String choice = input.nextLine();
            try {
                option = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                option = 0;
            }
        }

        String stopword = null;
        if (args.length > 1) {
            stopword = args[1];
        }

        StringBuffer text;

        if (option == 1) {
            try {
                text = processFile(args[0]);
            } catch (EmptyFileException e) {
                text = new StringBuffer("");
            }
        } else {
            text = new StringBuffer(args[0]);
        }

        try {
            int count = processText(text, stopword);
            System.out.println("Found " + count + " words.");
        } catch (InvalidStopwordException e) {
            System.out.println("Stopword not found. Enter a new stopword:");
            String newStopword = input.nextLine();

            try {
                int count = processText(text, newStopword);
                System.out.println("Found " + count + " words.");
            } catch (InvalidStopwordException e2) {
                System.out.println("Stopword not found again.");
            } catch (TooSmallText e2) {
                System.out.println(e2);
            }
        } catch (TooSmallText e) {
            System.out.println(e);
        }
    }
}