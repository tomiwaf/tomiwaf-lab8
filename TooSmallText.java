public class TooSmallText extends Exception {
    public TooSmallText() {
        super("You must have 5 or more words in your text.");
    }
}