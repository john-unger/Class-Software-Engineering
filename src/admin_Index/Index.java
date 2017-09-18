package admin_Index;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import admin_GUI.MakeMenuB;

import static java.nio.file.StandardCopyOption.*;

/**
 * Responsible for indexing each text file. Breaks each file
 * into paragraphs and each paragraph into words and saves information
 * in appropriate directory for later retrieval
 *
 * @author Team 11
 *
 */

public class Index
{
  private File file;
  final int lines2Read = 20;
  /**
   * Purpose Constructs the index file
   * @param file the file to be indexed
   */
  public Index(File file)
  {
    setFile(file);
  }
  /**
   * Purpose: Responsible for handling the updating of text books
   * @param title the title to be set
   * @param author the author to be set
   * @return false if no author or title is specified
   */
  public boolean updateTA(String title, String author)
  {
    if (title.isEmpty() && author.isEmpty())
    {
      return false;
    }
    else
    {
      setAuthor(author);
      setTitle(title);
      return true;
    }
  }

  /**
   * Purpose: Returns the books indexed
   * @return the indexed books
   */
  public ArrayList<String> getTAofBook()
  {
    ArrayList<String> ret = null;

    try
    {
      ret = new ArrayList<String>();
      ret.add(getTitle());
      ret.add(getAuthor());
    }
    catch (Exception e) {e.printStackTrace();}

    return ret;

  }
  /**
   * method makes the books directory if it doesn't exist and
   * adds the full text file if it already doesn't exist
   *
   * @return a boolean indicating if the book has already been
   * added or not
   */
  public boolean saveText()
  {
    //make the books directory if it doesn't exist
    File theDir = new File(MakeMenuB.getAdminPath() + File.separator+ "Books");
    
    if (!theDir.exists())
    {
      theDir.mkdir();
    }
    
    // make the file for the book and return false if it exists
    File outputFile = new File(MakeMenuB.getAdminPath()  + File.separator + "Books"+ File.separator + 
        getTitle()+".txt");
    
    try
    {
      Files.copy(getFile().toPath(), outputFile.toPath());
    }
    catch (IOException e)
    {
      return false;
    }
    return true;
  }

  /**
   * Breaks each file into paragraphs and calls appropriate methods
   * for putting them into words.
   */
  public void indexFile()
  {
    int ordinalNumber = 0;
    String paragraph = "";
    String bookLine = "";
    // open the file that is being indexed
    try
    {
      FileReader fileReader = new FileReader(getFile());
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      bookLine = bufferedReader.readLine();
      while(bookLine != null)
      {
        if(bookLine.isEmpty())
        {
          // paragraph has ended and needs be broken into words
          if(!paragraph.isEmpty())
          {
            ordinalNumber++;
            makeWords(ordinalNumber,paragraph);
            makeParagraphs(paragraph,ordinalNumber);
            paragraph = "";
          }
          bookLine = bufferedReader.readLine();
        }
        // add the next line read to the current paragraph
        else
        {
          paragraph = paragraph + bookLine+"\n";
          bookLine = bufferedReader.readLine();
          // gets the last paragraph of text
          if (bookLine == null)
          {
            ordinalNumber++;
            makeWords(ordinalNumber,paragraph);
            makeParagraphs(paragraph,ordinalNumber);
          }
        }
      }
      bufferedReader.close();
    }
    catch(FileNotFoundException ex)
    {
      System.out.println("Unable to find file");
    }
    catch(IOException ex)
    {
      System.out.println("Error reading file");
    }
  }
  /**
   * Purpose: Responsible for making text files for each word.
   * @param number the ordinal number of the paragraph
   * @param paragraph the paragraph
   */
  public void makeWords(int number, String paragraph)
  {
    File theDir = new File(MakeMenuB.getAdminPath()  + File.separator + "Index");
    if (!theDir.exists())
      theDir.mkdir();
    ArrayList<String> avoidDup = new ArrayList<String>();
    String[] words = paragraph.split("[\\W]");
    for (String word: words)
    {
      if (!avoidDup.contains(word))
        try
      {
          avoidDup.add(word);
          word = word.toLowerCase();
          File file = new File(MakeMenuB.getAdminPath()  + File.separator + "Index" + File.separator 
              + word +".txt");
          FileWriter filewriter;
          filewriter = new FileWriter(file, true);
          BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
          bufferedWriter.write(getTitle() + " " + Integer.toString(number));
          bufferedWriter.newLine();
          bufferedWriter.close();
          filewriter.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    avoidDup.clear();
  }
  /**
   * Responsible for making each paragraphs for each book having the
   * title as name of book and ordinal number for retrieval.
   *
   * @param paragraph the paragraph to be stored
   * @param number the ordinal number
   */
  public void makeParagraphs(String paragraph, int number)
  {
    // make a paragraph directory if it does not already exist
    File paragraphsDir = new File(MakeMenuB.getAdminPath()  + File.separator + "Paragraphs");
    if (!paragraphsDir.exists())
      paragraphsDir.mkdirs();

    //make a new text file with name of book and ordinal number and save the paragraph
    File para = new File(MakeMenuB.getAdminPath()  + File.separator + "Paragraphs" + File.separator
        + getTitle() + " " + Integer.toString(number)+".txt");
    FileWriter filewriter;
    try
    {
      filewriter = new FileWriter(para);
      BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
      bufferedWriter.write(paragraph);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Purpose: getter method that returns the file.
   * @return the file
   */
  public File getFile() 
  {
    return file;
  }
  /**
   * Purpose: the setter method for the file.
   * @param file the file to set
   */
  private void setFile(File file) 
  {
    this.file = file;
  }
  /**
   * Purpose: getter method for the title.
   * @return the title
   */
  public String getTitle() 
  {
    BufferedReader br = null;
    String ret = "";

    try
    {
      br = new BufferedReader( new FileReader(getFile()));
      String strLine;
      while( ( strLine = br.readLine()) != null)
      {
        if(strLine.startsWith("Title: ")) 
          ret = strLine.substring(7);
      }
      br.close();
    }
    catch (FileNotFoundException e) 
    {
      System.err.println("Unable to find the file: " + getFile().getName());
    }
    catch (IOException e) 
    {
      System.err.println("Unable to read the file: " + getFile().getName());
    }
    return ret;
  }
  /**
   * Purpose: Sets the title.
   * @param title the title to set
   */
  private void setTitle(String title) 
  {
    try
    {
      File file = getFile();
      if (file.canWrite())
      {
        String atr = "Title: " + title;

        PrintWriter writer = new PrintWriter("temp.txt");

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        Boolean flg = false;

        String strLine;
        while( (strLine = br.readLine()) != null)
        {
          if (strLine.startsWith("Title: "))
          { 
            writer.println(atr);
            flg = true;
          }
          else
          {
            writer.println(strLine);
          }
        }
        if(!flg)
        {
          writer.println(atr);
        }
        br.close();
        fr.close();

        Files.copy(Paths.get("temp.txt"), getFile().toPath(), REPLACE_EXISTING);
        writer.flush();
        writer.close();
        Files.delete(Paths.get("temp.txt"));
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } }
  /**
   * Purpose: getter method that returns the author
   * @return the author
   */
  public String getAuthor()
  {
    BufferedReader br = null;
    String ret = null;

    try
    {
      br = new BufferedReader( new FileReader(getFile()));
      String strLine;
      while( ( strLine = br.readLine()) != null)
      {
        if (strLine.startsWith("Author: ")) ret = strLine.substring(8);
      }
      
      br.close();
    }
    catch (FileNotFoundException e) {System.err.println("Unable to find the file: " + getFile().getName());}
    catch (IOException e) {System.err.println("Unable to read the file: " + getFile().getName());}
    
    return ret;
  }


  /**
   * Purpose: Sets the author for the file
   * @param author the author to set
   */
  private void setAuthor(String author)
  {
    try
    {
      File file = getFile();
      if (file.canWrite())
      {
        String atr = "Author: " + author;

        PrintWriter writer = new PrintWriter("temp.txt");

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        Boolean flg = false;


        String strLine;
        while( (strLine = br.readLine()) != null)
        {
          if (strLine.startsWith("Author: "))
          { 
            writer.println(atr);
            flg = true;
          }
          else
          {
            writer.println(strLine);
          }
        }
        if(!flg)
        {
          writer.println(atr);
        }

        br.close();
        fr.close();

        Files.copy(Paths.get("temp.txt"), getFile().toPath(), REPLACE_EXISTING);
        writer.flush();
        writer.close();
        Files.delete(Paths.get("temp.txt"));
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}