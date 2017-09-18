package user_GUI;
import javax.swing.*;

import user_Search.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * Constructs the page for the search results
 * @author Team 11
 *
 */
public class Search_Result implements ActionListener {
  
  JButton Search;
  JButton clear;
  JButton home;
  JPanel panel;
  JLabel label;
  JTextArea text;
  JScrollPane scrollPaneCreating;
  private String searchtype;
  private final MainFrame mainframe; 
  private String search;
  /**
   * Purpose: Constructor that holds the user search results.
   * @param mainframe mainframe JFrame that is the home frame
   * @param result the string of formatted results
   * @param buttontype the radio button pressed
   */
  public Search_Result(final MainFrame mainframe, String result, String buttontype)
  {
    this.searchtype = buttontype;
    this.search = result;
    this.mainframe = mainframe;
   
    //construct the main frame
    mainframe.setTitle("Guten Search");
    
    mainframe.setLayout(new FlowLayout());


    searchtype = buttontype;
    label = new JLabel("Guten Search");
    label.setFont(new Font("Serif", Font.ITALIC, 30));
    label.setForeground(Color.BLACK);

    //construct the buttons
    Search = new JButton("Search");
    clear = new JButton("Clear");
    home = new JButton("Home");
    text = new JTextArea(1, 55);

    //add basic components
    mainframe.add(label, BorderLayout.CENTER);
    mainframe.add(text, BorderLayout.WEST);
    mainframe.add(Search, BorderLayout.WEST);
    mainframe.add(clear, BorderLayout.EAST);
    mainframe.add(home, BorderLayout.EAST);
    //construct the panel to show result data
    construct_results(search);
    
    Search.removeActionListener(this);
    mainframe.setVisible(true);

    //add action Listener
    Search.addActionListener(this);
    clear.addActionListener(this);
    home.addActionListener(this);

  }
  /*****************************************************************
   * Responsible for handling actions associated with each button.
   * 
   * @param event the button pressed
   *****************************************************************/
  public void actionPerformed(ActionEvent event)
  {
    String command;
    String result;
    command = event.getActionCommand();

    //clear the panel and text box
    if (command.equals("Clear")) 
    {
      text.setText("");
      mainframe.remove(scrollPaneCreating);
      mainframe.revalidate();
      mainframe.repaint();
    }

    //construct new results 
    else if (command.equals("Search"))
    {
      mainframe.remove(scrollPaneCreating);
      result = text.getText(); // get text from box
      construct_results(result); // make new panel with new results
      mainframe.revalidate();
      mainframe.repaint();
    }
    else if (command.equals("Home"))
    {
      //Replacement for the Destroy the JFrame object
      mainframe.getContentPane().removeAll();
      mainframe.revalidate();
      mainframe.repaint();

      // Guten_Home home = new Guten_Home();
      new Guten_Home(mainframe);
    }
  }
  /*****************************************************************
   * Constructs a panel with the found results for displaying.
   * @param result  the query user enters
   *****************************************************************/
  public void construct_results(String result)
  {
    search answer = new search();
    ArrayList<String> check = new ArrayList<String>();
    if (searchtype.equals("or"))
      check = answer.searchResults(result);
    else if (searchtype.equals("and"))
      check = answer.PrepareAnd(result);
    else if (searchtype.equals("prefix"))
      check = answer.fixBuilder(result, true);
    else if (searchtype.equals("suffix"))
      check = answer.fixBuilder(result, false);
    int grid = check.size();

    panel = new JPanel(new GridLayout((grid*2),1));
    panel.setBackground(Color.LIGHT_GRAY);

    scrollPaneCreating = new JScrollPane(panel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    scrollPaneCreating.setMinimumSize(new Dimension(900, 605));
    scrollPaneCreating.setPreferredSize(new Dimension(900, 605));
    scrollPaneCreating.setBackground(Color.LIGHT_GRAY);
    for (String item : check)
    {
      item = item.replace("\n", "<br>");
      String atr = "<mark>" + result + "</mark>";
      item = item.replaceAll(result, atr);


      JLabel display = new JLabel("<html>"+item+"</html>");
      display.setFont(new Font("Serif", Font.ITALIC, 20));
      display.setForeground(Color.BLACK);
      panel.add(display);
      panel.setPreferredSize(new Dimension(880, panel.getPreferredSize().height + 600));
    }
    mainframe.add(scrollPaneCreating,BorderLayout.SOUTH);
  }
}
