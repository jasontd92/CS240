package spell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by jasontd on 1/23/18.
 */

public class SpellCorrector implements ISpellCorrector {

    static SortedSet<String> dist_one = new TreeSet<>();
    static SortedSet<String> dist_two = new TreeSet<>();
    static SortedSet<String> modified_words = new TreeSet<>();
    //static String best_word;
    //static int best_frequency = 0;

    //public Trie.Node root;
    private Trie trie;

    public SpellCorrector() {
        trie = new Trie();
    }

    public void useDictionary(String dictionaryFileName) throws IOException
    {
        try {

            Scanner s = new Scanner(new File(dictionaryFileName));

            while (s.hasNext()){

                String word = s.nextLine();
                trie.add(word.toLowerCase());
            }

        } catch (FileNotFoundException dictionaryfileName){
            System.out.println("File Not Found");
        } catch (Exception ex){
            System.out.println("Invalid input");
        }

    }//*end useDictionary

    public String suggestSimilarWord(String inputWord){
        inputWord = inputWord.toLowerCase();

        if (trie.find(inputWord) != null){
            return inputWord;
        }

        StringBuilder word = new StringBuilder(inputWord);

        dist_one.addAll(Deletion(word));
        dist_one.addAll(Transpose(inputWord));
        dist_one.addAll(Alteration(inputWord));
        dist_one.addAll(Insertion(word));

        String[] mod_word_list = modified_words.toArray(new String[modified_words.size()]);
        //String best_match;

        if (dist_one.size() == 0){

            for (int i = 0; i < mod_word_list.length; i++) {

                StringBuilder word_2 = new StringBuilder(mod_word_list[i]);
                dist_two.addAll(Deletion(word_2));
                dist_two.addAll(Transpose(mod_word_list[i]));
                dist_two.addAll(Alteration(mod_word_list[i]));
                dist_two.addAll(Insertion(word_2));
            }

            return trie.findBest(dist_two);
        }
        else if (dist_one.size() > 0){
            return trie.findBest(dist_one);
        }

        return null;
    }

    public SortedSet<String> Deletion(StringBuilder input){

        SortedSet<String> possible = new TreeSet<>();
        StringBuilder copy;
        
        for (int i = 0; i < input.length(); i++){
            copy  = new StringBuilder(input); //resets word each attempt
            copy.deleteCharAt(i);
            modified_words.add(copy.toString());

            if (trie.find(copy.toString()) != null){
                possible.add(copy.toString());
            }
        }

        return possible; //returns set of all possibilites
    }//*end Deletion

    public SortedSet<String> Transpose(String input){

        SortedSet<String> possible = new TreeSet<>();

        for (int i = 0; (i + 1) < input.length(); i++){
            char[] c = input.toCharArray(); //resets word each attempt
            char temp = c[i];
            c[i] = c[i+1];
            c[i+1] = temp;

            String swapped = new String(c);
            modified_words.add(swapped);
            if (trie.find(swapped) != null){
                possible.add(swapped);
            }
        }

        return possible; //returns set of all possibilites
    }//*end Transpose

    public SortedSet<String> Alteration(String input){

        SortedSet<String> possible = new TreeSet<>();

        String altered;

        for (int i = 0; i < input.length(); i++) {
            char[] c = input.toCharArray(); //resets word each loop

            for (int k = 0; k < 26; k++) {
                int convert = 'a' + k;
                char letter = (char) convert;
                c[i] = letter;
                altered = new String(c);
                modified_words.add(altered);
                if (trie.find(altered) != null) {
                    possible.add(altered);
                }

            }

        }
        return possible; //returns set of all possibilites
    }//*end Alteration

    public SortedSet<String> Insertion(StringBuilder input){

        SortedSet<String> possible = new TreeSet<>();

        StringBuilder copy;

        for (int i = 0; i < input.length(); i++) {
            copy = new StringBuilder(input); //resets word each loop

            for (int k = 0; k < 26; k++) {
                int convert = 'a' + k;
                copy.insert(i, convert);
                modified_words.add(copy.toString());

                if (trie.find(copy.toString()) != null) {
                    possible.add(copy.toString());
                }

            }

        }
        return possible; //returns set of all possibilites
    }//*end Alteration



}
