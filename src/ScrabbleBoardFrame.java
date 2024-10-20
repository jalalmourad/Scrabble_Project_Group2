import javax.swing.*;
import java.awt.*;

public class ScrabbleBoardFrame extends JFrame {

    JButton[][] buttons;

    JButton [] wordsInHandButtons;

    JPanel boardPanel;
    JPanel wordsInHandPanel;

    public ScrabbleBoardFrame(){

        super("Scrabble!");

        wordsInHandPanel = new JPanel();
        wordsInHandButtons = new JButton[7];

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(15,15));
        wordsInHandPanel.setLayout(new GridLayout(0,7));

        buttons = new JButton[15][15];
        setLayout(new BorderLayout());
        setSize(500,500);

        for (int i = 0; i<15;i++){
            for (int j = 0;j<15;j++){

                JButton button = new JButton();
                buttons[i][j] = button;
                //buttons[i][j].addActionListener();
                boardPanel.add(button);

            }

        }
        buttons[7][7].setBackground(Color.RED);

        for (int i = 0; i< wordsInHandButtons.length;i++){
            JButton button = new JButton();
            wordsInHandButtons[i] = button;
            wordsInHandButtons[i].setBackground(Color.WHITE);
            wordsInHandButtons[i].setPreferredSize(new Dimension(30, 50));

            wordsInHandPanel.add(wordsInHandButtons[i]);
        }

        this.add(boardPanel,BorderLayout.CENTER);
        this.add(wordsInHandPanel,BorderLayout.SOUTH);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ScrabbleBoardFrame frame = new ScrabbleBoardFrame();
    }
}
