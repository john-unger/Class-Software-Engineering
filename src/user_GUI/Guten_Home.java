package user_GUI;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**
 * Purpose:  The class that builds the home guten page that the user sees
 * @author Team 11
 *
 */
public class Guten_Home implements ActionListener {


  JPanel panel, panel1, panel2, buttonPanel;
  JButton search, help, administrator;
  JRadioButton and, or, sufix, prefix, button;
  JLabel label;
  JTextField text;
  private final MainFrame mainframe;
  /**
   * Constructs the home page
   * @param mainframe the main jframe for the program
   */
  public Guten_Home(final MainFrame mainframe)
  {
    
    this.mainframe = mainframe;
    
    // put the menubar on the frame
    mainframe.setJMenuBar(new MakeMenuB().makeMenu());

    GridBagConstraints gbc = new GridBagConstraints();
    GridBagConstraints gbc1 = new GridBagConstraints();
    GridBagConstraints gbc2 = new GridBagConstraints();
    GridBagConstraints gbc3 = new GridBagConstraints();
    ButtonGroup functions = new ButtonGroup();
    gbc.insets = new Insets(100, 0, 0, 0);
    gbc1.insets = new Insets(100, 0,10,10);
    gbc2.insets = new Insets(10,0,0,100);
    gbc3.insets = new Insets(10,0,0,100);

    mainframe.setLayout(new FlowLayout());
    
    //constructing the panels 
    panel = new JPanel(new GridBagLayout());
    panel1 = new JPanel(new GridBagLayout());
    panel2 = new JPanel(new GridBagLayout());
    buttonPanel = new JPanel(new GridBagLayout());
    panel.setBackground(Color.LIGHT_GRAY);
    panel1.setBackground(Color.LIGHT_GRAY);
    panel2.setBackground(Color.LIGHT_GRAY);
    buttonPanel.setBackground(Color.LIGHT_GRAY);

    label = new JLabel();
    label.setText("Guten Search");
    label.setFont(new Font("Serif", Font.ITALIC, 84));
    label.setForeground(Color.BLACK);
    panel.add(label,gbc);

    text = new JTextField(55);
    
    gbc1.gridx = 1;
    gbc1.gridy = 0;
    panel1.add(text, gbc1);

    search = new JButton("Search");
    gbc1.gridx = 2;
    gbc1.gridy = 0;
    panel1.add(search, gbc1);

    and = new JRadioButton("and");
    and.setBackground(Color.LIGHT_GRAY);
    and.setForeground(Color.BLACK);
    gbc3.gridx = 1;
    gbc3.gridy = 1;
    functions.add(and);
    buttonPanel.add(and, gbc3);

    or = new JRadioButton("or");
    or.setBackground(Color.LIGHT_GRAY);
    or.setForeground(Color.BLACK);
    or.setSelected(true);
    gbc3.gridx = 0;
    gbc3.gridy = 1;
    functions.add(or);
    buttonPanel.add(or, gbc3);

    prefix = new JRadioButton("prefix");
    prefix.setBackground(Color.LIGHT_GRAY);
    prefix.setForeground(Color.BLACK);
    gbc3.gridx = 2;
    gbc3.gridy = 1;
    functions.add(prefix);
    buttonPanel.add(prefix, gbc3);

    sufix = new JRadioButton("sufix");
    sufix.setBackground(Color.LIGHT_GRAY);
    sufix.setForeground(Color.BLACK);
    gbc3.gridx = 3;
    gbc3.gridy = 1;
    functions.add(sufix);
    buttonPanel.add(sufix, gbc3);

    gbc2.gridx = 0;
    gbc2.gridy = 1;

    gbc2.gridx = 1;
    gbc2.gridy = 1;

    mainframe.add(panel, BorderLayout.NORTH);
    mainframe.add(panel1, BorderLayout.CENTER);
    mainframe.add(buttonPanel, BorderLayout.SOUTH);
    mainframe.add(panel2, BorderLayout.SOUTH);

    search.addActionListener(this);
    and.addActionListener(this);
    or.addActionListener(this);
    sufix.addActionListener(this);
    prefix.addActionListener(this);

    mainframe.setVisible(true);

  }
  /*****************************************************************
   * Purpose: Handles the actions of all buttons pressed
   * 
   * @param event the button pressed
   *****************************************************************/
  public void actionPerformed(ActionEvent event)
  {
    String command;
    command = event.getActionCommand();

    if (command.equals("Search"))
    {
      button = new JRadioButton();
      String result = text.getText();     
      //pass the result into search engine index

      if(or.isSelected()) button = or;
      else if(and.isSelected()) button = and;
      else if(sufix.isSelected()) button = sufix;
      else if(prefix.isSelected()) button = prefix;
      mainframe.getContentPane().removeAll();
      mainframe.revalidate();
      mainframe.repaint();
      new Search_Result(mainframe, result, button.getText());
    }
  }
}
