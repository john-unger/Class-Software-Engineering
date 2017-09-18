package user_GUI;

import java.awt.Color;
import javax.swing.JFrame;

public class MainFrame extends JFrame
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public MainFrame()
  {
     super("Guten Search");
     setSize(900, 700);
     getContentPane().setBackground(Color.lightGray);
     setResizable(false);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
  }
}