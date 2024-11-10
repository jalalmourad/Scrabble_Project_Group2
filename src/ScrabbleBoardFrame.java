import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScrabbleBoardFrame extends JFrame implements ScrabbleView {

    JButton[][] buttons;
    JButton[] wordsInHandButtons;
    JPanel boardPanel;
    JPanel wordsInHandPanel;
    JButton submitButton;
    ScrabbleGame model;
    JTextArea scoreText;
    ScrabbleController controller;
    JMenu menu;
    JMenuBar menuBar;
    JMenuItem playMenuItem;

    public ScrabbleBoardFrame() {
        super("Scrabble!");

        wordsInHandPanel = new JPanel();
        wordsInHandButtons = new JButton[7];

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(15, 15));
        wordsInHandPanel.setLayout(new GridLayout(0, 7));

        model = new ScrabbleGame();
        controller = new ScrabbleController(this, model);
        model.addView(this);

        scoreText = new JTextArea("SCORES: ");
        scoreText.setEditable(false);


        submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(controller);

        buttons = new JButton[15][15];
        setLayout(new BorderLayout());
        setSize(800, 580);

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton button = new JButton();
                buttons[i][j] = button;
                buttons[i][j].addActionListener(controller);
                buttons[i][j].setActionCommand(i + "" + j);
                boardPanel.add(button);
            }
        }
        buttons[7][7].setBackground(Color.RED);

        for (int i = 0; i < wordsInHandButtons.length; i++) {
            JButton button = new JButton();
            wordsInHandButtons[i] = button;
            wordsInHandButtons[i].setBackground(Color.WHITE);
            wordsInHandButtons[i].setPreferredSize(new Dimension(30, 50));
            wordsInHandButtons[i].addActionListener(controller);
            wordsInHandButtons[i].setActionCommand("h" + i);
            wordsInHandPanel.add(wordsInHandButtons[i]);
        }

        menuBar = new JMenuBar();
        menu = new JMenu("Settings");
        menuBar.add(menu);
        playMenuItem = new JMenuItem("Play");
        playMenuItem.addActionListener(controller);
        playMenuItem.setActionCommand("play");
        menu.add(playMenuItem);

        setJMenuBar(menuBar);

        this.add(boardPanel, BorderLayout.CENTER);
        this.add(wordsInHandPanel, BorderLayout.SOUTH);
        this.add(scoreText, BorderLayout.EAST);
        this.add(submitButton, BorderLayout.NORTH);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ScrabbleBoardFrame frame = new ScrabbleBoardFrame();
    }

    public void enableHandButtons() {
        for (int i = 0; i < 7; i++) {
            wordsInHandButtons[i].setEnabled(true);
        }
    }

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