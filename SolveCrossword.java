import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

public class SolveCrossword {


  private HashMap<String, List<String>> dictionary;

  public SolveCrossword() {
    dictionary = readDictionary();
  }

  /**
   * Return a String with the letters of the string sorted.
   * Expects argument to be not null.
  **/
  private static String sortWord(String str) {
    char tempArray[] = str.toCharArray();
    Arrays.sort(tempArray);
    return new String(tempArray);
  }

  /**
   * Gets word list for an key (string of sorted letters).
  **/
  public List<String> getWords(String input) {
    return dictionary.get(input);
  }

  /**
   * Reads a list of words from the file "words_alpha.txt" expected to be in the
   * direcetory where the program is being run.
  **/
  private static HashMap readDictionary() {
    try {
      HashMap<String, List<String>> retVal = new HashMap();
      File wordsFile = new File("words_alpha.txt");
      Scanner reader = new Scanner(wordsFile);
      while (reader.hasNextLine()) {
        String word = reader.nextLine();
        String sortedWord = sortWord(word);
        List<String> words = retVal.get(sortedWord);
        if (words == null)
        {
          words = new LinkedList<String>();
          retVal.put(sortedWord, words);
        }
        words.add(word);
      }
      return retVal;
    } catch (FileNotFoundException fe) {
      System.err.println("Dictionary file not found.");
      return null;
    }
  }

  /**
   * Given a string, creates all possible combinations of sorted letters
  **/
  private static List<String> getSortedAnagrams(String input) {
    String sortedInput = sortWord(input);
    List<String> retVal = new LinkedList<String>();
    getSortedAnagramsRecur(sortedInput, retVal, 0);
    return retVal;
  }


  /**
   * Recursively generates possibilities by keeping and not keeping the letter at
   * index, and calling recursively by advancing the index
   */
  private static void getSortedAnagramsRecur(String str, List<String> list, int index) {

    // we've reached the last letter, prune out all the blanks and add item to list
    if (index == str.length()) {
      String val = str.replaceAll(" ", "");
      if (! val.equals(""))
        list.add(val);
      return;
    }

    // keep the letter at index, and recurse down to the next letter
    getSortedAnagramsRecur(str, list, index + 1);

    // flip the letter at index to blank (ie. don't pick), and recurse down to the next letter
    char[] arr = str.toCharArray();
    arr[index] = ' ';
    String newStr = new String(arr);

    getSortedAnagramsRecur(newStr, list, index + 1);
  }

  public static void main(String[] args) {
    SolveCrossword sc = new SolveCrossword();
    List<String> wordPossibilities = new LinkedList<String>();
    List<String> l = getSortedAnagrams(args[0]);
    l.forEach(i ->
      {
        // System.out.println(i);
        List<String> wordList = sc.getWords(i);
        if (wordList != null)
          wordList.forEach(w -> { wordPossibilities.add(w); });
      }
    );
    wordPossibilities.forEach(w -> {System.out.println(w);});
    /*
    HashMap dict = readDictionary();
    System.out.println("DictCount: " + dict.size());
    System.out.println("DictCount: " + dict.keySet().size());
    */
  }
}

