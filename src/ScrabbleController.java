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
            model.MVCparticipants(playerNumber);
            model.updateViews();
        }
        String[] handlist = s.split("");
        if (handlist[0].equals("h")) {
            JButton sourceButton = (JButton) e.getSource();
            String text = sourceButton.getText();
            model.setHandListCoord(handlist[1]);
            model.setTextPlayed(text);
        } else {
            int y = -1, x = -1;

            if (s.length() == 2) {
                if (Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(1))) {
                    y = Character.getNumericValue(s.charAt(0));
                    x = Character.getNumericValue(s.charAt(1));
                }
            } else if (s.length() == 3) {
                if (Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(1)) && Character.isDigit(s.charAt(2))) {
                    if (Character.getNumericValue(s.charAt(0)) <= 1) {
                        y = Integer.parseInt(s.substring(0, 2));
                        x = Character.getNumericValue(s.charAt(2));
                    } else {
                        y = Character.getNumericValue(s.charAt(0));
                        x = Integer.parseInt(s.substring(1, 3));
                    }
                }
            } else if (s.length() == 4) {
                if (Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(1)) &&
                        Character.isDigit(s.charAt(2)) && Character.isDigit(s.charAt(3))) {
                    y = Integer.parseInt(s.substring(0, 2));
                    x = Integer.parseInt(s.substring(2, 4));
                }
            }

            if (y >= 0 && y < 15 && x >= 0 && x < 15) {
                if (model.getDone()) {
                    if (x != 7 || y != 7) {
                        JOptionPane.showMessageDialog(null, "Illegal move, please start from the board center: (Y:7, X:7)");
                        return;
                    }
                    model.setDone(false);
                }

                model.setxCoordinate(x);
                model.setyCoordinate(y);
                model.MVCplayTurn(model.getCurrentPlayer(), x, y, model.getTextPlayed().charAt(0));
                model.updateViews();

                String place = JOptionPane.showInputDialog("Do you want to continue playing?");
                while (place.equalsIgnoreCase("yes")) {
                    place = JOptionPane.showInputDialog("Do you want to place another character? (yes/no)");
                    if (place.equals("yes")) {
                        return;
                    }
                    model.MVCplayTurn(model.getCurrentPlayer(), x, y, model.getTextPlayed().charAt(0));
                    model.updateViews();
                }

                String formedWord = model.checkValidWord(y, x);
                if (model.getParser().isValidWord(formedWord)) {
                    JOptionPane.showMessageDialog(null, formedWord + " is a valid word!");
                    model.getCurrentPlayer().calculateWordScore(formedWord);
                    model.removeCharsFromHand();
                    model.getCurrentPlayer().getHand().refillHand();
                    model.turn++;
                    model.updateViews();
                    frame.enableHandButtons();
                } else {
                    JOptionPane.showMessageDialog(null, formedWord + " is not a valid English word!");
                    model.clearInvalidWord();
                    frame.enableHandButtons();
                }
            } else {
                System.out.println("Invalid coordinates: (" + y + ", " + x + ")");
            }
        }
    }
}