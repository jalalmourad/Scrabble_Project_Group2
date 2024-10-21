import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrabbleController implements ActionListener {

    ScrabbleBoardFrame frame;
    ScrabbleGame model;
   // String str = "";
   // JButton selectedHandButton = null;

    public ScrabbleController(ScrabbleBoardFrame frame,ScrabbleGame model){
        this.frame = frame;
        this.model = model;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();


        if (s.equals("play")){

            int playerNumber;
            playerNumber= Integer.parseInt(JOptionPane.showInputDialog("Select the number of players (2-4)?"));
            model.MVCparticipants(playerNumber);

            frame.update(model);

        }
        if (s.equals("hand")){


            JButton sourceButton = (JButton) e.getSource();
            String text;
           text = sourceButton.getText();
           int x = Integer.parseInt(JOptionPane.showInputDialog("Select the X coordinate: "));
           int y = Integer.parseInt(JOptionPane.showInputDialog("Select the Y coordinate: "));
           model.setPlayedChar(text.charAt(0));
           model.setxCoordinate(x);
           model.setyCoordinate(y);

           model.MVCplayTurn(model.getCurrentPlayer(),x,y,text.charAt(0));
           frame.update(model);

           //Only issue for now is the placeAnother = JOptionPane.showInputDialog("Do you want to place another letter? (yes/no): ");

        }


    }
}
