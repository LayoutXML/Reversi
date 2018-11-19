
/**
 * ReversiGui:
 *
 * @author Iain Martin
 * @version 1.0
 *
 * Notes to use ReversiGui
 *  ReversiGuu is intended as a replacement for a Menu class for Reversi.
 *  Comments that start with ReversiGUI mark where you might
 *  add your own code. Please do not attempt to use this GUI until
 *  you have already met the minimum requirements of the project.
 *
 * Notes:
 *  Event handlers have been set up for Menu Options
 *  NewGame, LoadGame and Save Game.
 *
 *  An Event handler has also been set up for a Mouse Click on
 *  the grid which calls clickSquare(row, col).
 *
 *  To add functionality to this GUI add you code to these functions
 *  which are at the end of this file.
 *
 *  Potential additions: FileChoosers could be implemented and the grid characters
 *  could be replaced with graphics by loading gifs or jpgs into the grid which is
 *  created from JButtons.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ReversiGui implements ActionListener
{
    // Default filename to use for saving and loading files
    // Possible improvement: replace with a FileChooser
    private final static String DEFAULT_FILENAME = "Reversigui.txt";
    private int GRID_SIZE = 8;
    private JButton [] buttonArray;
    private Game game = new Game();

    public JMenuBar createMenu()
    {
        JMenuBar menuBar  = new JMenuBar();;
        JMenu menu = new JMenu("Reversi Menu");
        JMenuItem menuItem;

        menuBar.add(menu);

        // A group of JMenuItems. You can create other menu items here if desired
        menuItem = new JMenuItem("New Game (HvH)");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("New Game (HvC)");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Load Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //a submenu
        menu.addSeparator();
        return menuBar;
    }

    public Container createContentPane()
    {
        int numButtons = GRID_SIZE * GRID_SIZE;
        JPanel grid = new JPanel(new GridLayout(GRID_SIZE,GRID_SIZE));
        buttonArray = new JButton[numButtons];

        for (int i=0; i<numButtons; i++)
        {
            buttonArray[i] = new JButton(" ");

            // This label is used to identify which button was clicked in the action listener
            buttonArray[i].setActionCommand("" + i); // String "0", "1" etc.
            buttonArray[i].addActionListener(this);
            grid.add(buttonArray[i]);

            buttonArray[i].setBackground(Color.GRAY);
            buttonArray[i].setFocusPainted(false);
        }
        return grid;
    }

    /**
     * This method handles events from the Menu and the board.
     *
     */
    public void actionPerformed(ActionEvent e)
    {
        String classname = getClassName(e.getSource());
        JComponent component = (JComponent)(e.getSource());

        if (classname.equals("JMenuItem"))
        {
            JMenuItem menusource = (JMenuItem)(e.getSource());
            String menutext  = menusource.getText();

            // Determine which menu option was chosen
            if (menutext.equals("New Game (HvH)")) {
                game.startNewGameHvH();
                updateGUIBoard(game.returnBoard());
                game.printWhoseTurn();
            }
            else if (menutext.equals("New Game (HvC)")) {
                Object[] options = {"Yes", "No"};
                int n = JOptionPane.showOptionDialog(new JFrame(), "Would you like computer to go first?", "Who goes first?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                switch (n) {
                    case 0:
                        game.startNewGameHvC(true);
                        break;
                    case 1:
                        game.startNewGameHvC(false);
                        break;
                    default:
                        game.startNewGameHvC(false);
                        break;
                }
                updateGUIBoard(game.returnBoard());
                game.printWhoseTurn();
            }
            else if (menutext.equals("Save Game")) {
                game.saveGame();
            }
            else if (menutext.equals("Load Game")) {
                game.loadGame();
                updateGUIBoard(game.returnBoard());
                game.checkIfGameOver();
                game.printWhoseTurn();
            }
        }
        // Handle the event from the user clicking on a command button
        else if (classname.equals("JButton"))
        {
            JButton button = (JButton)(e.getSource());
            int bnum = Integer.parseInt(button.getActionCommand());
            int row = bnum / GRID_SIZE;
            int col = bnum % GRID_SIZE;

            /* ReversiGUI    Add your code here to handle user clicking on the grid ***********/
            clickSquare(row, col);
        }
    }

    /**
     *  Returns the class name
     */
    protected String getClassName(Object o)
    {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    /**
     * Create the GUI and show it.
     * For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        // Create and set up the window.
        JFrame frame = new JFrame("ReversiGui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        ReversiGui Reversigui = new ReversiGui();
        frame.setJMenuBar(Reversigui.createMenu());
        frame.setContentPane(Reversigui.createContentPane());

        // Display the window, setting the size
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    /**
     * Sets a Gui grid square at row, col to display a character
     */
    public boolean setGuiSquare(int row, int col, char c)
    {
        int bnum = row * GRID_SIZE + col;
        if (bnum >= (GRID_SIZE*GRID_SIZE))
        {
            return false;
        }
        else
        {
            if (c=='#')
                buttonArray[bnum].setBackground(Color.BLACK);
            else if (c=='O')
                buttonArray[bnum].setBackground(Color.WHITE);
            else
                buttonArray[bnum].setBackground(Color.GRAY);
            //buttonArray[bnum].setText(Character.toString(c));
        }
        return true;
    }

    /**
     * This is a standard main function for a Java GUI
     */
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }

    private void updateGUIBoard(char[][] board) {
        for (int i=0; i<Board.boardHeight; i++) {
            for (int j=0; j<Board.boardWidth; j++) {
                setGuiSquare(i, j, board[j][i]);
            }
        }
    }

    /**
     * This method is called from the Mouse Click event.
     * ReversiGUI
     */
    public void clickSquare(int row, int col)
    {
        if (game.gameStarted) {
            game.runGame(col, row);
            updateGUIBoard(game.returnBoard());
        }
    }
}