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
    JTextArea playerTurnText;

    ScrabbleGame model;
    ScrabbleController controller;

    JMenu menu;
    JMenuBar menuBar;
    JMenuItem playMenuItem;
    JMenuItem helpMenuItem;
    JMenuItem pointsMenuItem;

    public ScrabbleBoardFrame() {
        super("Scrabble!");
        setLayout(new BorderLayout());
        setSize(850,850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new ScrabbleGame();
        controller = new ScrabbleController(this, model);
        model.addView(this);

        // Menu Bar Initializations
        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        menuBar.add(menu);
        playMenuItem = new JMenuItem("Play");
        playMenuItem.addActionListener(controller);
        playMenuItem.setActionCommand("play");

        helpMenuItem = new JMenuItem("Help");
        helpMenuItem.addActionListener(controller);
        helpMenuItem.setActionCommand("help");

        pointsMenuItem = new JMenuItem("Letter Values");
        pointsMenuItem.addActionListener(controller);
        pointsMenuItem.setActionCommand("values");

        menu.add(playMenuItem);
        menu.add(helpMenuItem);
        menu.add(pointsMenuItem);
        setJMenuBar(menuBar);

        // Main Board Initializations
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE,SIZE));
        buttons = new JButton[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                boolean isPremium = false;

                for (int[][] premiumType : Board.premiumTiles) { // Looping through premium tile types
                    for (int[] premiumCoords : premiumType) { // Looping through individual premium tile coordinates of each type
                        if (premiumCoords[1] == i && premiumCoords[0] == j) {
                            if (premiumType == DoubleLetterSquare.dlsCoords) { // Create Double Letter Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.CYAN);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == TripleLetterSquare.tlsCoords) { // Create Triple Letter Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.BLUE);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == DoubleWordSquare.dwsCoords) { // Create Double Word Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.MAGENTA);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == TripleWordSquare.twsCoords) { // Create Triple Word Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.RED);
                                boardPanel.add(buttons[i][j]);
                            }
                            isPremium = true;
                            break;
                        }
                    }
                    if (isPremium) {
                        break;
                    }
                }
                if (!isPremium) { // Create normal square
                    buttons[i][j] = new JButton();
                    buttons[i][j].addActionListener(controller);
                    buttons[i][j].setActionCommand(i + "" + j);
                    buttons[i][j].setBackground(Color.WHITE);
                    boardPanel.add(buttons[i][j]);
                }
            }
        }
        buttons[7][7].setBackground(Color.PINK);

        /**
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(controller);
                buttons[i][j].setActionCommand(i + "" + j);
                buttons[i][j].setBackground(Color.WHITE);
                boardPanel.add(buttons[i][j]);
            }
        }
        buttons[7][7].setBackground(Color.RED);
         */

        // WordsInHand Panel Initializations
        wordsInHandPanel = new JPanel();
        wordsInHandPanel.setLayout(new GridLayout(0, 7));
        wordsInHandButtons = new JButton[7];

        for (int i = 0; i < wordsInHandButtons.length; i++) {
            wordsInHandButtons[i] = new JButton();
            wordsInHandButtons[i].addActionListener(controller);
            wordsInHandButtons[i].setActionCommand("h" + i);
            wordsInHandButtons[i].setBackground(Color.WHITE);
            wordsInHandButtons[i].setPreferredSize(new Dimension(50,50));
            wordsInHandPanel.add(wordsInHandButtons[i]);
        }

        // Score TextArea Initializations
        scoreText = new JTextArea("SCORE");
        scoreText.setEditable(false);
        scoreText.setPreferredSize(new Dimension(100,100));

        // Action Panel Initializations
        actionButtons = new JPanel();
        actionButtons.setLayout(new GridLayout(0,2));

        submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(controller);
        submitButton.setBackground(Color.WHITE);

        passButton = new JButton("Pass");
        passButton.addActionListener(controller);
        passButton.setActionCommand("pass");
        passButton.setBackground(Color.WHITE);

        actionButtons.add(submitButton);
        actionButtons.add(passButton);

        playerTurnText = new JTextArea();
        playerTurnText.setEditable(false);
        actionButtons.add(playerTurnText);

        this.add(boardPanel, BorderLayout.CENTER);
        this.add(wordsInHandPanel, BorderLayout.SOUTH);
        this.add(scoreText, BorderLayout.EAST);
        this.add(actionButtons, BorderLayout.NORTH);

        // Initially disable everything except the menu bar (to allow the player to trigger the game)
        this.disableComponents(boardPanel.getComponents());
        this.disableComponents(wordsInHandPanel.getComponents());
        this.disableComponents(scoreText.getComponents());
        this.disableComponents(actionButtons.getComponents());

        this.setLocationRelativeTo(null);
        setVisible(true);

        JOptionPane.showMessageDialog(null, "Welcome to the game of Scrabble! Please select Options > Play to begin the game!");
    }

    /**
     * Begin game
     */
    public static void main(String[] args) {
        ScrabbleBoardFrame frame = new ScrabbleBoardFrame();
    }

    /**
     * Disables selected components, useful for preventing player mis-input
     */
    public void disableComponents(Component[] comps) {
        for (Component comp : comps) {
            comp.setEnabled(false);
        }
    }

    /**
     * Enables selected components, useful for allowing player input
     */
    public void enableComponents(Component[] comps) {
        for (Component comp : comps) {
            comp.setEnabled(true);
        }
    }

    /**
     * Specific disabler for the Submit button to ensure turn separation
     */
    public void disableSubmitButton(Component comp) {
        comp.setEnabled(false);
    }

    /**
     * Updates game view(s)
     */
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
            enableComponents(wordsInHandPanel.getComponents());
            game.resetInvalidFlag();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SCORE\n\n");
        for (Player p : model.players) {
            sb.append("Player " + p.getName() + ": " + p.getPlayerScore() + " pts\n\n");
        }
        playerTurnText.setText("Player " + model.getCurrentPlayer().getName() + "'s Turn");

        scoreText.setText(sb.toString());

        buttons[model.getyCoordinate()][model.getxCoordinate()].setText(String.valueOf(model.getTextPlayed()));

        if(model.getHandListCoord()!= null) {
            wordsInHandButtons[Integer.parseInt(model.getHandListCoord())].setEnabled(false);
        }
    }
}