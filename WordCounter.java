import java.util.*;
import java.io.*;

public class WordCounter {

    public static int processText(StringBuffer text, String stopWord)
            throws InvalidStopwordException, TooSmallText {

        int count = 0;
        String[] words = text.toString().split("\\s+");

        boolean found = false;

        for (String word : words) {

            word = word.replaceAll("^[^A-Za-z0-9']+|[^A-Za-z0-9']+$", "");

            if (word.matches("[A-Za-z0-9']+")) {
                count++;

                if (stopWord != null && word.equals(stopWord)) {
                    found = true;
                    break;
                }
            }
        }

        if (count < 5) {
            throw new TooSmallText("Only found " + count + " words.");
        }

        if (stopWord != null && !found) {
            throw new InvalidStopwordException("Couldn't find stopword: " + stopWord);
        }

        return count;
    }

    public static StringBuffer processFile(String path)
            throws EmptyFileException {

        Scanner sc = null;

        while (true) {
            try {
                sc = new Scanner(new File(path));
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Enter new filename:");
                Scanner input = new Scanner(System.in);
                path = input.nextLine();
            }
        }

        StringBuffer text = new StringBuffer();

        while (sc.hasNextLine()) {
            text.append(sc.nextLine());
            text.append(" ");
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
            option = input.nextInt();
            input.nextLine();
        }

        String stopword = null;
        if (args.length > 1) {
            stopword = args[1];
        }

        StringBuffer text = new StringBuffer();

        if (option == 1) {
            try {
                text = processFile(args[0]);
            } catch (EmptyFileException e) {
                System.out.println(e);
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

        input.close();
    }
}