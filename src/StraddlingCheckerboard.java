import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

public class StraddlingCheckerboard {

    /**
     * Takes the cipher and runs it through the table with the selected key.
     * @param table
     * the table of keys and their letter values.
     * @param cipher
     * the cipher text.
     * @param key
     * the key used.
     * @return
     * the decoded string.
     */
    public static String decode(HashMap<String, String> table, String cipher, String key) {

        StringBuilder deciphered = new StringBuilder();
        
        for (int i = 0; i < cipher.length(); i++) {
            int current = Character.getNumericValue(cipher.charAt(i));
            int keyEight = Character.getNumericValue(key.charAt(8));
            int keyNine = Character.getNumericValue(key.charAt(9));

            if (current == keyEight || current == keyNine) {
                int oneAhead = Character.getNumericValue(cipher.charAt(i + 1));

                if (current == 0) {
                    deciphered.append(table.get("0" + oneAhead));
                    i++;
                } else {
                    deciphered.append(table.get(Integer.toString(current * 10 + oneAhead)));
                i++;
                }
            } else {
                deciphered.append(table.get(Integer.toString(current)));
            }
        }

        String result = deciphered.toString();
        return result;
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
            table.put(numbers[8] + numbers[i], letters[i + 8]);
        }
        //Third table row, adding numbers[9] to front of key. See readme for more info
        for (int i = 0; i < 10; i++) {
            table.put(numbers[9] + numbers[i], letters[i + 18]);
        }

        return table;
    }

    /**
     * Loads all keys into memory and returns a linked list.
     * @param filepath
     * the file path to be followed.
     */
    public static LinkedList<String> prepareKeys(String filepath) {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader filereader = new BufferedReader(in , 10);
        LinkedList<String> list = new LinkedList<String>();
        String line = "";

        try {
            while ((line = filereader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        return list;
    }

    public static void main(String[] args) {

        

    }
}
