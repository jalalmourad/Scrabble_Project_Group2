import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ScrabbleBoardFrame extends JFrame implements ScrabbleView {

    public static final int SIZE = 15;
    JButton[][] buttons;
    JButton[] wordsInHandButtons;
    JButton submitButton;
    JButton passButton;
    JButton undoButton;
    JButton redoButton;

    JPanel boardPanel;
    JPanel wordsInHandPanel;
    JPanel actionButtons;
    JTextArea scoreText;
    JTextArea playerTurnText;

    ScrabbleGame model;
    ScrabbleController controller;

    JMenuBar menuBar;
    JMenu optionMenu;
    JMenuItem playMenuItem;
    JMenuItem helpMenuItem;
    JMenuItem pointsMenuItem;
    JMenu saveMenu;
    JMenuItem saveItem;
    JMenuItem loadItem;

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

        optionMenu = new JMenu("Options");
        menuBar.add(optionMenu);

        playMenuItem = new JMenuItem("Play");
        playMenuItem.addActionListener(controller);
        playMenuItem.setActionCommand("play");
        optionMenu.add(playMenuItem);

        helpMenuItem = new JMenuItem("Help");
        helpMenuItem.addActionListener(controller);
        helpMenuItem.setActionCommand("help");
        optionMenu.add(helpMenuItem);

        pointsMenuItem = new JMenuItem("Letter Values");
        pointsMenuItem.addActionListener(controller);
        pointsMenuItem.setActionCommand("values");
        optionMenu.add(pointsMenuItem);

        saveMenu = new JMenu("Save/Load");
        menuBar.add(saveMenu);

        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(controller);
        saveItem.setActionCommand("save");
        saveMenu.add(saveItem);

        loadItem = new JMenuItem("Load");
        loadItem.addActionListener(controller);
        loadItem.setActionCommand("load");
        saveMenu.add(loadItem);

        setJMenuBar(menuBar);


        // Main Board Initializations
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE,SIZE));
        buttons = new JButton[SIZE][SIZE];

        NormalModeBoard(); // Initialize the default game board, which can be changed later upon starting the game
        buttons[7][7].setBackground(Color.PINK);

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
        actionButtons.setLayout(new GridLayout(0,4));

        submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(controller);
        submitButton.setBackground(Color.WHITE);

        passButton = new JButton("Pass");
        passButton.addActionListener(controller);
        passButton.setActionCommand("pass");
        passButton.setBackground(Color.WHITE);

        undoButton = new JButton("Undo");
        undoButton.addActionListener(controller);
        undoButton.setActionCommand("undo");
        undoButton.setBackground(Color.WHITE);

        redoButton = new JButton("Redo");
        redoButton.addActionListener(controller);
        redoButton.setActionCommand("redo");
        redoButton.setBackground(Color.WHITE);

        actionButtons.add(submitButton);
        actionButtons.add(passButton);
        actionButtons.add(undoButton);
        actionButtons.add(redoButton);

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
     * Specific disabler for buttons to ensure no wrong inputs
     * (eg) false submits, false undos, etc.
     */
    public void disableButton(Component comp) {
        comp.setEnabled(false);
    }

    /**
     * Specific enablers for buttons to ensure no wrong inputs
     * (eg) false submits, false undos, etc.
     */
    public void enableButton(Component comp) {
        comp.setEnabled(true);
    }

    /**
     * Enables specific letter in Hand, used for Undo method
     */
    public void enableLetterButton(char letter, Component[] comps) {
        for (Component comp : comps) {
            if (comp instanceof JButton button) {
                if (button.getText().equals(String.valueOf(letter))) {
                    button.setEnabled(true);
                }
            }
        }
    }

    /**
     * Disables specific letter in Hand, used for Redo method
     */
    public void disableLetterButton(char letter, Component[] comps) {
        for (Component comp : comps) {
            if (comp instanceof JButton button) {
                if (button.getText().equals(String.valueOf(letter))) {
                    button.setEnabled(false);
                }
            }
        }
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


    public void saveGame() {
        try (ObjectOutputStream out =new ObjectOutputStream(new FileOutputStream("scrabble_game.ser"))) {
            out.writeObject(model);
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, "Error saving the game: "+ e.getMessage());
        }
    }
    
    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("scrabble_game.ser"))) {
            ScrabbleGame loadedGame =(ScrabbleGame) in.readObject();
            //model =loadedGame;
            model.importScrabbleGame(loadedGame);
            controller =new ScrabbleController(this, model);
            model.addView(this);
            update(model);
            model.updateViews();
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } catch (IOException |ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading the game: " +e.getMessage());
        }
    }

    /**
     * Selection of Board, prompted when the game is first started
     */
    public void boardSelection(String selection) {
        boardPanel.removeAll();

        switch (selection) {
            case "Normal Mode" -> NormalModeBoard();
            case "Fun Mode" -> FunModeBoard();
            case "Spiral Mode" -> SpiralModeBoard();
            case "Boring Mode" -> BoringModeBoard();
        }
        buttons[7][7].setBackground(Color.PINK);
        disableComponents(boardPanel.getComponents());
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    /**
     * Normal Mode Board
     */
    public void NormalModeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                boolean isPremium = false;

                for (int[][] premiumType : Board.premiumTilesNormal) { // Looping through premium tile types
                    for (int[] premiumCoords : premiumType) { // Looping through individual premium tile coordinates of each type
                        if (premiumCoords[1] == i && premiumCoords[0] == j) {
                            if (premiumType == DoubleLetterSquare.dlsCoordsNormal) { // Create Double Letter Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.CYAN);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == TripleLetterSquare.tlsCoordsNormal) { // Create Triple Letter Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.BLUE);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == DoubleWordSquare.dwsCoordsNormal) { // Create Double Word Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.MAGENTA);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == TripleWordSquare.twsCoordsNormal) { // Create Triple Word Square
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
    }

    /**
     * Fun Mode Board (Custom 1)
     */
    public void FunModeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                boolean isPremium = false;

                for (int[][] premiumType : Board.premiumTilesFunMode) { // Looping through premium tile types
                    for (int[] premiumCoords : premiumType) { // Looping through individual premium tile coordinates of each type
                        if (premiumCoords[1] == i && premiumCoords[0] == j) {
                            if (premiumType == DoubleLetterSquare.dlsCoordsFunMode) { // Create Double Letter Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.CYAN);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == TripleLetterSquare.tlsCoordsFunMode) { // Create Triple Letter Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.BLUE);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == DoubleWordSquare.dwsCoordsFunMode) { // Create Double Word Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.MAGENTA);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == TripleWordSquare.twsCoordsFunMode) { // Create Triple Word Square
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
    }

    /**
     * Spiral Mode Board (Custom 2)
     */
    public void SpiralModeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                boolean isPremium = false;

                for (int[][] premiumType : Board.premiumTilesSpiralMode) { // Looping through premium tile types
                    for (int[] premiumCoords : premiumType) { // Looping through individual premium tile coordinates of each type
                        if (premiumCoords[1] == i && premiumCoords[0] == j) {
                            if (premiumType == DoubleLetterSquare.dlsCoordsSpiralMode) { // Create Double Letter Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.CYAN);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == TripleLetterSquare.tlsCoordsSpiralMode) { // Create Triple Letter Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.BLUE);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == DoubleWordSquare.dwsCoordsSpiralMode) { // Create Double Word Square
                                buttons[i][j] = new JButton();
                                buttons[i][j].addActionListener(controller);
                                buttons[i][j].setActionCommand(i + "" + j);
                                buttons[i][j].setBackground(Color.MAGENTA);
                                boardPanel.add(buttons[i][j]);
                            } else if (premiumType == TripleWordSquare.twsCoordsSpiralMode) { // Create Triple Word Square
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
    }

    /**
     * Boring Mode Board (Custom 3)
     */
    public void BoringModeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(controller);
                buttons[i][j].setActionCommand(i + "" + j);
                buttons[i][j].setBackground(Color.WHITE);
                boardPanel.add(buttons[i][j]);
            }
        }
    }

}