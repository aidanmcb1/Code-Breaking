import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//Class for implementing comparator to be used with sort.
class Answer {
    public int score;
    public String plaintext;

    public Answer(int s, String p) {
        score = s;
        plaintext = p;
    }

    @Override
    public String toString() {
        return "Score: " + this.score + ". Plaintext: " + this.plaintext;
    }
}

//comparator class implementation.
class AnswerCompare<T> implements Comparator<T> {
    public int compare(T obj1, T obj2) {

        Answer a = (Answer) obj1;
        Answer b = (Answer) obj2;

        if (a.score < b.score) {
            return -1;
        }
        if (a.score > b.score) {
            return 1;
        }
        return 0;
    }
}

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
    public static LinkedList<String> prepareKeys(String filepath) {
        LinkedList<String> list = new LinkedList<String>();
        String line = "";

        try {
            FileReader in = new FileReader(filepath);
            BufferedReader filereader = new BufferedReader(in , 10);

            while ((line = filereader.readLine()) != null) {
                list.add(line);
            }

            filereader.close();
        } catch (IOException e) {
            System.out.println("Something went wrong");
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

    public static void main(String[] args) {

        String dictionary = "words.txt";
        String keyFile = "keys.txt";
        String cipher = "6398996552068459109949406691099494014909931099879089946374995619994099720994399295660994630809968749968365656651649997256974089994546399463099809102549099463090996508029109994099699584996404997689895751406599945463994630529966716495764089699940997209984076651649946358996213946606564099612369994630529966516495584899139496994630209972099276262762727991084660659951997996839524907265996706351659946305299633690996795566655164969946309099854995199799420099849569979967085650997996525065999597996139514751969974994630994569099361994630995191785319994099945666699621394997666699463749994099100659943996213949983994637499940996871994066669946306999463052999766768099519939529906997520999454639994326589946309099766669994566669995165028471659699799943265993619994721516499439946309969751401716809965097724690149951994630990910149946374994630909969958499915854994630809927626276272796994630996533299361994630996839524907265999456666996866380997165996636862996706351659990395969946302099589913990935499439946309984200499951660889990395996607910997999432620299518565099463099679556665516499439966049990395996776862995196996539913499640499427979706596996132994630995140669971766908489994099744768639986204686308993619983690996976032996395697199667164957640899439963066979990395996404998472406596996971657251996863510809958998973620199519946309907849936199785799463099806831659966726408499683145101496995489994326589969958499670999723139516806599945463996802475199975468639968314395289932995199680247519997724899361994630999754686399271640969954899890666676766099842956849520995899856997660969954996836969951568740899642769697456876699690715164997666938499093686695859106690994632395646399943265993265029699549958999425440199945463994633958716589936199686372768402899076863992097208014516499799977245689566729969071516496998977158639968369089961236999095239709946309968314510149936199697190999701518956678996795499693849989707620289936199897715863997209951994630994943996831451014899361994630997690256878969954995899463099683696931996671649576409951994630998395463021999772499361991324639976902568799716599463099940840219997724993619983954639976902568796996427696974568766996907151649958996836969951568740659997724669099679099943265993265029997724669099679099686371645164994630990165516489936199943265899716599977246690996790996863338516499680247519997231395189699016466586399683690899612369997199586671659910729909523970996795499947899897207659994326665945650996790997199068313695687666690999739402619566990699752096995499589989736201995199463099650180669099973979566740659989567683145101499361995165579974994630998395463993619978579951994630996801427669971659913246302199977248993619913246399769025687993199463099586671659968314510149936199795842766579951994630998395463021693849997724993619946309966726408499683145101499761256879971659951996971909951402174531766996831476848998956863997899791574531997165996795851088969954996378997996672640991956967029936199913940668969954999580899943265990165516489961329983690995161326974531998956863997899910267994018099716599789706849699549995808999432659932650299613299693849951613269745319951686695655164991395199236608969963516559971659995265959972099494399847165726599667164957640899463749995809965561610201499942545164998908406989971659997206102996556161020149991368767956672909967954994637499720996866380990139564639946374994630529989707620289968719995165028471659907686399346302969963516559958998973620199945650669099519951655799089706857666690994630991324639936199516557997165999526595995199107267909997762584719699727675689932564517408991072999463020997612568799785799716599095239709969004969954996706876909946309989736201996671649576409961729983954630784997165991324639408499361994635899976676809971659963789969719099849565014899066809463020996706879580995499427910660659976631649994546399799895686808861956699206656453196995499589913409432463909961329963791516499943265992334899697650993619946320099683183171489994635686399687199670996154995143996971909991394066994069976674089943996502591099667264099804899361992066740659994326589699973249564950809932564517406599519909523970991072998977158639971659958997996671649576409936199799856956672994909709699463099693849936791539589965561610201680995899463749997324956495080999580899178766999139406689994630209989771586399637899913940668999766958991787669968318317148969954995899897362019951994630990784993619983954639976902568799716599519983690996839514250899361998395463021997612568796996701647665995899799858402996671649576409943996351655998973620199519907840219951655799716599105646367325164996771646676508639699549909397208808991395199236608997165991956967029946323956463999432659901655164896995499637899719908970685766669099256863998049936199016551648996132999102678969943999580997991956902766994399683951499799670164766599139519990395996995849995809979968667885615029932996907895209994326596994635899589976683997996863727684025845689936199697165725199686351080969929588571996836908996123699907840219909523970997165995899897362019976823889968014276699785799716599514399078402199785799089706857666690991072997996976032992756623765994637499682388089946309968314510149699549963789909342069066909919569023958999432659901655164899463749968722909964276969745687669969071516499839946374999432658996871996709997667680659951997666938499719099326502969994326599326502995899958065994399683696995156874099439756899716599613689589699548999776674766996831831714899716599683183171499686695840289972099799686376666016409961329961320564199897076202896";

        String currentKey = "";
        ArrayList<Answer> answers = new ArrayList<Answer>();
        LinkedList<String> keys = prepareKeys(keyFile);

        while (!keys.isEmpty()) {
            currentKey = keys.pop();
            HashMap<String, String> table = createTable(currentKey);
            String plaintext = decode(table, cipher, currentKey);
            int score = checkDictionary(plaintext, dictionary);

            if (score >= 700) {
                Answer answer = new Answer(score, plaintext);
                answers.add(answer);
            }
        }

        Comparator<Answer> comparator = new AnswerCompare<Answer>();
        Collections.sort(answers, comparator);

        try {
            File answerFile = new File("answers.txt");
            answerFile.createNewFile();
            FileWriter writer = new FileWriter(answerFile);

            for (Answer x : answers) {
                writer.write(x.toString());
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }
}
