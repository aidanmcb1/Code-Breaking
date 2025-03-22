import java.util.HashMap;

public class StraddlingCheckerboard {

    public static String decode(HashMap<String, String> key) {
        //TODO this method

        return " ";
    }
    /**
     * Creates a hashmap of <String, String> pairs for each possible numerical value with the corresponding letter.
     * @param key
     * String of 10 digit number.
     * @return
     * The completed hashmap.
     * @requires {@code key} to be exactly 10 digits with no repeating values.
     */
    public static HashMap<String, String> createTable(String key) {

        HashMap<String, String> table = new HashMap<String, String>();
        //The order of the letters in the table (size 10*3, spots 9 and 10 are left blank)
        String[] letters = {"a", "s", "i", "n", "t", "o", "e", "r", "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "p", "q", "u", "v", "w", "x", "y", "z", ".", "#"};
        //Initialize and generate array of key numbers
        String[] numbers = new String[10];
        for (int i = 0; i < 10; i++) {
            numbers[i] = Character.toString(key.charAt(i));
        }

        //First table row (spots 9 and 10 ignored)
        for (int i = 0; i < 8; i++) {
            table.put(numbers[i], letters[i]);
        }
        //Second table row, adding numbers[8] to front of key. See readme for more info
        for (int i = 0; i < 10; i++) {
            table.put(numbers[8] + numbers[i + 8], letters[i + 8]);
        }
        //Third table row, adding numbers[9] to front of key. See readme for more info
        for (int i = 0; i < 10; i++) {
            table.put(numbers[9] + numbers[i + 18], letters[i + 18]);
        }

        return table;
    }

    public static void main(String filePath) {
        
    }
}
