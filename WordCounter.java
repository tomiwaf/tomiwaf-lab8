public class WordCounter {
    public int processText(StringBuffer text, String stopWord) throws Exception {
        int count = 0;
        String[] words = text.toString().split("\\s+");
        if (stopWord.equals(null)) {
            if (words.length < 5){
                throw new TooSmallText();
            }
            return words.length;
        }
        if (!text.toString().contains(stopWord)) {
            throw new InvalidStopWordException("");
        }
        for (int i = 0; i < words.length; i++) {
            if(words[i].matches("[A-Za-z0-9']+")){
                count++;
            }
            if (words[i].equals(stopWord)) {
                return count;
            }
        }



    }
    public StringBuffer processFile(String path) throws Exception {
        Scanner sc = null;
        while(true){
            try{
                sc = new Scanner(new File(path));
                break;
            } catch(FileNotFoundException e){
                System.out.println(path + " not found. Enter a new file name: ");
                Scanner input = new Scanner(System.in);
                path = input.nextLine();
            }
        }
        StringBuffer text = new StringBuffer();
        while (fileScanner.hasNextLine()) {
            text.append(fileScanner.nextLine());
            text.append(" ");
        }

        fileScanner.close();

        if (text.length() == 0) {
            throw new EmptyFileException("File is empty: " + path);
        }

        return text;

    }
    public static void main(String[] args){
        while(true){
            System.out.println("Type 1 for processing a file and type 2 for processing a text");
            Scanner input = new Scanner(System.in);
            if (input != "1" || input != "2"){
                System.out.println("Type a valid number: ");
            }
        }

    }

}