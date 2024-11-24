Scrabble! Game
----------------

Description:
----------------

This project contains 13 java classes, 2 UML diagrams, 1 Dictionary.txt file, and 1 ReadMe.txt file.

Milestone 1:
In this deliverable, we added the main functionalities of the Scrabble game which included: managing the bag of letters, board design and implementation, player handling, assigning hands of letters, word and letter handling, and gameplay logic and implementation.
To play the game, run the main function in Game class, choose how many players are playing, and you're good to go !

Milestone 2:
In this deliverable, the Model-View-Controller design pattern was implemented into the existing program to create a greater user-friendly visual experience. With the changes implemented, users can now interact with the Scrabble board through mouse-click inputs to play the game. Letters are now easier to place, as users can simply click on their desired square rather than input coordinates in contrast to the previous milestone. To play the game, run the 'main' method in the ScrabbleBoardFrame.java class and follow the instructions!

Milestone3: 
In this deliverable, we added blank tiles, premium squares, and the ability to use any number of “AI” players.

The Project is made up of the following files:

Bag,java                A single Java script

Board.java              A single Java script

Command.java            A single Java script

CommandWords.java       A single Java script

GameTests.java          A single Java script

Hand.java               A single Java script

Parser.java             A single Java script

Player.java             A single Java script

ScrabbleGame.java       A single Java script

Square.java             A single Java script

ScrabbleBoardFrame.java A single Java script

ScrabbleController.java A single Java script

ScrabbleView.java       A single Java script

UML_M1.png              A single image of a UML diagram for Milestone 1

UML_M2.png              A single image of a UML diagram for Milestone 2


Installation:
----------------
To be able to run the program, you should have Java 15.0.0 or later installed on your computer.

Usage:
----------------
Bag: This class stores all the letters provided in the game. It deals with hand distribution by ensuring that each player receives a randomized list of 7 letters.

Board: This class represents the board used in a Scrabble game. It is represented by a 15x15 grid consisting of squares that store letters. Its purpose is to allow for placement of letters and formation of words by the players, along with displaying the state of the board whenever it changes.

Command: This class is used to get the user command inputs generated with each turn.

CommandWords: This class is used to implement the game options provided with each player turn and correctly handling them by checking the player command validity.

GameTests: This class is made up of the test cases.

Hand: This class handles the list of 7 letters provided to a player during the game. It incorporates adding, removing, refilling, and swapping functionalities for the given hand.

Parser: This class handles user input throughout the game and checks for the validity of each user command and parses through the given set of English words to check if the user formed word is a Scrabble word.

Player: This class represents the players participating in the game. Each player includes a name, score, list of formed words, and the hand provided. The class includes methods that manage the player attributes and implements a method that calculates the word score.

ScrabbleGame: This class implements the overall gameplay functionality and logic. It handles adding players, setting letters on the board, player turns, and forming valid words. It also provides the play method which combines the functionalities to generate a running a game of Scrabble.

Square: This class represents the individual squares that form the scrabble board. Each square can be either empty or used to hold the user inputted letter which gets assigned a score by the provided letter score mechanism.

ScrabbleBoardFrame: This class is responsible for the creation of the JFrame that users will be visually seeing upon running the game. The board and its clickable buttons are initialized through here.

ScrabbleController: This class is responsible for the communication between the Model and the Controller, and processes user input(s) accordingly.

ScrabbleView: This class is used by the Model to update the views seen throughout the game's length.


> Iteration 1:

Kareem Kaddoura: Board.java, Command.java, CommandWords.java, Parser.java, ScrabbleGame.java, Square.java
Jalal Mourad: Bag.java, Board.java, Hand.java, Parser.java, Player.java, ScrabbleBoardFrame.java, ScrabbleController.java, ScrabbleGame.java, ScrabbleView.java
Ishaq Nour: Bag.java, Board.java, GameTest.java, Hand.java, ScrabbleGame.java, README.md, UML diagram

> Iteration 2:

Kareem Kaddoura: ScrabbleGame.java, ScrabbleView.java, ScrabbleController.java, ScrabbleBoardFrame.java, Refactoring changes
Jalal Mourad: ScrabbleGame.java, ScrabbleView.java, ScrabbleController.java, ScrabbleBoardFrame.java, Sequence diagram
Ishaq Nour: ScrabbleGame.java, ScrabbleView.java, ScrabbleController.java, ScrabbleBoardFrame.java, UML diagram

> Iteration 3:

Kareem Kaddoura: ScrabbleGame.java, ScrabbleController.java, ScrabbleBoardFrame.java
Jalal Mourad: ScrabbleGame.java, ScrabbleController.java, ScrabbleBoardFrame.java, README.txt
Ishaq Nour: ScrabbleGame.java, ScrabbleView.java, ScrabbleController.java, ScrabbleBoardFrame.java, UML diagram

Copyright 2024 Jalal Mourad, Kareem Kaddoura, Ishaq Nour
