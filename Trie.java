package spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;

/**
 * Created by jasontd on 1/20/18.
 */


public class Trie implements ITrie {

    static int node_counter = 1; //the root node counts as one

    //public int frequency_root = 0;
    //public Node[] node = new Node[26];
    private Node root;

    public Trie() {
        root = new Node();
    }//*end Trie constructor

    public class Node implements ITrie.INode {



        private int frequency = 0; //number of word occurences. Describes PARENT node!
        private Node[] children = new Node[26];

        Node() {
            Node[] children;
            int frequency;
        }//*end Node.constructor

        public int getValue() {

            return frequency;
        }//*end Node.getValue()


        public void addHelper(String word){

            if (word.length() == 0){
                return;
            }

            int index = word.charAt(0) - 'a'; //zeros ASCI number

            if (children[index] == null) {
                children[index] = new Node(); //array[index] now pointing to a new Node
                children[index].children = new Node[26];
                children[index].frequency = 0;
                node_counter++;

                if(word.length() == 1){ //end of word reached
                    children[index].frequency++;
                }

                children[index].addHelper(word.substring(1)); //recursive call, without first letter
            }
            else {
                if (word.length() == 1) { //end of word reached
                    children[index].frequency++;
                }
                children[index].addHelper(word.substring(1));
            }//*end Node.addHelper()

        }

        public Node findHelper(String word){

            int index = word.charAt(0) - 'a';



            if (word.length() == 1){
                if (children[index] != null){
                    return children[index];
                }
                else {return null;}
            }
            else if (children[index] == null){
                return null;
            }

            return children[index].findHelper(word.substring(1));
        }//*end Node.findHelper()

        public int getWordCountHelper(int word_counter){
            for (int i = 0; i < 26; i++){
                if (children[i].frequency > 0){
                    word_counter++;
                    children[i].getWordCountHelper(word_counter);
                }
            }
            return word_counter;
        }//*end Node.getWordCountHelper()


        public String toStringHelper(StringBuilder st){

            for (int i = 0; i < 26; i++){
                if (children[i] != null){
                    int letter = 'a' + i;
                    st.append((char) letter);

                    if (children[i].frequency > 0){
                        st.append("/n");
                    }
                }
                children[i].toStringHelper(st);
            }
            return st.toString();
        }//*end Node.toStringHelper()

        public boolean equalsHelper(Node o){
            for (int i = 0; i < 26; i++){

                if ((children[i] != null) && (o.children[i] != null)){
                    return children[i].equalsHelper(o.children[i]);
                }
                else {
                    return false;
                }
            }
            return true;
        }//*end Node.equalsHelper()

    }//***---end Node Class---***



    public void add(String word) {
        root.addHelper(word);
    }//*end Trie.add()

    public Node find(String word){
        return root.findHelper(word);
    }//*end Trie.find()

    public int getWordCount(){
        int word_counter = 0;
        return root.getWordCountHelper(word_counter);
    }// *end Trie.getWordCount()

    public int getNodeCount(){
        return node_counter;
    }//*end Trie.getNodeCount()
    
    public String toString(){//@Overload
        StringBuilder st = new StringBuilder();
        return root.toStringHelper(st);
    }//*end Trie.toString()

    public int hashCode(){
        return getWordCount() * getNodeCount() * 31;
    }//*end Trie.hashCode()

    public boolean equals(Object o){

        if (o == null){
            return false;
        }
        else if ( !(o instanceof Trie)){
            return false;
        }
        else{
            Trie t = (Trie) o;
            return root.equalsHelper(t.root);
        }

    }//*end Trie.equals();

    public String findBest(SortedSet<String> myset){

        String[] options = myset.toArray(new String[myset.size()]);
        Arrays.sort(options);
        String best_word = "";
        int best_freq = 0;

        for (int i = 0; i < options.length; i++){

            int current_freq = root.findHelper(options[i]).frequency;
            if( current_freq > best_freq) { //if frequencies are equal, array is already sorted alphabetically
                best_freq = current_freq;
                best_word = options[i];
            }
        }
        return best_word;
    }//*end Trie.findBest

}//***----end Trie Class---*
