import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class ScrabbleController implements ActionListener, Serializable {

    private final ScrabbleBoardFrame frame;
    private final ScrabbleGame model;

    public ScrabbleController(ScrabbleBoardFrame frame, ScrabbleGame model) {
        this.frame = frame;
        this.model = model;
    }

    /**
     * Accounts for all possible actions performed by the user(s)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        // Menu actions
        if (s.equals("play")) {
            if (!model.getGameStarted()) {
                frame.gameSetup();

                model.updateViews();
                frame.enableComponents(frame.wordsInHandPanel.getComponents());
                frame.enableComponents(frame.scoreText.getComponents());
                frame.playerTurnText.setEnabled(true);
                model.updateViews();
            } else {
                JOptionPane.showMessageDialog(null,"A game is already in progress!");
                return;
            }
        } else if (s.equals("help")) {
            JOptionPane.showMessageDialog(null,"Welcome to the game of Scrabble!\n" +
                    "- Each player has the ability to select letters and place them on the board, but make sure that the letters are connecting to other letters on the board!\n" +
                    "- After completing your word, you can click 'Submit' at the top to collect points based on the word you created.\n" +
                    "- Players may also skip their turn by clicking 'Pass' at the top.\n" +
                    "- Players may also undo/redo the letters they have placed.\n" +
                    "- Clicking on 'Letter Values' in the menu can show players value of each letter.\n" +
                    "- Letters placed on CYAN-colored squares are worth DOUBLE.\n" +
                    "- Letters placed on BLUE-colored squares are worth TRIPLE.\n" +
                    "- Any word placed on a PINK-colored square becomes worth DOUBLE.\n" +
                    "- Any word placed on a RED-colored square becomes worth TRIPLE.\n\n" +
                    "Good luck!");
            return;
        } else if (s.equals("values")) {
            JOptionPane.showMessageDialog(null, "1 point: A, E, I, O, U, L, N, S, T, R\n" +
                    "2 points: D, G\n" +
                    "3 points: B, C, M, P\n" +
                    "4 points: F, H, V, W, Y\n" +
                    "5 points: K\n" +
                    "8 points: J, X\n" +
                    "10 points: Q, Z\n" +
                    "0 points: Blank Tiles");
            return;

        } else if (s.equals("save")) {
            try {
                model.save("scrabble_game.ser");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            frame.saveGame();

        } else if (s.equals("load")) {
            model.load("scrabble_game.ser");
            frame.loadGame();
            model.updateViews();
            frame.enableComponents(frame.boardPanel.getComponents());
            frame.enableComponents(frame.wordsInHandPanel.getComponents());
            frame.enableComponents(frame.submitButton.getComponents());

        } else if (s.equals("undo")) {
            frame.enableButton(frame.redoButton);
            frame.enableLetterButton(model.getLatestUndoLetter(), frame.wordsInHandPanel.getComponents());
            model.undo();
            model.setHandListCoord(null);
            model.updateViews();

        } else if (s.equals("redo")) {
            frame.disableLetterButton(model.getLatestRedoLetter(), frame.wordsInHandPanel.getComponents());
            model.redo();
            model.updateViews();
        }

        if (s.startsWith("h")) {
            JButton sourceButton = (JButton) e.getSource();
            String text = sourceButton.getText();
            model.setHandListCoord(s.substring(1));

            if (text.equals(" ")) { // Blank tile has been selected
                Character[] blankTileOptions = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}; // Letter options to replace a blank tile
                int blankTileLetter = JOptionPane.showOptionDialog(null, "What letter would you like your blank tile to represent?", "Scrabble!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, blankTileOptions, blankTileOptions[0]);
                char selectedBlankTileLetter = blankTileOptions[blankTileLetter];

                model.setTextPlayed(String.valueOf(selectedBlankTileLetter));
                model.addBlankTileLetters(String.valueOf(selectedBlankTileLetter), true);
            } else { // Regular tile has been selected
                model.setTextPlayed(text);
                model.addBlankTileLetters(text, false);
            }

            frame.enableComponents(frame.boardPanel.getComponents()); // Allow player to place their selected letter
            frame.disableComponents(frame.actionButtons.getComponents());

        } else { // Placing letter action
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

        // Action bar actions
        if (s.equals("submit")) {
            String formedWord = model.checkValidWord(model.getyCoordinate(), model.getxCoordinate());
            List<String> formedSquares = model.checkSquareTypes(model.getyCoordinate(), model.getxCoordinate());

            // Check to see if the formedWord actually contains any blank tiles, stored in tempWord
            String tempWord = formedWord;
            for (int i = 0; i < formedWord.length(); i++) {
                if (model.isBlankTileLetter(String.valueOf(formedWord.charAt(i)))) {
                    tempWord = tempWord.replace(formedWord.charAt(i), ' ');
                }
                // Otherwise leave AS IS
            }

            if (model.getParser().isValidWord(formedWord)) { // Original version of the word (no blank tiles)
                JOptionPane.showMessageDialog(null, formedWord + " is a valid word!");
                model.getCurrentPlayer().calculateWordScore(tempWord, formedSquares);
                model.removeCharsFromHand();
                model.getCurrentPlayer().getHand().refillHand();
                model.InvalidChars.clear();
                model.undoStack.clear();
                model.redoStack.clear();
                model.turn++;
                model.updateViews();
                frame.enableComponents(frame.wordsInHandPanel.getComponents());
                frame.disableButton(frame.submitButton);
            } else {
                JOptionPane.showMessageDialog(null, formedWord + " is not a valid English word!");
                model.clearInvalidWord();
                int question = JOptionPane.showConfirmDialog(null, "Would you like to continue your turn?", "", JOptionPane.YES_NO_OPTION);
                if (question == JOptionPane.YES_OPTION) {
                    model.setTextPlayed(" ");
                    model.updateViews();
                    frame.enableComponents(frame.wordsInHandPanel.getComponents());
                } else if (question == JOptionPane.NO_OPTION) {
                    model.clearInvalidWord();
                    model.setTextPlayed(" ");
                    model.turn++;
                    model.updateViews();
                    frame.enableComponents(frame.wordsInHandPanel.getComponents());
                }
            }
        }

        if (s.equals("pass")) {
            model.turn++;
            frame.disableComponents(frame.boardPanel.getComponents());
            model.updateViews();
            frame.enableComponents(frame.wordsInHandPanel.getComponents());
        }

        completeTurn();
    }

    /**
     * Checks to ensure that a placed letter is connected to another letter that has already been placed on the board
     * Complicit with Scrabble rules
     */
    private boolean isConnectedToOtherLetters(int y, int x) {
        return (y > 0 && model.board.getLetterOnBoard(y - 1, x) != ' ') ||
                (y < 14 && model.board.getLetterOnBoard(y + 1, x) != ' ') ||
                (x > 0 && model.board.getLetterOnBoard(y, x - 1) != ' ') ||
                (x < 14 && model.board.getLetterOnBoard(y, x + 1) != ' ');
    }

    /**
     * Used for end-of-turn actions, like triggering AI's turn or disabling buttons
     */
    private void completeTurn() {
        if (model.undoStack.isEmpty()) { // Deactivates undo button if max redo have been performed
            frame.disableButton(frame.undoButton);
        }
        if (model.redoStack.isEmpty()) { // Ensures redo button remains inactive unless undo has been used
            frame.disableButton(frame.redoButton);
        }
        while (model.getCurrentPlayer().isAI()) {
            model.aiHighestScoreWord();
            model.turn++;
            model.updateViews();
            frame.enableComponents(frame.wordsInHandPanel.getComponents());
        }
    }

}
