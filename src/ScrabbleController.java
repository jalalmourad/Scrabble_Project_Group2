import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ScrabbleController implements ActionListener {

    ScrabbleBoardFrame frame;
    ScrabbleGame model;

    public ScrabbleController(ScrabbleBoardFrame frame, ScrabbleGame model) {
        this.frame = frame;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("play")) {
            int playerNumber = Integer.parseInt(JOptionPane.showInputDialog("Select the number of players (2-4)?"));
            frame.enableComponents(frame.boardPanel.getComponents());
            frame.enableComponents(frame.handPanel.getComponents());
            frame.enableComponents(frame.scoreText.getComponents());
            model.participants(playerNumber);
            model.updateViews();
        }

        String[] handlist = s.split("");
        if (handlist[0].equals("h")) {
            JButton sourceButton = (JButton) e.getSource();
            String text = sourceButton.getText();
            model.setHandListCoord(handlist[1]);
            model.setTextPlayed(text);
        } else {
            String string = e.getActionCommand();
            String[] coordinates = string.split("");

            if (Character.isDigit(coordinates[0].charAt(0))) {

                int x = Integer.parseInt(coordinates[1]);
                int y = Integer.parseInt(coordinates[0]);


                if (model.getDone()) {
                    if (x != 7 || y != 7) {
                        JOptionPane.showMessageDialog(null, "Illegal move, please start from the board center: (X:7, Y:7)");
                        return;
                    }
                    model.setDone(false);
                }

                model.setX(x);
                model.setY(y);

                model.playTurn(model.getCurrentPlayer(), x, y, model.getTextPlayed().charAt(0));
                model.updateViews();

                String place = JOptionPane.showInputDialog("Do you want to continue playing?");

                while (place.equalsIgnoreCase("yes")) {
                    place = JOptionPane.showInputDialog("Do you want to place another character? (yes/no)");
                    if (place.equals("yes")){
                        return;
                    }
                    model.playTurn(model.getCurrentPlayer(), x, y, model.getTextPlayed().charAt(0));
                    model.updateViews();
                }

                String formedWord = model.checkValidWord(y, x);
                if (model.getParser().isValidWord(formedWord)) {
                    JOptionPane.showMessageDialog(null, formedWord + " is a valid word!");
                    model.getCurrentPlayer().calculateWordScore(formedWord);
                    model.turn++;
                    model.updateViews();
                    model.removeCharsFromHand();
                    model.playerHand.refillHand();
                    frame.enableHandButtons();

                } else {
                    JOptionPane.showMessageDialog(null, formedWord + " is not a valid English word!");
                }
            }
        }
    }


}
