package admin_GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import admin_Index.*;
/**
 * The class responsible for selecting a book to edit
 * @author Team 11
 *
 */

public class Select_Book{

    JComboBox<?>     list;
    ArrayList<String> BookNames = new ArrayList<String>();
    File[] paths = null;
    private final MainFrame mainframe;
    JDialog dialog;

    /**
     * Purpose:  Select the book to edit
     * 
     * @param mainframe the main JFrame for the admin program.
     */
    public Select_Book(final MainFrame mainframe)
    {
        this.mainframe = mainframe;

        JOptionPane pn = new JOptionPane(makeList(), JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

        dialog = pn.createDialog("Select Your Book");

        dialog.setVisible(true);

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    /**
     * Purpose: Make a panel that returns the panel with the books.
     * @return the constructed panel
     */
    private JPanel makeList()
    {
        JPanel pan = new JPanel();
        int index = 0;
        File f = null;

        pan.add(new JLabel("No books have been added"));

        try{
            // create new file
            f = new File(MakeMenuB.getAdminPath() + File.separator + "Books");
            // array of files and directory
            paths = f.listFiles();
            try{index = paths.length;}
            catch (NullPointerException e)
            {
                index=0;
                mainframe.getContentPane().removeAll();
                mainframe.revalidate();
                mainframe.repaint();
                new Add_Text(mainframe);
            }
        }catch(Exception e){
            // if any error occurs
            e.printStackTrace();
        }

        if (index > 0)
        {
            for (index = 0; index < paths.length; index++)
            {
                ArrayList<String> bookTA = new ArrayList<String>();
                bookTA.addAll(new Index(paths[index]).getTAofBook());
                // Finally got all the names
                BookNames.add(bookTA.get(0) + " by: " + bookTA.get(1));
            }

            final ArrayList<String> it = new ArrayList<String>();
            it.addAll(BookNames);

            list = new JComboBox(BookNames.toArray());
            list.addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e) {
                    JComboBox<?> cb = (JComboBox<?>)e.getSource();
                    String bookName = (String)cb.getSelectedItem();

                    int i = it.indexOf(bookName);
       
                    dialog.dispose();

                    mainframe.getContentPane().removeAll();
                    mainframe.revalidate();
                    mainframe.repaint();

                    Index book = new Index(paths[i]);
                    new EditFile(mainframe, book);
                }});
            pan.removeAll();
            pan.add(list);
        }
        return pan;
    }
}
