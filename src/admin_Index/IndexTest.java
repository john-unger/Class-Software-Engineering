package admin_Index;

import static org.junit.Assert.*;
import admin_GUI.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

/**
 * This IndexTest, is a JUnit test class for the Index.java class. 
 * 
 * @author Rebecca Andrews
 *
 */

public class IndexTest
{
	@Test
	public void constructor_SetFile()
	{
		File   file;
		Index  index;
		
		// Hardcoded location
		file   = new File("C:\\Users\\Rebecca\\Deskop\\NewFile.txt");
		index  = new Index(file);
		
		assertTrue(file.equals(index.getFile()));
	}
	
	@Test
	public void saveText_NewFile()
	{
		// This test only works the first time the test suite is run.
		
		File   file;
		Index  index;
		
		// Hardcoded location
		file   = new File("C:\\Users\\Rebecca\\Desktop\\NewFile.txt");
		index = new Index(file);
		
		assertTrue(index.saveText());
	}
	
	@Test
	public void saveText_ExistingFile()
	{
		File   file;
		Index  index;
		
		// Hardcoded location
		file   = new File("C:\\Users\\Rebecca\\Desktop\\ExistingFile.txt");
		index = new Index(file);
		index.saveText();
		assertFalse(index.saveText());
	
	}
	
	/**
	 * To run a second (or further) time, 
	 * replace UpdateTitleAuthor.txt with UpdateTitleAuthor2.txt
	 */
	@Test
	public void updateTA_Invalid()
	{
	
		
		String  author, title;
		File    file;
		Index   index;
		
		author = "";
		title = "";
		
		//Hardcoded location
		file   = new File("C:\\Users\\Rebecca\\Desktop\\UpdateTitleAuthor.txt");
		index = new Index(file);
		index.saveText();
		
		assertFalse(index.updateTA(title, author));
	}
	
	@Test
	public void updateTA_Valid()
	{
		String  author, title;
		File    file;
		Index   index;
		
		// Hardcoded location
		file   = new File("C:\\Users\\Rebecca\\Desktop\\UpdateTitleAuthor.txt");
		index = new Index(file);
		index.saveText();
		
		author = "Dr. Bernstein";
		title = "CS 345";
		
		assertTrue(index.updateTA(title, author));
	}
	
	
	@Test
	public void getTAofBook_Valid()
	{
		ArrayList<String> ta;
		File   file;
		Index  index;
		
		ta     = new ArrayList<String>();
		file   = new File("C:\\Users\\Rebecca\\Desktop\\GetTitleAuthor.txt");
		index  = new Index(file);
		
		index.saveText();
		
		ta = index.getTAofBook();
		
		assertEquals("CS345", ta.get(0));
		assertEquals("Dr. Bernstein", ta.get(1));
	}
	
	@Test
	public void getTitle_InvalidFile()
	{
		File file;
		Index index;
		
		file = new File("DoesNotExist");
		index = new Index(file);
		index.saveText();
		
		assertEquals(index.getTitle(), "");
	}
	
	@Test
	public void getTitle_InvalidDocumentType()
	{
		File   file;
		Index  index;
		
		// Hardcoded location
		file   = new File("C:\\Users\\Rebecca\\Desktop\\InvalidDocumentType.rtf");
		index = new Index(file);
		index.saveText();
		
		assertEquals(index.getTitle(), "");	
	}
	
	@Test
	public void getAuthor_InvalidFile()
	{
		File file;
		Index index;
		
		file   = new File("C:\\Users\\Rebecca\\Desktop\\InvalidDocumentType.rtf");
		index = new Index(file);
		index.saveText();
		
		assertNull(index.getAuthor());
	}
	
	
	//Still working on this.
	
	/**
	 * This test will open up a Document selection window. 
	 * Select IndexTest.txt. 
	 * This test will only work the first time the test suite is run. 
	 * To run the test suite again, delete the IndexTest.txt file from the Books folder.
	 */
	@Test 
	public void indexFile_Valid()
	{		
		ActionEvent  event;
		Add_Text     add;
		File         indexFile, wordFile;
		Index        index;
		MainFrame    mainframe;
		
		indexFile  = new File("C:\\Users\\Rebecca\\Desktop\\IndexTest.txt");
		event      = new ActionEvent(indexFile, 0, "Index File(s)");
		mainframe  = new MainFrame();
		add        = new Add_Text(mainframe);
		
		add.actionPerformed(event);
		index = new Index(indexFile);
		index.saveText();
		
		index.indexFile();
		
		wordFile   = new File("C:\\Users\\Rebecca\\Documents\\Academics\\College\\3 - Junior Year\\CS 345\\Sprints\\Index\\here.txt");
		assertTrue(wordFile.exists());
		
	}

}
