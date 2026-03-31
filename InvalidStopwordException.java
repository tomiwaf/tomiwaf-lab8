public class InvalidStopwordException  extends Exception {
    public InvalidStopwordException() {
        super("This file does not contain the stopword provided.");
    }
}