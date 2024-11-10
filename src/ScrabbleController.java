import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrabbleController implements ActionListener {

    private final ScrabbleBoardFrame frame;
    private final ScrabbleGame model;

    public ScrabbleController(ScrabbleBoardFrame frame, ScrabbleGame model) {
        this.frame = frame;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("play")) {

            if (!model.getGameStarted()) {
                boolean numPlayers = false;

                while (!numPlayers) {
                    int playerCount = Integer.parseInt(JOptionPane.showInputDialog("Select the number of players (2-4)?"));
                    if (playerCount < 2) {
                        JOptionPane.showMessageDialog(null,"Not enough players!");
                    } else if (playerCount > 4) {
                        JOptionPane.showMessageDialog(null,"Too many players!");
                    } else {
                        model.MVCparticipants(playerCount);
                        frame.enableComponents(frame.wordsInHandPanel.getComponents());
                        frame.enableComponents(frame.scoreText.getComponents());
                        model.updateViews();
                        numPlayers = true;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,"A game is already in progress!");
                return;
            }


        } else if (s.equals("help")) {
            JOptionPane.showMessageDialog(null,"Welcome to the game of Scrabble!\n" +
                    "Each player has the ability to select letters and place them on the board, but make sure that the letters are connecting to other letters on the board!\n" +
                    "After completing your word, you can click 'Submit' at the top to collect points based on the word you created.\n" +
                    "Players may also skip their turn by clicking 'Pass' at the top.\n" +
                    "Good luck!");
            return;
        }

        if (s.startsWith("h")) {
            JButton sourceButton = (JButton) e.getSource();
            String text = sourceButton.getText();
            model.setHandListCoord(s.substring(1));
            model.setTextPlayed(text);

            frame.enableComponents(frame.boardPanel.getComponents()); // Allow player to place their selected letter
            frame.disableComponents(frame.actionButtons.getComponents());

        } else {
            int y = -1, x = -1;

            if (s.length() == 2) {
                y = Character.getNumericValue(s.charAt(0));
                x = Character.getNumericValue(s.charAt(1));
            } else if (s.length() == 3) {
                if (Character.getNumericValue(s.charAt(0)) <= 1) {
                    y = Integer.parseInt(s.substring(0, 2));
                    x = Character.getNumericValue(s.charAt(2));
                } else {
                    y = Character.getNumericValue(s.charAt(0));
                    x = Integer.parseInt(s.substring(1, 3));
                }
            }

            if (y >= 0 && y < 15 && x >= 0 && x < 15) {
                if (model.board.getLetterOnBoard(7,7) == ' ') {
                    if (x != 7 || y != 7) {
                        JOptionPane.showMessageDialog(null, "Illegal move, please start from the board center: (Y:7, X:7)");
                        return;
                    }
                } else {
                    if (!isConnectedToOtherLetters(y, x)) {
                        JOptionPane.showMessageDialog(null, "Each letter must be connected to another letter on the board.");
                        return;
                    }
                }

                model.setxCoordinate(x);
                model.setyCoordinate(y);

                frame.disableComponents(frame.boardPanel.getComponents());
                frame.enableComponents(frame.actionButtons.getComponents()); // Allow player to select actions only after placing a letter

                model.MVCplayTurn(model.getCurrentPlayer(), x, y, model.getTextPlayed().charAt(0));
                model.updateViews();
            }
        }

        if (s.equals("submit")) {
            String formedWord = model.checkValidWord(model.getyCoordinate(), model.getxCoordinate());
            if (model.getParser().isValidWord(formedWord)) {
                JOptionPane.showMessageDialog(null, formedWord + " is a valid word!");
                model.getCurrentPlayer().calculateWordScore(formedWord);
                model.removeCharsFromHand();
                model.getCurrentPlayer().getHand().refillHand();
                model.InvalidChars.clear();
                model.turn++;
                model.updateViews();
                //frame.enableHandButtons();
                frame.enableComponents(frame.wordsInHandPanel.getComponents());
            } else {
                JOptionPane.showMessageDialog(null, formedWord + " is not a valid English word!");
                model.clearInvalidWord();
                String question = JOptionPane.showInputDialog("Are you done with your turn? (yes/no)");
                if (question != null && question.equalsIgnoreCase("yes")) {
                    model.clearInvalidWord();
                    model.setTextPlayed(" ");
                    model.turn++;
                    model.updateViews();
                    //frame.enableHandButtons();
                    frame.enableComponents(frame.wordsInHandPanel.getComponents());
                }
                model.setTextPlayed(" ");
                model.updateViews();
                //frame.enableHandButtons();
                frame.enableComponents(frame.wordsInHandPanel.getComponents());
            }
        }

        if (s.equals("pass")){
            model.turn++;
            frame.disableComponents(frame.boardPanel.getComponents());
            model.updateViews();
            //frame.enableHandButtons();
            frame.enableComponents(frame.wordsInHandPanel.getComponents());
        }
    }

    private boolean isConnectedToOtherLetters(int y, int x) {
        return (y > 0 && model.board.getLetterOnBoard(y - 1, x) != ' ') ||
                (y < 14 && model.board.getLetterOnBoard(y + 1, x) != ' ') ||
                (x > 0 && model.board.getLetterOnBoard(y, x - 1) != ' ') ||
                (x < 14 && model.board.getLetterOnBoard(y, x + 1) != ' ');
    }
}
