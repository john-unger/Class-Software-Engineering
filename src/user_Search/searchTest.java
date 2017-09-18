package user_Search;

import static org.junit.Assert.*;

import admin_GUI.Add_Text;
import admin_GUI.MainFrame;
import admin_Index.Index;
import user_GUI.MakeMenuB;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import org.junit.Test;


/**
 * This searchTest, is a JUnit test class for the search.java class. 
 * 
 * @author Rebecca Andrews
 *
 */
public class searchTest
{

	/**
	 * This test will open up two Document selection windows. 
	 * For the first choice, select CSHaiku.txt. 
	 * For the second choice, select the folder that contains the Index folder.
	 */
	@Test
	public void searchResults_SingleWord()
	{
		// Variables to add the text file
		ActionEvent  indexEvent;
		Add_Text     add;
		File         indexFile;
		Index        index;
		MainFrame    mainframe;

		// Variables to set the user path
		ActionEvent  searchEvent;
		MakeMenuB    mmb;

		// Variables related to searchResults()
		ArrayList<String> results;
		search		 find;
		String       format;

		mainframe   = new MainFrame();
		add         = new Add_Text(mainframe);
		indexFile   = new File("C:\\Users\\Rebecca\\Desktop\\CSHaiku.txt");
		index       = new Index(indexFile);
		indexEvent  = new ActionEvent(indexFile, 0, "Index File(s)");

		mmb         = new MakeMenuB();
		searchEvent = new ActionEvent(indexFile, 0, "Change User Load Location");

		find        = new search();
		results     = new ArrayList<String>();
		format      = "<strong><font size=\"6\">CS Haiku </font></strong>(Paragraph: 3)\n" + 
				"<p>Today it is not working.</p>";


		// Add the text file
		add.actionPerformed(indexEvent);
		index.saveText();
		index.indexFile();

		// Set the user path
		mmb.actionPerformed(searchEvent);

		// Run method
		results = find.searchResults("today");
		assertEquals(format, results.get(0));

	}


	/**
	 * This test will open up two Document selection windows.
	 * For the first choice, select CSHaiku.txt.
	 * For the second choice, select the folder that contains the Index folder.
	 */
	@Test
	public void prepareAnd_MultipleWords()
	{	
		// Variables to add the text file
		Add_Text     add;
		File         haikuFile;
		Index        haikuIndex;
		MainFrame    mainframe;

		// Variables to set the user path
		ActionEvent  searchEvent, haikuEvent;
		MakeMenuB    mmb;

		// Variables related to searchResults()
		ArrayList<String> results;
		search		 find;
		String       format;


		mainframe    = new MainFrame();
		add          = new Add_Text(mainframe);

		haikuFile    = new File("C:\\Users\\Rebecca\\Desktop\\CSHaiku.txt");
		haikuIndex   = new Index(haikuFile);		
		haikuEvent   = new ActionEvent(haikuFile, 0, "Index File(s)");

		mmb          = new MakeMenuB();
		searchEvent    = new ActionEvent(haikuFile, 0, "Change User Load Location");

		find        = new search();
		results     = new ArrayList<String>();
		format      = "\nCS Haiku 4\nCoding is like that.";


		// Add the text files
		add.actionPerformed(haikuEvent);
		haikuIndex.saveText();
		haikuIndex.indexFile();

		// Set the user path
		mmb.actionPerformed(searchEvent);

		// Run method
		results = find.PrepareAnd("that is");
		assertEquals(format, results.get(0));

	}

	/**
	 * This test will open up two Document selection windows. 
	 * For the first choice, select Gingerbread.txt. 
	 * For the second choice, select the folder that contains the Index folder.
	 */
	@Test
	public void fixBuilder_Prefix()
	{		
		// Variables to add the text file
		ActionEvent  indexEvent;
		Add_Text     add;
		File         indexFile;
		Index        index;
		MainFrame    mainframe;

		// Variables to set the user path
		ActionEvent  searchEvent;
		MakeMenuB    mmb;

		// Variables related to searchResults()
		ArrayList<String> results;
		search		 find;
		String       format;

		mainframe   = new MainFrame();
		add         = new Add_Text(mainframe);
		indexFile   = new File("C:\\Users\\Rebecca\\Desktop\\Gingerbread.txt");
		index       = new Index(indexFile);
		indexEvent  = new ActionEvent(indexFile, 0, "Index File(s)");

		mmb         = new MakeMenuB();
		searchEvent = new ActionEvent(indexFile, 0, "Change User Load Location");

		find        = new search();
		results     = new ArrayList<String>();
		format      = "\nThe Little Gingerbread Man 2\nOnce upon a time there was an old " + 
				"woman who loved baking gingerbread. She would bake gingerbread " + 
				"cookies, cakes, houses and gingerbread people, all decorated with " + 
				"chocolate and peppermint, caramel candies and colored frosting.";


		// Add the text file
		add.actionPerformed(indexEvent);
		index.saveText();
		index.indexFile();

		// Set the user path
		mmb.actionPerformed(searchEvent);

		// Run method
		results = find.fixBuilder("gingerbread", true);

		assertEquals(format, results.get(0));
	}


	/**
	 *  This test will open up two Document selection windows. 
	 *  For the first choice, select Gingerbread.txt. 
	 *  For the second choice, select the folder that contains the Index folder.
	 */
	@Test
	public void fixBuilder_Suffix()
	{				
		// Variables to add the text file
		ActionEvent  indexEvent;
		Add_Text     add;
		File         indexFile;
		Index        index;
		MainFrame    mainframe;

		// Variables to set the user path
		ActionEvent  searchEvent;
		MakeMenuB    mmb;

		// Variables related to searchResults()
		ArrayList<String> results;
		search		 find;
		String       format;

		mainframe   = new MainFrame();
		add         = new Add_Text(mainframe);
		indexFile   = new File("C:\\Users\\Rebecca\\Desktop\\Gingerbread.txt");
		index       = new Index(indexFile);
		indexEvent  = new ActionEvent(indexFile, 0, "Index File(s)");

		mmb         = new MakeMenuB();
		searchEvent = new ActionEvent(indexFile, 0, "Change User Load Location");

		find        = new search();
		results     = new ArrayList<String>();
		format      = "\nThe Little Gingerbread Man 2\nOnce upon a time there was an old " + 
				"woman who loved baking gingerbread. She would bake gingerbread " + 
				"cookies, cakes, houses and gingerbread people, all decorated with " + 
				"chocolate and peppermint, caramel candies and colored frosting.";


		// Add the text file
		add.actionPerformed(indexEvent);
		index.saveText();
		index.indexFile();

		// Set the user path
		mmb.actionPerformed(searchEvent);

		// Run method
		results = find.fixBuilder("gingerbread", false);

		assertEquals(format, results.get(0));
	}
}
