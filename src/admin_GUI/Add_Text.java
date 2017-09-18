package admin_GUI;
import java.awt.*;
import admin_Index.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * Purpose:  The class is responsible for building the gui for adding text 
 * and being able to connect to the editing of existing books
 * 
 * @author Team 11
 *
 */

public class Add_Text implements ActionListener {
  File[] files;
  File[] filesInDirectory;
  JButton Browse_Text, Submit, Home, Existing, Directory_add;
  JTextArea Text_title, Text_author;
  JPanel panel1, panel2, panel3, panel4, panel5;
  final JFileChooser fc = new JFileChooser();
  File file = null;
  Boolean multFiles = false;
  private final MainFrame mainframe;
  
  /**
   * Constructs the main GUI for the Admin home page
   * @param mainframe the main jframe used for the program
   */
  public Add_Text(final MainFrame mainframe)
  {
    this.mainframe = mainframe;

    mainframe.setTitle("Adding new text Files");

    // put the menubar on the frame
    mainframe.setJMenuBar(new MakeMenuB().makeMenu());
    mainframe.setLayout(new GridLayout(4,1));


    Text_title = new JTextArea(1, 45);
    Text_author = new JTextArea(1, 45);
    Browse_Text = new JButton("Index File(s)");
    Directory_add = new JButton("Index Directory");
    Submit = new JButton("Submit");
    Existing = new JButton("Edit Existing");

    construct_panel();

    make_label("Guten Search Administrator", panel1);
    BufferedImage wPic;
    try
    {
      wPic = ImageIO.read(this.getClass().getResource("books.jpg"));
      JLabel wIcon = new JLabel(new ImageIcon(wPic));
      panel2.add(wIcon);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    mainframe.add(panel1);
    mainframe.add(panel2);
    JLabel space = new JLabel();
    mainframe.add(space);
    bottomPanel();
    mainframe.setVisible(true);
    Existing.removeActionListener(this);
    Browse_Text.addActionListener(this);
    Directory_add.addActionListener(this);
    Submit.addActionListener(this);
    Existing.addActionListener(this);
  }
  /**
   * Purpose: constructs the bottom panel 
   */
  public void bottomPanel()
  {
    panel4.add(Browse_Text);
    panel4.add(Directory_add);
    panel4.add(Existing);
    mainframe.add(panel4);
  }
  /**
   * Purpose: The method responsible for handling the actions
   * 
   *  @param event the event that is triggered
   */
  public void actionPerformed(ActionEvent event)
  {

    fc.setMultiSelectionEnabled(true);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
    fc.setFileFilter(filter);

    String command;
    command = event.getActionCommand();

    if (command.equals("Index File(s)"))
    {
      fc.setDialogTitle("Choose a file");
      int returnValue = fc.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION)
      {
        panel4.removeAll();
        bottomPanel();
        files = fc.getSelectedFiles();
        
        for(int i=0; i<files.length; i++)
          System.out.println(files[i].getName());
        if (files.length > 1)
        {
          multFiles = true;
        }
        for(int i=0; i<files.length; i++)
        {
          file = files[i];
          // -----------------------------------------------------------
          // -----------------------------------------------------------
          ( new Thread(new RunBook(/*mainframe,*/ file)) ).start();
          // -----------------------------------------------------------
          // -----------------------------------------------------------
        }
      }
    }
    
    if (command.equals("Index Directory"))
    {
      fc.setDialogTitle("Choose a Directory");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        // disable the "All files" option.
        fc.setAcceptAllFileFilterUsed(false);
      int returnValue = fc.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION)
      {
        panel4.removeAll();
        bottomPanel();

        file = fc.getSelectedFile();
        String fullPath = file.getAbsolutePath();
        File theDirectory = new File(fullPath);
  
        fc.setCurrentDirectory(theDirectory);
        filesInDirectory = fc.getCurrentDirectory().listFiles();
        
        if (filesInDirectory.length > 1)
        {
          multFiles = true;
        }
        for(int i=0; i<filesInDirectory.length; i++)
        {
          file = filesInDirectory[i];

          // -----------------------------------------------------------
          // -----------------------------------------------------------
          ( new Thread(new RunBook(/*mainframe,*/ file)) ).start();
          // -----------------------------------------------------------
          // -----------------------------------------------------------
        }
      }
    }
    else if (command.equals("Edit Existing"))
    {
//      mainframe.getContentPane().removeAll();
//      mainframe.revalidate();
//      mainframe.repaint();
      new Select_Book(mainframe);
    }
  }
  /**
   * Purpose: Returns a panel given a text
   * 
   * @param title the title of the component
   * @param panel the panel that will be added to
   * @return the constructed component.
   */
  public Component make_label(String title, JPanel panel)
  {

    JLabel label;
    label = new JLabel();
    label.setText(title);
    label.setFont(new Font("Serif", Font.ITALIC, 34));
    label.setForeground(Color.black);
    label.setBackground(Color.LIGHT_GRAY);
    panel.add(label, FlowLayout.LEFT);
    return panel;

  }
  /**
   * Purpose: Constructs a panel
   */
  public void construct_panel()
  {
    panel1 = new JPanel(new FlowLayout());
    panel2 = new JPanel(new FlowLayout());
    panel3 = new JPanel(new FlowLayout());
    panel4 = new JPanel(new FlowLayout());
    panel1.setBackground(Color.LIGHT_GRAY);
    panel2.setBackground(Color.LIGHT_GRAY);
    panel3.setBackground(Color.LIGHT_GRAY);
    panel4.setBackground(Color.LIGHT_GRAY);
  }
  /**
   * Purpose: Private class that makes the initial calls for editing
   * text books
   * @author Team 11
   *
   */
  private class RunBook extends MainFrame implements Runnable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    File fl;
    /**
     * Purpose: Constructs the runBook object
     * @param file the file that is to be edited
     */
    public RunBook (File file)
    {
      this.fl = file;
    }
    /**
     * Purpose: Indexes the file with changed attributes
     */
    public void run()
    {

      Index add = new Index(fl);
      if (multFiles)
      {
        if (add.saveText())
        {
          add.indexFile();
        }
      }
      else
      {
        //Replacement for the Destroy the JFrame object
        mainframe.getContentPane().removeAll();
        mainframe.revalidate();
        mainframe.repaint();
        new EditFile(mainframe, add);
      }
    }
   }
}