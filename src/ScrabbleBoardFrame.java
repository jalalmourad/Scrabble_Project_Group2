import javax.swing.*;
import java.awt.*;

public class ScrabbleBoardFrame extends JFrame implements ScrabbleView{

    public static final int SIZE = 15;
    JButton[][] buttons;
    JButton[] handButtons;

    ScrabbleGame model;
    ScrabbleController controller;

    JPanel boardPanel;
    JPanel handPanel;
    JTextArea scoreText;

    JMenu menu;
    JMenuBar menuBar;
    JMenuItem playMenuItem;

    public ScrabbleBoardFrame(){
        super("Scrabble!");
        this.setLayout(new BorderLayout());
        this.setSize(1000,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        menu.add(playMenuItem);
        setJMenuBar(menuBar);

        // Main Board Initializations
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE,SIZE));
        buttons = new JButton[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(controller);
                buttons[i][j].setActionCommand(i + "" + j);
                buttons[i][j].setBackground(Color.WHITE);
                boardPanel.add(buttons[i][j]);
            }
        }
        buttons[7][7].setBackground(Color.RED);

        // Hand Panel Initializations
        handPanel = new JPanel();
        handPanel.setLayout(new GridLayout(0,7));
        handButtons = new JButton[7];
        for (int i = 0; i < 7; i++){
            handButtons[i] = new JButton();
            handButtons[i].addActionListener(controller);
            handButtons[i].setActionCommand("h" + i);
            handButtons[i].setBackground(Color.WHITE);
            handButtons[i].setPreferredSize(new Dimension(50,50));
            handPanel.add(handButtons[i]);
        }

        // Score TextArea Initializations
        scoreText = new JTextArea("Scores");
        scoreText.setEditable(false);
        scoreText.setPreferredSize(new Dimension(100,100));

        this.add(boardPanel,BorderLayout.CENTER);
        this.add(handPanel,BorderLayout.SOUTH);
        this.add(scoreText,BorderLayout.EAST);

        this.disableComponents(boardPanel.getComponents());
        this.disableComponents(handPanel.getComponents());
        this.disableComponents(scoreText.getComponents());

        this.setVisible(true);
    }

    public static void main(String[] args) {
        ScrabbleBoardFrame frame = new ScrabbleBoardFrame();
    }

    public void enableHandButtons(){
        for (int i = 0;i<7;i++){
            handButtons[i].setEnabled(true);
        }
    }

    @Override
    public void update(ScrabbleGame game) {

        for (int i = 0; i<game.getCurrentPlayer().getHand().getLetters().size();i++){
            handButtons[i].setText(String.valueOf(game.getCurrentPlayer().getHand().getLetters().get(i)));
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Player Name: ").append(model.getCurrentPlayer().getName()).append("\n").append("Score: ").append(String.valueOf(model.getCurrentPlayer().getPlayerScore()));

        scoreText.setText(String.valueOf(sb));

        buttons[model.getY()][model.getX()].setText(String.valueOf(model.getTextPlayed()));

        handButtons[Integer.parseInt(model.getHandListCoord())].setEnabled(false);
    }

    public void disableComponents(Component[] comps) {
        for (Component comp : comps) {
            comp.setEnabled(false);
        }
    }

    public void enableComponents(Component[] comps) {
        for (Component comp : comps) {
            comp.setEnabled(true);
        }
    }
}
