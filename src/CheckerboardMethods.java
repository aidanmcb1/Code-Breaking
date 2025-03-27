import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerboardMethods {
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

            if (current == keyEight && i < 5523 || current == keyNine && i < 5523) {
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
    public static LinkedBlockingQueue<String> prepareKeys(String filepath) {
        LinkedBlockingQueue<String> list = new LinkedBlockingQueue<String>();
        String line = "";

        try {
            FileReader in = new FileReader(filepath);
            BufferedReader filereader = new BufferedReader(in , 10);

            while ((line = filereader.readLine()) != null) {
                list.put(line);
            }

            filereader.close();
        } catch (IOException e) {
            System.out.println("Something went wrong");
        } catch (InterruptedException e) {
            System.out.println("Threading error");
        }

        return list;
    }

    /**
     * checks dictionary list for words in string and returns how many times it did.
     * @param plaintext
     * the decoded text
     * @param filepath
     * the path of the word list
     * @return
     * the amount of word hits
     */
    public static int checkDictionary(String plaintext, String filepath) {
        ArrayList<String> dictionary = new ArrayList<String>();
        
        String line = "";
        int score = 0;

        try {
            FileReader in = new FileReader(filepath);
            BufferedReader fileReader = new BufferedReader(in);

            while ((line = fileReader.readLine()) != null) {
                dictionary.add(line);
            }

            fileReader.close();
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        for (String x : dictionary) {
            Pattern word = Pattern.compile(Pattern.quote(x), Pattern.CASE_INSENSITIVE);
            Matcher check = word.matcher(plaintext);
            if (check.find()) {
                score++;
                while (!check.hitEnd()) {
                    if (check.find()) {
                        score++;
                    } 
                }
            }
        }

        return score;
    }
}
