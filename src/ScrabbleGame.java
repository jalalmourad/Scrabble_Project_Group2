import javax.swing.*;
import java.io.*;
import java.util.*;

public class ScrabbleGame implements Serializable{

    List<Player> players;
    Bag bag;
    Board board;
    Parser parser;
    int turn = 0;
    Hand playerHand;

    int xCoordinate;
    int yCoordinate;
    ArrayList<ScrabbleView> views;

    List<int[]> placedPositions;
    List<int[]> removedChars;
    List<int[]> InvalidChars;

    Stack<int[]> undoStack;
    Stack<int[]> redoStack;

    private String bestAIWord = "";

    String handListCoord;
    String text;
    private HashMap<String, Boolean> blankTileLetters;
    private boolean invalidFlag = false;
    boolean gameStarted = false;

    //List<Object> game =

    /**
     * Creates the game, initializes players, board, bag, views, etc.
     */
    public ScrabbleGame() {
        players = new ArrayList<>();
        removedChars = new ArrayList<>();
        InvalidChars = new ArrayList<>();
        bag = new Bag();
        chooseBoard("Normal Board"); // Default Choice
        //board = new Board();
        parser = new Parser();

        views = new ArrayList<>();

        placedPositions = new ArrayList<>();

        undoStack = new Stack<>();
        redoStack = new Stack<>();

        blankTileLetters = new HashMap<>();

        parser.loadWordsFromFile("src/Dictionary.txt");
    }

    /**
     * Add views for MVC
     */
    public void addView(ScrabbleView view){
        views.add(view);
    }

    /**
     * Updates the views for all the subscribed viewers
     */

    public void updateViews(){
        for (ScrabbleView v : views){
            v.update(this);
        }
    }

    /**
     * Adds players to the game, used by Controller, changes seen in Frame
     */

    public void MVCparticipants(int n, String[] playerTypes) {
        for (int i = 0; i < n; i++) {
            int j = i + 1;
            String playerName;

            if (playerTypes[i].equals("Human")) {
                playerName = JOptionPane.showInputDialog("Enter player " + j + " Name: ");
            } else {
                playerName = "AI " + j;
            }

            Player player = new Player(playerName);

            if (playerTypes[i].equals("AI")) {
                player.setAI(true);
            }

            player.getHand().setLetters(bag.getAlphabet());
            players.add(player);
        }
        gameStarted = true;
    }

    /**
     * Plays the player's turn, used by other classes
     */
    public void MVCplayTurn(Player currentPlayer, int x, int y, char letter) {

        setLetterOnBoard(y, x, letter, currentPlayer);
        placedPositions.add(new int[]{x, y});
        InvalidChars.add(new int[]{x, y});
        board.printBoard();
    }

    /**
     * Set a letter on the board for a player.
     */
    public void setLetterOnBoard(int y, int x, char letter, Player player) {
        playerHand = player.getHand();
        System.out.println("Letter Placed is: "+letter+"\n");
        board.setLetterOnBoard(y,x,letter);
        int letterIndex = playerHand.getLetterPosition(letter);
        removedChars.add(new int[]{letterIndex, letter});
        placedPositions.add(new int[]{x, y});

        undoStack.push(new int[]{x, y, letter});
        redoStack.clear();
    }

    /**
     * Remove chars from Player's hand
     */
    public void removeCharsFromHand() {
        for (int[] indexLetterPair : removedChars) {
            char letter = (char) indexLetterPair[1];

            if (playerHand.getLetters().contains(letter)) {
                playerHand.getLetters().remove((Character) letter);
            }
        }
        removedChars.clear();
    }

    /**
     * Gets the turn of the current player
     */
    public Player getCurrentPlayer(){
        return players.get(turn%players.size());
    }

    /**
     * Checks if the word placed is a valid word
     */
    public String checkValidWord(int y, int x) {

        StringBuilder word = new StringBuilder();

        for(int i = x; i >= 0 && board.getLetterOnBoard(y,i) != ' '; i--) {
            word.insert(0,board.getLetterOnBoard(y, i));
        }
        for(int i = x + 1; i < 15 && board.getLetterOnBoard(y,i) != ' '; i++) {
            word.append(board.getLetterOnBoard(y,i));
        }
        for(int i = y - 1; i >= 0 && board.getLetterOnBoard(i,x) != ' '; i--) {
            word.insert(0,board.getLetterOnBoard(i,x));
        }
        for(int i = y + 1; i < 15 && board.getLetterOnBoard(i,x) != ' '; i++) {
            word.append(board.getLetterOnBoard(i,x));
        }
        return word.toString();
    }

    /**
     * Checks type of square for each letter placed
     */
    public List<String> checkSquareTypes(int y, int x) {
        ArrayList<String> squareTypes = new ArrayList<>();

        for(int i = x; i >= 0 && board.getLetterOnBoard(y,i) != ' '; i--) {
            squareTypes.add(0, board.getSquareType(y, i));
        }
        for(int i = x + 1; i < 15 && board.getLetterOnBoard(y,i) != ' '; i++) {
            squareTypes.add(board.getSquareType(y,i));
        }
        for(int i= y - 1; i >= 0 && board.getLetterOnBoard(i,x) != ' '; i--) {
            squareTypes.add(0,board.getSquareType(i,x));
        }
        for(int i = y + 1; i < 15 && board.getLetterOnBoard(i,x) != ' '; i++) {
            squareTypes.add(board.getSquareType(i,x));
        }
        return squareTypes;
    }

    /**
     * Clears the invalid word placed by Player
     */
    public void clearInvalidWord() {
        for (int[] pos : InvalidChars) {
            int x = pos[0];
            int y = pos[1];
            char letter = board.getLetterOnBoard(y, x);

            board.setDeleteLetterFromBoard(y, x, ' ');
            getCurrentPlayer().getHand().addLetter(letter);
        }
        InvalidChars.clear();
        invalidFlag = true;
        updateViews();
    }

    /**
     * Generate all possible combinations of letters from AI's hand
     */
    public ArrayList<String> generateAiCombinations(List<Character> letters, int index, StringBuilder current) {
        ArrayList<String> combinations = new ArrayList<>();

        if (current.length() > 0) {
            combinations.add(current.toString());
        }

        for (int i = index; i < letters.size(); i++) {
            current.append(letters.get(i));
            combinations.addAll(generateAiCombinations(letters, i + 1, current));
            current.deleteCharAt(current.length() - 1);

        }

        return combinations;
    }

    /**
     * Filter out invalid words from all possible combinations of letters from AI's hand
     */
    public ArrayList<String> checkAiCombinations(ArrayList<String> combinations) {

        Iterator<String> iterator = combinations.iterator();
        while (iterator.hasNext()) {
            String combination = iterator.next();
            if (!parser.isValidWord(combination)) {
                iterator.remove();
            }
        }
        System.out.println("Valid AI words: " + combinations);
        return combinations;
    }

    private List<String> placeWordOnBoard(String word, int startY, int startX, boolean isVertical) {
        List<String> squareTypes = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            int y;
            int x;
            if (isVertical) {
                y = startY + i;
                x = startX;
            } else {
                y = startY;
                x = startX + i;
            }

            board.setDeleteLetterFromBoard(y, x, word.charAt(i));
            squareTypes.add(board.getSquareType(y, x));
        }

        System.out.println("AI placed the word: " + word);
        return squareTypes;
    }


    private boolean isHorizontalCrossWordValid(char letter, int y, int x) {
        StringBuilder crossWord = new StringBuilder();
        for (int i = x; i >= 0 && board.getLetterOnBoard(y, i)!= ' ';i--) {
            crossWord.insert(0, board.getLetterOnBoard(y,i));
        }
        for (int i = x + 1; i < 15 && board.getLetterOnBoard(y, i) != ' ';i++) {
            crossWord.append(board.getLetterOnBoard(y, i));
        }
        return crossWord.length() <=1 ||parser.isValidWord(crossWord.toString());
    }

    private boolean isVerticalCrossWordValid(char letter, int y, int x) {
        StringBuilder crossWord = new StringBuilder();

        for (int i= y;i >= 0 && board.getLetterOnBoard(i,x) != ' ';i--) {
            crossWord.insert(0, board.getLetterOnBoard(i, x));
        }
        for (int i= y +1;i< 15 && board.getLetterOnBoard(i,x) != ' ';i++) {
            crossWord.append(board.getLetterOnBoard(i, x));
        }
        return crossWord.length() <= 1 || parser.isValidWord(crossWord.toString());
    }

    //This method checks if the word can be placed on board
    private boolean canPlaceWordOnBoard(String word,int startY,int startX, boolean isVertical) {

        int length = word.length();
        if (isVertical){
            if (startY +length > 15) return false;
        } else {
            if (startX + length> 15) return false;
        }
        boolean isConnected = false;
        for (int i = 0; i < length; i++) {

            int x, y;
            if (isVertical){
                x = startX;
                y = startY+ i;
            } else {
                x = startX + i;
                y = startY;
            }
            char existingLetter = board.getLetterOnBoard(y, x);
            if (existingLetter != ' ' && existingLetter != word.charAt(i)) {
                return false;
            }
            if (existingLetter != ' '){
                isConnected = true;
            }
            if (!isVertical){
                if (!isHorizontalCrossWordValid(word.charAt(i), y,x)){ //Checks if the letter is connected to another letter horizontally
                    return false;
                }
            }
            else {
                if(!isVerticalCrossWordValid(word.charAt(i), y, x)){ //Checks if the letter is connected to another letter vertically
                    return false;
                }
            }
        }
        return isConnected || isBoardEmpty();
    }

    private boolean isBoardEmpty(){ //Checks if the board is empty
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if (board.getLetterOnBoard(y, x) != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check for the highest scoring word
     */
    public void aiHighestScoreWord() { //This method returns the highest AI scoring word


        List<String> aiWords = checkAiCombinations(generateAiCombinations(getCurrentPlayer().getHand().getLetters(), 0, new StringBuilder())); //Generate a list of all possible hand combinations

        int maxScore = 0;
        String bestWord = "";
        int bestY = -1, bestX = -1;
        boolean bestIsVertical = false;

        for (String word : aiWords) {
            for (int y = 0; y < 15; y++) {
                for (int x = 0; x < 15; x++) {
                    if (canPlaceWordOnBoard(word, y, x, false)) {
                        int score = getCurrentPlayer().getWordScore(word); //, checkSquareTypes(y, x));
                        if (score > maxScore) {
                            maxScore = score;
                            bestWord = word;
                            bestAIWord = word;
                            bestY = y;
                            bestX = x;
                            bestIsVertical = false;
                        }
                    }
                    if (canPlaceWordOnBoard(word,y, x,true)) {
                        int score = getCurrentPlayer().getWordScore(word); //checkSquareTypes(y, x));
                        if (score > maxScore) {
                            maxScore = score;
                            bestWord = word;
                            bestAIWord = word;
                            bestY = y;
                            bestX = x;
                            bestIsVertical = true;
                        }
                    }
                }
            }
        }


        if (!bestWord.isEmpty()) { //checks if the highest scoring word is empty
            List<String> formedSquares = placeWordOnBoard(bestWord, bestY, bestX, bestIsVertical);
            getCurrentPlayer().removeLettersFromHand(getBestAIWord());
            getCurrentPlayer().getHand().refillHand();

            playAiTurn(getBestAIWord(), formedSquares);
            JOptionPane.showMessageDialog(null, "AI has placed: " + getBestAIWord());
        } else {

            JOptionPane.showMessageDialog(null, "Passing turn since the AI could not find a valid position to place any word.");
        }

    }

    public void playAiTurn(String bestAIWord, List<String> aiSquares) {
        if (!getBestAIWord().isEmpty()) {
            getCurrentPlayer().calculateWordScore(bestAIWord, aiSquares);
        }
    }

    /**
     * Save and export the game via serialization
     * [Milestone 4]
     */
    public void save(String filename) throws IOException {

        try (FileOutputStream fileOutput = new FileOutputStream(filename);
             ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {

            objectOutput.writeObject(this);

            System.out.println("Game Saved Successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game:" + e.getMessage());
            throw e;
        }
    }

    /**
     * Load and import a game via serialization
     * [Milestone 4]
     */
    public void load(String filename) {
        try (FileInputStream fileInput = new FileInputStream(filename);
             ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {

            ScrabbleGame loadedGame = (ScrabbleGame) objectInput.readObject();

            this.players = loadedGame.players;
            this.bag = loadedGame.bag;
            this.board = loadedGame.board;
            this.parser = loadedGame.parser;
            this.turn = loadedGame.turn;
            this.playerHand = loadedGame.playerHand;
            this.xCoordinate = loadedGame.xCoordinate;
            this.yCoordinate = loadedGame.yCoordinate;
            this.views = loadedGame.views;
            this.placedPositions = loadedGame.placedPositions;
            this.removedChars = loadedGame.removedChars;
            this.InvalidChars = loadedGame.InvalidChars;
            this.bestAIWord = loadedGame.bestAIWord;
            this.handListCoord = loadedGame.handListCoord;
            this.text = loadedGame.text;
            this.blankTileLetters = loadedGame.blankTileLetters;
            this.invalidFlag = loadedGame.invalidFlag;
            this.gameStarted = loadedGame.gameStarted;
            updateViews();


            System.out.println("Game Loaded Successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
    }

    public void importScrabbleGame(ScrabbleGame loadedGame) {
        this.players = loadedGame.players;
        this.bag = loadedGame.bag;
        this.board = loadedGame.board;
        this.parser = loadedGame.parser;
        this.turn = loadedGame.turn;
        this.playerHand = loadedGame.playerHand;
        this.xCoordinate = loadedGame.xCoordinate;
        this.yCoordinate = loadedGame.yCoordinate;
        this.placedPositions = loadedGame.placedPositions;
        this.removedChars = loadedGame.removedChars;
        this.InvalidChars = loadedGame.InvalidChars;
        this.bestAIWord = loadedGame.bestAIWord;
        this.handListCoord = loadedGame.handListCoord;
        this.text = loadedGame.text;
        this.blankTileLetters = loadedGame.blankTileLetters;
        this.invalidFlag = loadedGame.invalidFlag;
        this.gameStarted = loadedGame.gameStarted;

        this.views.clear();
        this.views.addAll(loadedGame.views);
        updateViews();
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("No actions to undo.");
            return;
        }

        int[] previousMove = undoStack.pop();
        redoStack.push(previousMove);

        int x = previousMove[0];
        int y = previousMove[1];
        //char letter = (char) previousMove[2];

        board.setDeleteLetterFromBoard(y, x, ' ');
        setTextPlayed(" ");

        System.out.println("Undo done!");
        board.printBoard();
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("No actions to redo.");
            return;
        }

        int[] previousMove = redoStack.pop();
        undoStack.push(previousMove);

        int x = previousMove[0];
        int y = previousMove[1];
        char letter = (char) previousMove[2];

        board.setLetterOnBoard(y, x, letter);
        setTextPlayed(String.valueOf(letter));

        System.out.println("Redo done!");
        board.printBoard();
    }

    /**
     * Create a board depending on user selection
     */
    public void chooseBoard(String selection) {
        switch (selection) {
            case "Normal" -> board = new Board("Normal");
            case "Target" -> board = new Board("Target");
            case "Spiral" -> board =  new Board("Spiral");
            case "Boring" -> board = new Board("Boring");
        }
    }



    /**
     * Following miscellaneous methods are used throughout the program as getters, setters, etc.
     */

    public String getHandListCoord(){
        return handListCoord;
    }

    public void setHandListCoord(String handListCoord){
        this.handListCoord = handListCoord;
    }

    public void setTextPlayed(String text) {
        this.text = text;
    }

    public String getTextPlayed() {
        return text;
    }

    public void addBlankTileLetters(String text, boolean bool) {
        blankTileLetters.put(text, bool);
    }

    public boolean isBlankTileLetter(String text) {
        return blankTileLetters.getOrDefault(text, false);
    }

    public boolean invalidFlag() {
        return invalidFlag;
    }

    public void resetInvalidFlag() {
        invalidFlag = false;
    }

    Parser getParser(){
        return parser;
    }

    public boolean getGameStarted() {
        return gameStarted;
    }

    public void setxCoordinate(int xCoordinate){
        this.xCoordinate = xCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
    public int getyCoordinate(){
        return yCoordinate;
    }

    public String getBestAIWord() {
        return bestAIWord;
    }

    public char getLatestUndoLetter() {
        if (!undoStack.isEmpty()) {
            return (char) undoStack.peek()[2];
        }
        return ' ';
    }

    public char getLatestRedoLetter() {
        if (!redoStack.isEmpty()) {
            return (char) redoStack.peek()[2];
        }
        return ' ';
    }
}