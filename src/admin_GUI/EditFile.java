package admin_GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

import admin_Index.*;
/**
 * Gui class for handling the editing of files.
 * @author Team 11
 *
 */
public class EditFile implements ActionListener
{
  private JLabel lbl;        // the label of each corresponding TextArea
  private JButton button;        // the apply button to save the changes
  Index bookBeingChanged;
  private final MainFrame mainframe;
  JTextField[] txt = {new JTextField(), new JTextField()}; // FIELDS is an int, representing the max number of JTextFields
  String[] attributeTypes = {"Title", "Author"};
  String[] attributeValues = {"This is", "a Test"};

  /**
   * One-arg constructor, calls each of the build panel methods,
   * and adds them accordingly to the book referenced
   * 
   * @param mainframe the main JFrame for the program
   * @param bk the index object 
   */
  public EditFile(final MainFrame mainframe, Index bk)
  {
    this.mainframe = mainframe;
    int index;
    bookBeingChanged = bk;
    GridBagConstraints gbc = new GridBagConstraints();

    // Set the window title.
    mainframe.setTitle("Edit Book Attributes");

    // Add a BorderLayout manager to the content pane.
    mainframe.setLayout(new GridBagLayout());
    mainframe.getContentPane().setForeground(Color.BLACK);

    attributeValues[0] = bookBeingChanged.getTitle();
    attributeValues[1] = bookBeingChanged.getAuthor();

    // Add the panels to the content pane.
    for (index = 0; index < attributeTypes.length; index++)
    {
      gbc.gridx = 0;
      gbc.gridy = index;

      mainframe.add(buildLabelPanel(attributeTypes[index]), gbc);

      gbc.gridx = 1;
      mainframe.add(buildTextPanel(attributeValues[index], index), gbc);

      // Make a blank panel
      JPanel pn = new JPanel();
      pn.setBackground(Color.LIGHT_GRAY);
      mainframe.add(pn);
    }

    gbc.insets = new Insets(15, 2, 2, 2);
    gbc.gridx = 1;
    gbc.gridy = (index++);
    mainframe.add(buildApplyButtonPanel(), gbc);

    gbc.gridx = 0;
    mainframe.add(buildCancelButtonPanel(), gbc);

    // Pack and display the window.
    // pack();
    mainframe.setVisible(true);
  }
  /**
   * Purpose: Builds a cancel button panel
   * @return the built jpanel
   */
  private JPanel buildCancelButtonPanel()
  {
    // Create a JPanel object, called panel
    JPanel panel = new JPanel();
    panel.setBackground(Color.LIGHT_GRAY);


    // Create the text field, and then set the text to what was passed
    button = new JButton();
    button.setText("Cancel");

    // Make a listener
    //button.addActionListener(new ButtonListener(mainframe));
    button.addActionListener(this);
    // Add the text field to the field
    panel.add(button);

    return panel;
  }
  /**
   * Builds a panel that holds the apply button for editing books
   * @return panel containing the apply button.
   */
  private JPanel buildApplyButtonPanel()
  {
    // Create a JPanel object, called panel
    JPanel panel = new JPanel();
    panel.setBackground(Color.LIGHT_GRAY);

    
    // Create the text field, and then set the text to what was passed
    button = new JButton();
    button.setText("Apply");
    
    // Make a listener
    //button.addActionListener(new ButtonListener(mainframe));
    button.addActionListener(this);
    // Add the text field to the field
    panel.add(button);

    return panel;
  }

  /**
   * This buildTextPanel method initializes and returns a panel,
   * with a text box, that already has the text in it that was passed as an arg.
   * 
   * @param str the String that will be in the text box originally
   * @return JPanel with a text box for the user to enter new values into
   */
  private JPanel buildTextPanel(String str, int i)
  {
    // Create a JPanel object, called panel
    JPanel panel = new JPanel();
    panel.setBackground(Color.LIGHT_GRAY);

    // Create the text field, and then set the text to what was passed
    txt[i].setText(str);

    txt[i].setPreferredSize(new Dimension( 200, 24 ));;

    // Add the text field to the field
    panel.add(txt[i]);

    return panel;
  }

  /**
   * This buildLabelPanel method initializes and returns a panel,
   * with a label, that specifies what kind of info that is (going to be next to it).
   * Setting the text to what was passed.
   * @param str the String that will be inside the label
   * @return JPanel with a label that informs the user of the type of attribute
   */
  private JPanel buildLabelPanel(String str)
  {
    // Create a JPanel object, called panel
    JPanel panel = new JPanel();

    panel.setBackground(Color.LIGHT_GRAY);
    panel.setForeground(Color.BLACK);

    // Create the text field with the desired length.
    lbl = new JLabel(str +": ");

    lbl.setForeground(Color.BLACK);
    // Add the text field to the field
    panel.add(lbl);

    return panel;
  }
  public void actionPerformed(ActionEvent event)
  {
    switch(event.getActionCommand().toString())
    {
    case "Apply":
      String ti = txt[0].getText();
      String ath = txt[1].getText();
      bookBeingChanged.updateTA(ti, ath);

      // -----------------------------------------------------------
      // -----------------------------------------------------------
      ( new Thread(new RunBook(bookBeingChanged.getFile())) ).start();
      // -----------------------------------------------------------
      // -----------------------------------------------------------
      mainframe.getContentPane().removeAll();
      mainframe.revalidate();
      mainframe.repaint();
      new Add_Text(mainframe);
    case "Cancel":
      mainframe.getContentPane().removeAll();
      mainframe.revalidate();
      mainframe.repaint();
      new Add_Text(mainframe);
      break;
    }
  }
  /**
   * Purpose:  The prepares to index changed data
   * @author Team 11
   *
   */
  private class RunBook implements Runnable {
    File file;
    public RunBook (File file){
      this.file = file;
    }
    /**
     * Purpose: Index the new file
     */
    public void run(){
      Index in = new Index(file);
      if (in.saveText()){
        in.indexFile();
      }
    }}
}
