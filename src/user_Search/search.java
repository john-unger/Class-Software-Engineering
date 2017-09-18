package user_Search;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import user_GUI.MakeMenuB;

import java.util.Set;
import java.util.TreeSet;
import java.util.*;
/**
 * Responsible for conducting searches and returning a 
 * formatted result to the user interface.
 * 
 * @author Team 11
 *
 */
public class search
{
  ArrayList<String> formatedResults = new ArrayList<String>();
  /**
   * Conducts a search from the index and returns title, ordinal
   * number and paragraph of hit result
   * 
   * @param word the term to be searched
   * @return formatted results
   */
  public ArrayList<String> searchResults(String word)
  {
    String match = "";  //holds each formatted results
    String paragraph = ""; //holds each paragraph for each result
    ArrayList<String> results = new ArrayList<String>(); //holds all the formatted results
    FileReader fileReader;
    BufferedReader reader;
    
    try
    {
      word = word.toLowerCase();
      String[] words = word.split("[\\W]");
      // for each word read the index object and retrieve paragraph.
      for (String search: words)
      {
        fileReader = new FileReader(new File(MakeMenuB.getUserPath() + File.separator + "Index" 
            + File.separator + search +".txt"));
        reader = new BufferedReader(fileReader);
        String line = null;
        StringBuilder ln = new StringBuilder();
        
        // reads the content of each word file to collect paragraph information
        while ((line = reader.readLine()) != null) 
        {
          File para = new File(MakeMenuB.getUserPath() + File.separator + "Paragraphs"
              + File.separator + line+".txt");
          FileReader paraReader = new FileReader(para);
          BufferedReader paragraphRead = new BufferedReader(paraReader);
          ln.append(line);
          ln.insert(ln.lastIndexOf(" ") + 1, "</font></strong>(Paragraph: ");
          match =  "<strong><font size=\"6\">" + ln.toString() + ")\n<p>";
          
          //gets the paragraph for each saved entry for each word
          while ((paragraph = paragraphRead.readLine()) != null)
          {
            match = match + paragraph /*+ "\n"*/;
            match = match + "</p>";
            results.add(match);
          }
          
          // resets each string
          match = "";
          paragraph = "";
          line = null;
          ln.setLength(0);
          paragraphRead.close();
        }
      }
     }
      catch (FileNotFoundException e) { return results; }
      catch (IOException e) { return results; }
      return results;
  }
  /**
   * Prepares the query to be searched in intersection form
   * @param word the query to be searched
   * @return formated arraylist of results
   */
  public ArrayList<String> PrepareAnd(String word)
  {
    String match = "";  //holds each formatted results
    ArrayList<String> results = new ArrayList<String>(); //holds all the formatted results
    Map<String, Set<File>> holdAll = new HashMap<String, Set<File>>();
    FileReader fileReader;
    Set<File> compare = new TreeSet<File>();
    BufferedReader reader;
    try
    {
      results.add(match);
      word = word.toLowerCase();
      String[] words = word.split("[\\W]");
      // for each word read the index object and retrieve paragraph.
      for (String search: words)
      {
        fileReader = new FileReader(new File(MakeMenuB.getUserPath() + File.separator + "Index" 
            + File.separator + search +".txt"));
        reader = new BufferedReader(fileReader);
        holdAll.put(search, new TreeSet<File>());
        String line = null;
        
        // reads the content of each word file to collect paragraph information
        while ((line = reader.readLine()) != null) 
        {
          File para = new File(MakeMenuB.getUserPath() + File.separator + "Paragraphs" 
              + File.separator +line+".txt");
          holdAll.get(search).add(para);
        }
      }
      int value = 0;
      for (Entry<String, Set<File>> entry : holdAll.entrySet())
      {
       if (value == 0)
       {
         compare = entry.getValue();
         value++;
       }
       else
         compare.retainAll(entry.getValue());
      }
  
    }
     catch (FileNotFoundException e) {}
     catch (IOException e)  {} 
     return searchAnd(compare);
  }    
  /**
   * Constructs a search based on intersection, only returns paragraphs
   * that contain all the words in the query
   * @param searched the files of paragraphs that have all words in them
   * @return formatted list of string results
   */
   public ArrayList<String> searchAnd(Set<File> searched)
   {
     ArrayList<String> formated = new ArrayList<String>();
     String line = "";
     String result = "";
     for (File files: searched)
     {
       try
       {
         FileReader paraReader = new FileReader(files);
         BufferedReader paragraphRead = new BufferedReader(paraReader);
         line = files.getName();
         line = line.substring(0, line.length() - 4);
         result = "\n" + line + "\n";
         String paragraph = "";
         while ((paragraph = paragraphRead.readLine()) != null)
         result = result + paragraph;
         formated.add(result);
         line = "";
         result = "";
         paragraph = "";
         paragraphRead.close();
       }
       catch (FileNotFoundException e){}
       catch (IOException e)  {}  
     }
     return formated;
   }
   /**
    * Method is responsible for building the word based on whether it is a suffix
    * or prefix
    * @param word is the search term to be looked up
    * @param prefix whether the search type is prefix
    * @return ArrayList of Strings that are part of the results
    */
   public ArrayList<String> fixBuilder(final String word, Boolean prefix) 
   {
     File[] foundFiles = null;
     File dir = new File(MakeMenuB.getUserPath() + File.separator + "Index");
     if (prefix)
     {
       foundFiles = dir.listFiles(new FilenameFilter() {
           public boolean accept(File dir, String name) {
               return name.startsWith(word);
           }
       });
     }
     else
     {
       foundFiles = dir.listFiles(new FilenameFilter() {
         public boolean accept(File dir, String name) {
             return name.endsWith(word+".txt");
         }
     });
     }
     Set<File> mySet = new HashSet<File>(Arrays.asList(foundFiles));
     return preAndSuf(mySet);
   }
   /**
    * Returns a formated string for prefix or suffix searches
    * @param foundFiles the files of words that match the prefix or suffix
    * @return the formated strings in a list (no duplicates)
    * 
    */
   public ArrayList<String> preAndSuf(Set<File> foundFiles)
   {
     ArrayList<String> found = new ArrayList<String>();
     try{
     for (File file : foundFiles) {
       {
         FileReader reader = new FileReader(file);
         BufferedReader lineRead = new BufferedReader(reader);
         String line = null;
         String match = "";
         String paragraph = "";
         // reads the content of each word file to collect paragraph information
         while ((line = lineRead.readLine()) != null) 
         {
           File para = new File(MakeMenuB.getUserPath() + File.separator + "Paragraphs"+ File.separator
               +line+".txt");
           FileReader paraReader = new FileReader(para);
           BufferedReader paragraphRead = new BufferedReader(paraReader);
           match =  "\n" + line + "\n";
           
           //gets the paragraph for each saved entry for each word
           while ((paragraph = paragraphRead.readLine()) != null)
             match = match + paragraph;
           found.add(match);
           // resets each string
           match = "";
           paragraph = "";
           line = null;
           paragraphRead.close();
         }
         lineRead.close();
       } 
     }
   }
     catch (FileNotFoundException e) {}
     catch (IOException e)  {}
     
     // deletes duplicates
     String [] remove = new String[found.size()];
     found.toArray(remove);
     Set<String> mySet = new HashSet<String>(Arrays.asList(remove));
     ArrayList<String> last = new ArrayList<String>();
     last.addAll(mySet);
     return last;
   }
}