import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScrabbleBoardFrame extends JFrame implements ScrabbleView {

    public static final int SIZE = 15;
    JButton[][] buttons;
    JButton[] wordsInHandButtons;
    JButton submitButton;
    JButton passButton;

    JPanel boardPanel;
    JPanel wordsInHandPanel;
    JPanel actionButtons;
    JTextArea scoreText;

    ScrabbleGame model;
    ScrabbleController controller;

    JMenu menu;
    JMenuBar menuBar;
    JMenuItem playMenuItem;

    public ScrabbleBoardFrame() {
        super("Scrabble!");
        setLayout(new BorderLayout());
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new ScrabbleGame();
        controller = new ScrabbleController(this, model);
        model.addView(this);

        // Menu Bar Initializations
        menuBar = new JMenuBar();
        menu = new JMenu("Settings");
        menuBar.add(menu);
        playMenuItem = new JMenuItem("Play");
        playMenuItem.addActionListener(controller);
        playMenuItem.setActionCommand("play");
        menu.add(playMenuItem);
        setJMenuBar(menuBar);

        // Main Board Initializations
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE,SIZE));
        buttons = new JButton[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                //JButton button = new JButton();
                //buttons[i][j] = button;
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(controller);
                buttons[i][j].setActionCommand(i + "" + j);
                buttons[i][j].setBackground(Color.WHITE);
                boardPanel.add(buttons[i][j]);
            }
        }
        buttons[7][7].setBackground(Color.RED);

        // WordsInHand Panel Initializations
        wordsInHandPanel = new JPanel();
        wordsInHandPanel.setLayout(new GridLayout(0, 7));
        wordsInHandButtons = new JButton[7];

        for (int i = 0; i < wordsInHandButtons.length; i++) {
            //JButton button = new JButton();
            //wordsInHandButtons[i] = button;
            wordsInHandButtons[i] = new JButton();
            wordsInHandButtons[i].addActionListener(controller);
            wordsInHandButtons[i].setActionCommand("h" + i);
            wordsInHandButtons[i].setBackground(Color.WHITE);
            wordsInHandButtons[i].setPreferredSize(new Dimension(50,50));
            wordsInHandPanel.add(wordsInHandButtons[i]);
        }

        // Score TextArea Initializations
        //scoreText = new JTextArea("SCORES: ");
        scoreText = new JTextArea("SCORE");
        scoreText.setEditable(false);
        scoreText.setPreferredSize(new Dimension(100,100));

        // Action Panel Initializations
        actionButtons = new JPanel();
        actionButtons.setLayout(new GridLayout(0,2));

        submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(controller);

        passButton = new JButton("Pass");
        passButton.addActionListener(controller);
        passButton.setActionCommand("pass");

        actionButtons.add(submitButton);
        actionButtons.add(passButton);


        this.add(boardPanel, BorderLayout.CENTER);
        this.add(wordsInHandPanel, BorderLayout.SOUTH);
        this.add(scoreText, BorderLayout.EAST);
        this.add(actionButtons, BorderLayout.NORTH);

        // Initially disable everything except the menu bar (to allow the player to trigger the game)
        //this.disableComponents(boardPanel.getComponents());
        //this.disableComponents(wordsInHandPanel.getComponents());
        //this.disableComponents(scoreText.getComponents());
        //this.disableComponents(actionButtons.getComponents());

        setVisible(true);

    }

    public static void main(String[] args) {
        ScrabbleBoardFrame frame = new ScrabbleBoardFrame();
    }

    public void enableHandButtons() {
        for (int i = 0; i < 7; i++) {
            wordsInHandButtons[i].setEnabled(true);
        }
    }

    //public void disableComponents(Component[] comps) {
    //    for (Component comp : comps) {
    //        comp.setEnabled(false);
    //    }
    //}

    //public void enableComponents(Component[] comps) {
    //    for (Component comp : comps) {
    //        comp.setEnabled(true);
    //    }
    //}

    @Override
    public void update(ScrabbleGame game) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                char letterOnBoard = game.board.getLetterOnBoard(i, j);
                if (letterOnBoard == ' ') {
                    buttons[i][j].setText("");
                } else {
                    buttons[i][j].setText(String.valueOf(letterOnBoard));
                }
            }
        }

        ArrayList<Character> lettersInHand = game.getCurrentPlayer().getHand().getLetters();
        for (int i = 0; i < wordsInHandButtons.length; i++) {
            if (i < lettersInHand.size()) {
                wordsInHandButtons[i].setText(String.valueOf(lettersInHand.get(i)));
            } else {
                wordsInHandButtons[i].setText("");
            }
        }

        if (game.invalidFlag()) {
            enableHandButtons();
            game.resetInvalidFlag();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Player Name: ").append(model.getCurrentPlayer().getName()).append("\n")
                .append("Score: ").append(String.valueOf(model.getCurrentPlayer().getPlayerScore()));

        scoreText.setText(sb.toString());

        buttons[model.getyCoordinate()][model.getxCoordinate()].setText(String.valueOf(model.getTextPlayed()));

        if(model.getHandListCoord()!= null) {
            wordsInHandButtons[Integer.parseInt(model.getHandListCoord())].setEnabled(false);
        }
    }
}