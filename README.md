Scrabble Game
_____________

Description:
____________

This project contains 14 java classes, 1 UML diagram, and 1 ReadMe.txt file.
In this deliverable, we added the main functionalities of the Scrabble game which included: managing the bag of letters, board design and implementation, player handling, assigning hands of letters, word and letter handling, and gameplay logic and implementation.
To play the game, run the main function in Game class, choose how many players are playing and you're good to go !

The Project is made up of the following files:

GameTests.java          A single Java script

Bag,java                A single Java script

Board.java              A single Java script

Command.java            A single Java script

CommandWords.java       A single Java script

Hand.java               A single Java script

Parser.java             A single Java script

Player.java             A single Java script

ScrabbleGame.java       A single Java script

Square.java             A single Java script

ScrabbleBoardFrame.java A single Java script

ScrabbleController.java A single Java script

ScrabbleGame.java       A single Java script

ScrabbleView.java       A single Java script

UML diagram             A single image of a UML diagram


Installation:
-------------
To be able to run the program, you should have Java 15.0.0 or later installed on your
computer.

Usage:
------
Bag: this class stores all the letters provided in the game. It deals with hand distribution by ensuring that each player receives a randomized list of 7 letters.

Board: this class represents the board used in a Scrabble game. It is represented by a 15x15 grid consisting of squares that store letters. Its purpose is to allow for placement of letters and formation of words by the players, along with displaying the state of the board whenever it changes.

Square: this class represents the individual squares that form the scrabble board. Each square can be either empty or used to hold the user inputted letter which gets assigned a score by the provided letter score mechanism.

Command: this class is used to get the user command inputs generated with each turn.

CommandWords: this class is used to implement the game options provided with each player turn and correctly handling them by checking the player command validity.

Hand: this class handles the list of 7 letters provided to a player during the game. It incorporates adding, removing, refilling, and swapping functionalities for the given hand.

Player: this class represents the players participating in the game. Each player includes a name, score, list of formed words, and the hand provided. The class includes methods that manage the player attributes and implements a method that calculates the word score.

Game: this class implements the overall gameplay functionality and logic. It handles adding players, setting letters on the board, player turns, and forming valid words. It also provides the play method which combines the functionalities to generate a running a game of Scrabble.

Parser: this class handles user input throughout the game and checks for the validity of each user command and parses through the given set of English words to check if the user formed word is a Scrabble word.

GameTest: This class is made up of the test cases.


>Iteration1:

Kareem Kaddoura: Board.java, Command.java, CommandWords.java, Parser.java, ScrabbleGame.java, Square.java
Jalal Mourad: Bag.java, Board.java, Hand.java, Parser.java, Player.java, ScrabbleBoardFrame.java, ScrabbleController.java, ScrabbleGame.java, ScrabbleView.java
Ishaq Nour: Bag.java, Board.java, GameTest.java, Hand.java, ScrabbleGame.java, README.md, UML diagram


Copyright 2024 Jalal Mourad, Kareem Kaddoura, Ishaq Nour
