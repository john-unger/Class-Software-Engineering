package user_GUI;
import java.awt.*;
import javax.swing.*;
/*****************************************************************
* Class that is responsible for constructing a help menu
* 
*****************************************************************/
public class Construct_Help extends JFrame {

  private static final long serialVersionUID = 1L;
  JLabel andSearch, asterisk, changeLoad, options, orSearch, phrase,
         prefix, question, search, specialChar, suffix, title, loadhelp;
  
  /*****************************************************************
  * Constructs the GUI for the help menu
  * 
  *****************************************************************/
  public Construct_Help()
  {
    super("User Guide");

    String fontColor, header, tab;
    fontColor = "<font color='000000'>";
    header = "<html>" + fontColor + "<font size='4'>";
    tab = "&#8195";
    
    setLayout(new BorderLayout());
    getContentPane().setBackground(Color.LIGHT_GRAY);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    
    title = new JLabel("<html>" + fontColor + "<font face='Times New Roman' size ='6'>"
            + "<i>GutenSearch Search Tools</i><br><br></html>");
    
    search = new JLabel("<html>" + fontColor + "<font size='4'>To perform a search,"
        + " type the terms you wish to search for into the text field<br>and press"
        + " the search button to the right.</font><br><br></html>");
    
    changeLoad = new JLabel("<html>" + fontColor + "<font size='4'>To change the index"
        + " loading location, press the \"User Load Location\" button<br>and select"
        + " the appropriate location.<br><br></html>");
    
    options = new JLabel("<html><br><br>" + fontColor + "<font size='5'>Search Options"
        + "</font><br><br></html>");
    
    orSearch = new JLabel(header + "<u>OR</u></font><br>" + tab + "An OR search will only"
        + " return results that contain at least one of the terms in the search query."
        + "<br>" + tab + "NOTE: This is the default search option.</font><br><br></html>");
    
    andSearch = new JLabel(header + "<u>AND</u></font><br>" + tab + "An AND search will"
        + " only return results that contain all of the terms in the search query."
        + "</font><br><br></html>");
    
    prefix = new JLabel(header + "<u>Prefix</u></font><br>" + tab + "A prefix search will"
        + " return results that contain words that begin with the search query.</font>"
        + "<br><br></html>");
    
    suffix = new JLabel(header + "<u>Suffix</u></font><br>" + tab + "A suffix search will "
        + "return results that contain words that end with the search query.</font><br>"
        + "<br></html>");

    loadhelp = new JLabel(header + "<u>IMPORTANT INFO</u></font><br>" + tab + "NOTE, the user must "
        + "select a file location to load from before searching and the admin must select the "
        + "file location to save the index.</font><br>"
        + "<br></html>");
    // Construct the center panel
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setForeground(Color.BLACK);
    centerPanel.setBackground(Color.LIGHT_GRAY);
    centerPanel.add(search);
    centerPanel.add(changeLoad);
    centerPanel.add(options);
    centerPanel.add(orSearch);
    centerPanel.add(andSearch);
    centerPanel.add(prefix);
    centerPanel.add(suffix);
    centerPanel.add(loadhelp);

    
    JScrollPane scrollPane = new JScrollPane(centerPanel);

    // Lays out the text on the gui
    add(title,BorderLayout.NORTH);
    add(scrollPane,BorderLayout.CENTER);
    
    setSize(600, 775);
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
