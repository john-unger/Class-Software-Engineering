package admin_GUI;
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
  private JFileChooser jfcAdmin;
  /**
   * Constructs the menu bar 
   * @return the constructed menu bar
   */
  public JMenuBar makeMenu()
  {
    menuBar = new JMenuBar();

    // build the File menu
    JMenu fileMenu = new JMenu("File");
    JMenuItem openMenuItem = new JMenuItem("Change Admin SaveTo Location");
    openMenuItem.addActionListener(this);
    fileMenu.add(openMenuItem);

    if (ADMIN_PATH.equals(""))
    {
      ADMIN_PATH = new File(System.getProperty("user.dir")).getParent();
    }
    
    // add menus to menubar
    menuBar.add(fileMenu);
    
    return menuBar;
  }
  /**
   * Purpose: Handles the button pressed events
   * @param e the event that is triggered
   */
  public void actionPerformed(ActionEvent e) 
  {
    String command= "";
    command = e.getActionCommand();
    
    jfcAdmin = new JFileChooser(new java.io.File(ADMIN_PATH));
    jfcAdmin.setDialogTitle("Choose where to save your .txt's at");
    jfcAdmin.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    jfcAdmin.setAcceptAllFileFilterUsed(false);
    
    if (command.equals("Change Admin SaveTo Location"))
    {
      if (jfcAdmin.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
      {
        ADMIN_PATH = jfcAdmin.getSelectedFile().getAbsolutePath();
      }
    }
  }
  /**
   * Purpose: Getter method that returns the path for the admin
   * @return the path for the admin
   */
  public static String getAdminPath()
  {
    return ADMIN_PATH;
  }
}
