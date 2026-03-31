public class EmptyFileException extends IOException {
    public EmptyFileException() {
        super("Empty file");
    }
}