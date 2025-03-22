import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

public class CheckboardGenerateKeys {

    /*This class is to generate valid keys (10 digits, all unique) for straddling checkerboard 
    *to avoid wasting compute on multiple runs.
    */
    public static void main(String[] args) {

        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        File keys = new File("keys.txt");

        try {
            keys.createNewFile();
            FileWriter writer = new FileWriter(keys);
            int total = 0;

            System.out.println("Starting.");
            long timeStart = System.currentTimeMillis();

            while (numbers[0] < 10) {
                
                for (int i = 0; i < 10; i++) {
                    //AnyMatch needs the variable to be final.
                    final int test = i;
                    if (!IntStream.of(numbers).anyMatch(x -> x == test)) {
                        //If a value from 0 to 9 is not found in test number, break back to while and start over with new number.
                        //Otherwise print valid number to file.
                        break;
                    }
                    if (i == 9) {
                        total++;
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < 10; j++) {
                            sb.append(numbers[j]);
                        }

                        writer.write(sb.toString());
                        writer.write("\n");
                        writer.flush();
                    }
    
                }
                
                numbers[9]++;
                for (int i = 9; i > 0; i--) {
                    if (numbers[i] > 9) {
                        numbers[i] = 0;
                        numbers[i - 1]++;
                    }
                }
            }

            long timeEnd = System.currentTimeMillis();
            double totalTime = timeEnd - timeStart;

            System.out.println("Done.");
            System.out.println("Total number of keys: " + total + ".");
            System.out.println("Took " + totalTime + " seconds.");

            writer.close();

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }
}
