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
        //JButton sourceButton = (JButton) e.getSource();

        if (s.equals("play")){

            int playerNumber;
            playerNumber= Integer.parseInt(JOptionPane.showInputDialog("Select the number of players (2-4)?"));
            model.MVCparticipants(playerNumber);

            frame.update(model);

        }

//        if (s.equals("hand")){
//            Player player = new Player("Test");
//            model.addPlayers(player);
//
//            str = sourceButton.getText();
//            selectedHandButton = sourceButton;
//
//            sourceButton.setText("");
//
//            frame.update(model);
//        }
//
//        if (s.equals("board")){
//            sourceButton.setText(str);
//            str = "";
//
//            if(selectedHandButton!=null){
//                selectedHandButton.setText("");
//                selectedHandButton = null;
//            }
            //frame.update(model);

        //}

    }
}
