package user_GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * Builds the menu bars for the user and admin
 * @author Team 11
 *
 */
public class MakeMenuB implements ActionListener {

  private JMenuBar menuBar;
  static String ADMIN_PATH = "";
  static String USER_PATH = "";
  private JFileChooser jfcUser;
  
  /**
   * Constructs the menu bar 
   * @return the constructed menu bar
   */
  public JMenuBar makeMenu()
  {
    menuBar = new JMenuBar();

    // build the File menu
    JMenu fileMenu = new JMenu("File");
    JMenuItem exportMenuItem = new JMenuItem("Change User Load Location");
    exportMenuItem.addActionListener(this);
    fileMenu.add(exportMenuItem);

    JMenu helpMenu = new JMenu("Help");
    JMenuItem userMenuItem = new JMenuItem("User Guide");
    helpMenu.add(userMenuItem);
    userMenuItem.addActionListener(this);
    
    if (USER_PATH.equals(""))
    {
      USER_PATH = new File(System.getProperty("user.dir")).getParent();
    }

    // add menus to menubar
    menuBar.add(fileMenu);
    menuBar.add(helpMenu);
    return menuBar;
  }
  /**
   * Purpose: Handles the button pressed events
   * @param e the event that is triggered
   */
  public void actionPerformed(ActionEvent e) {

    String command= "";
    command = e.getActionCommand();
    
    jfcUser = new JFileChooser(new java.io.File(USER_PATH));
    jfcUser.setDialogTitle("Choose where to load your .txt's from");
    jfcUser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    jfcUser.setAcceptAllFileFilterUsed(false);
    
    if (command.equals("Change User Load Location"))
    {
      if (jfcUser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
      {
        USER_PATH = jfcUser.getSelectedFile().getAbsolutePath();
      }
    }
    else if (command.equals("User Guide"))
    {
      new Construct_Help();
    }
  }
  /**
   * Purpose: Getter method that returns the path for the user
   * @return the path for the user
   */
  public static String getUserPath()
  {
    return USER_PATH;
  }
}
