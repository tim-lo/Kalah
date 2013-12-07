Kalah
=====

The classic board game in its entirety, coded in Java.

The Files (Version 1)
---------------------

Split into three classes: Main, Player and House:

 * Main handles the core game mechanics; it takes care of the turns, players' moves, and decide if the game is won by any of the two players.
 * Player represents each of the two players in the game, providing core methods for setting moves and targets.
 * House represents the houses of the players, providing methods for bean manipulation.

The Files (Version 2)
---------------------

 * The Kalah class initilises the board, players etc. and handles core game functionalities, including rule checking.
 * The Board class contains the layout of the board and associated methods for getting and setting board data (beans in houses etc.)
 * The AI class covers the decision making of the AI players using a greedy variant of the minimax algorithm...
 * More updates on their way...
