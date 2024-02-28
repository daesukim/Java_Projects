import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.XMLFormatter;

public class TicTacToe {
    // Static variables for the TicTacToe class, effectively configuration options
    // Use these instead of hard-coding ' ', 'X', and 'O' as symbols for the game
    // In other words, changing one of these variables should change the program
    // to use that new symbol instead without breaking anything
    // Do NOT add additional static variables to the class!
    static int size = 3;
    static char emptySpaceSymbol = ' ';
    static char playerOneSymbol = 'X';
    static char playerTwoSymbol = 'O';

    public static void main(String[] args) {
        // TODO
        // This is where the main menu system of the program will be written.
        // Hint: Since many of the game runner methods take in an array of player names,
        // you'll probably need to collect names here.
        int currentPlayer = 0;
        boolean isMenuValid = false;
        boolean repeat = true;
        char[][] state = new char[3][3];
        String[] playerNameArray = new String[2]; // string array to save name of the players
        String[] onePlayerNameArray = new String[2];
        Scanner in = new Scanner(System.in);
        ArrayList<char[][]> archive = new ArrayList<>();

        boolean firstPlayerIsChose = false;
        boolean twoPlayerIsChose = false;

        while(repeat){
            while(!isMenuValid){
                System.out.println("Welcome to game of Tic Tac Toe, choose one of the following options from below: ");
                System.out.println("1. Single player");
                System.out.println("2. Two player");
                System.out.println("D. Display last match");
                System.out.println("Q. Quit");
                System.out.print("What do you want to do: ");
                String gameOption = in.next().toUpperCase();
                if (gameOption.equals("1")){
                    isMenuValid = true;
                    System.out.print("Enter player 1 name: ");
                    onePlayerNameArray[0] = in.next();
                    onePlayerNameArray[1] = "Computer";
                    archive = runOnePlayerGame(onePlayerNameArray);
                    firstPlayerIsChose = true;
                }
                else if (gameOption.equals("2")) {
                    isMenuValid = true;
                    System.out.print("Enter player 1 name: ");
                    String firstPlayer = in.next();
                    playerNameArray[0] = firstPlayer;
                    System.out.print("Enter player 2 name: ");
                    String secondPlayer = in.next();
                    playerNameArray[1] = secondPlayer;
                    archive = runTwoPlayerGame(playerNameArray);
                    twoPlayerIsChose = true;
                }
                else if (gameOption.equals("D")){
                    if (firstPlayerIsChose){
                        runGameHistory(onePlayerNameArray, archive);
                    }
                    else if (twoPlayerIsChose){
                        runGameHistory(playerNameArray, archive);
                    }
                    isMenuValid = true;
                }
                else if (gameOption.equals("Q")){
                    System.out.println("Thanks for playing. Hope you had fun!");
                    isMenuValid = true;
                    repeat = false;
                    break;
                }
                isMenuValid = false;
            }
        }
    }

    // Given a state, return a String which is the textual representation of the tic-tac-toe board at that state.
    private static String displayGameFromState(char[][] state) {
        String result = "";

        for (int i = 0; i <= state.length - 1; i++){
            for (int j = 0; j < state[i].length ; j++){
                if (j <= 1) {
                    result = result + " " + state[i][j] + " " + "|";
                }
                else{
                    result = result + " " + state[i][j];
                }
            }
            if (i <= state.length-2) {
                result = result + "\n-----------\n";
            }
        }
        return result;
    }

    // Returns the state of a game that has just started.
    // This method is implemented for you. You can use it as an example of how to utilize the static class variables.
    // As you can see, you can use it just like any other variable, since it is instantiated and given a value already.
    private static char[][] getInitialGameState() {
        return new char[][]{{emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}};
    }

    // Given the player names, run the two-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runTwoPlayerGame(String[] playerNames) {
        String currentPlayer = playerNames[0];
        char[][] currentState = getInitialGameState();
        ArrayList<char[][]> result = new ArrayList<>();

        System.out.println("Tossing a coin to decide who goes first!!!");
        if (Math.random() > 0.5) {
            currentPlayer = playerNames[0];
            System.out.println(currentPlayer + " gets to go first.");
            System.out.println(displayGameFromState(currentState));
            System.out.println();

        }
        else {
            currentPlayer = playerNames[1];
            System.out.println(playerNames[1] + " gets to go first.");
            System.out.println(displayGameFromState(currentState));
            System.out.println();
        }

        int[] move = new int[2];
        boolean gameOver = false;

        while (!gameOver) {
            if (checkWin(currentState)){
                gameOver = true;
                if (currentPlayer.equals(playerNames[0])){
                    System.out.println(playerNames[1] + " wins!");
                }
                else {
                    System.out.println(playerNames[0] + " wins!");
                }
            }
            else if (checkDraw(currentState)){
                gameOver = true;
                System.out.println("It's a draw!");
            }
            else{
                if (currentPlayer.equals(playerNames[0])){
                    System.out.println(currentPlayer + "'s turn: ");
                    move = getInBoundsPlayerMove(playerNames[0]);
                    while(!checkValidMove(move, currentState)){
                        move = getInBoundsPlayerMove(playerNames[0]);
                    }
                    System.out.println(displayGameFromState(makeMove(move, playerOneSymbol, currentState)));
                    currentState = makeMove(move, playerOneSymbol, currentState);
                    currentPlayer = playerNames[1];
                    result.add(currentState);
                }
                else{
                    System.out.println(currentPlayer + "'s turn: ");
                    move = getInBoundsPlayerMove(playerNames[1]);
                    while(!checkValidMove(move, currentState)){
                        move = getInBoundsPlayerMove(playerNames[1]);
                    }
                    System.out.println(displayGameFromState(makeMove(move, playerTwoSymbol, currentState)));
                    currentState = makeMove(move, playerTwoSymbol, currentState);
                    currentPlayer = playerNames[0];
                    result.add(currentState);
                }
            }
        }
        return result;
    }

    // Given the player names (where player two is "Computer"),
    // Run the one-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runOnePlayerGame(String[] playerNames) {
        // TODO
        char[][] onePlayerCurrentState = getInitialGameState();
        ArrayList<char[][]> result = new ArrayList<>();
        String currentPlayer = playerNames[0];

        System.out.println("Tossing a coin to decide who goes first!!!");
        if (Math.random() > 0.5) {
            currentPlayer = playerNames[0];
            System.out.println(currentPlayer + " gets to go first.");
            System.out.println(displayGameFromState(onePlayerCurrentState));
            System.out.println();

        }
        else {
            currentPlayer = playerNames[1];
            System.out.println(playerNames[1] + " gets to go first.");
            System.out.println(displayGameFromState(onePlayerCurrentState));
            System.out.println();
        }

        int[] move = new int[2];
        boolean gameOver = false;

        while (!gameOver) {
            if (checkWin(onePlayerCurrentState)){
                gameOver = true;
                if (currentPlayer.equals(playerNames[0])){
                    System.out.println(playerNames[1] + " wins!");
                }
                else {
                    System.out.println(playerNames[0] + " wins!");
                }
            }
            else if (checkDraw(onePlayerCurrentState)){
                gameOver = true;
                System.out.println("It's a draw!");
            }
            else{
                if (currentPlayer.equals(playerNames[0])){
                    System.out.println(playerNames[0] + "'s turn: ");
                    move = getInBoundsPlayerMove(playerNames[0]);
                    while(!checkValidMove(move, onePlayerCurrentState)){
                        move = getInBoundsPlayerMove(playerNames[0]);
                    }
                    System.out.println(displayGameFromState(makeMove(move, playerOneSymbol, onePlayerCurrentState)));
                    onePlayerCurrentState = makeMove(move, playerOneSymbol, onePlayerCurrentState);
                    currentPlayer = playerNames[1];
                    result.add(onePlayerCurrentState);
                }
                else if (currentPlayer.equals(playerNames[1])){
                    System.out.println("Computer's turn: ");
                    currentPlayer = playerNames[0];
                    System.out.println(displayGameFromState(getCPUMove(onePlayerCurrentState)));
                    result.add(getCPUMove(onePlayerCurrentState));
                    onePlayerCurrentState = getCPUMove(onePlayerCurrentState);
                }
            }
        }
        return result;
    }

    // Repeatedly prompts player for move in current state, returning new state after their valid move is made
    private static char[][] runPlayerMove(String playerName, char playerSymbol, char[][] currentState) {
        char[][] afterMove = new char[size][size];

        int[] rowAndCol = getInBoundsPlayerMove(playerName);
        int row = rowAndCol[0]; // re-clarified what rowAndCol is
        int col = rowAndCol[1];
        afterMove[row][col] = playerSymbol; // change the value of the given position

        return afterMove;
    }

    // Repeatedly prompts player for move. Returns [row, column] of their desired move such that row & column are on
    // the 3x3 board, but does not check for availability of that space.
    private static int[] getInBoundsPlayerMove(String playerName) {
        Scanner sc = new Scanner(System.in);
        int[] rowAndCol = new int[2]; // create an array of int to save the value of row and column
        boolean valid = false; // created boolean to see if the value is valid

        while (!valid){
            System.out.print(playerName + " enter row: ");
            int row = sc.nextInt();
            System.out.print(playerName + " enter col: ");
            int col = sc.nextInt();

            if (row >= 0 && row <= size-1 && col >= 0 && col <= size-1){ // since size is 3, and both values should be no bigger than 3 -> size - 1
                rowAndCol[0] = row;
                rowAndCol[1] = col;
                valid = true;
            }
            else {
                System.out.println("That row or column is out of bounds. Try again.");
            }
        }
        return rowAndCol;
    }

    // Given a [row, col] move, return true if a space is unclaimed.
    // Doesn't need to check whether move is within bounds of the board.
    private static boolean checkValidMove(int[] move, char[][] state) {
        boolean valid = false;

        int row = move[0];
        int col = move[1];

        if (state[row][col] == emptySpaceSymbol){
            valid = true;
        }
        else{
            System.out.println("That space is already taken. Try again.");
        }

        return valid;
    }

    // Given a [row, col] move, the symbol to add, and a game state,
    // Return a NEW array (do NOT modify the argument currentState) with the new game state
    private static char[][] makeMove(int[] move, char symbol, char[][] currentState) {
        // TODO:
        // Hint: Make use of Arrays.copyOf() somehow to copy a 1D array easily
        // You may need to use it multiple times for a 1D array
        char[][] copiedArray = new char[currentState.length][currentState[0].length];


        for(int i = 0; i < currentState.length; i++){
            for (int j = 0; j < currentState[i].length; j++){
                copiedArray[i][j]  = currentState[i][j];
            }
        }

        int row = move[0];
        int col = move[1];

        copiedArray[move[0]][move[1]] = symbol;

        return copiedArray;
    }

    // Given a state, return true if some player has won in that state
    private static boolean checkWin(char[][] state) {
        boolean win = false;

        // vertically equivalent
        for (int i = 0; i < state.length; i++){
            if (state[i][0] == state[i][1] && state[i][1] == state[i][2] && state[i][0] != emptySpaceSymbol){
                win = true;
            }
        }

        // horizontally equivalent
        for (int j = 0; j < state[0].length; j++){
            if (state[0][j] == state[1][j] && state[1][j] == state[2][j] && state[0][j] != emptySpaceSymbol){
                win = true;
            }
        }

        // diagonal equivalent
        if (state[0][0] == state[1][1] && state[1][1] == state[2][2] && state[0][0] != emptySpaceSymbol){
            win = true;
        }
        if (state[0][2] == state[1][1] && state[1][1] == state[2][0] && state[0][2] != emptySpaceSymbol){
            win = true;
        }

        return win;
    }

    // Given a state, simply checks whether all spaces are occupied. Does not care or check if a player has won.
    private static boolean checkDraw(char[][] state) {
        // check if the state is full
        boolean notDraw = true;

        for (int i = 0; i < state.length; i++){
            for (int j = 0; j < state[i].length; j++){
                if (state[i][j] == emptySpaceSymbol){
                    notDraw = false;
                }
            }
        }
        return notDraw;
    }

    // Given a game state, return a new game state with move from the AI
    // It follows the algorithm in the PDF to ensure a tie (or win if possible)
    private static char[][] getCPUMove(char[][] gameState) {
        // TODO

        // Hint: you can call makeMove() and not end up returning the result, in order to "test" a move
        // and see what would happen. This is one reason why makeMove() does not modify the state argument
        char[][] result = new char[gameState.length][gameState[0].length];
        // Determine all available spaces
        // check if the center is taken by the user
        boolean centerTaken = false;
        ArrayList<int[]> unclaimed = getValidMoves(gameState);
        int row = 0;
        int col = 0;

        for (int i = 0; i < unclaimed.size(); i++){
            for (int j = 0; j < unclaimed.get(i).length; j++){
                if (unclaimed.get(i)[0] == 1 && unclaimed.get(i)[1] == 1){
                    centerTaken = true;
                }
            }
        }

        // if center is not taken set row and col to 1
        if (centerTaken){
            for (int i = 0; i < unclaimed.size(); i++){
                for (int j = 0; j < unclaimed.get(i).length; j++){
                        row = 1;
                        col = 1;
                    }
                }
            }

        else {
            for (int i = 0; i < unclaimed.size(); i++) {
                for (int j = 0; j < unclaimed.get(i).length; j++) {
                    row = unclaimed.get(i)[0];
                    col = unclaimed.get(i)[1];
                }
            }
        }

        // modify the state of the given char[][]
        for (int a = 0; a < result.length; a++){
            for (int b = 0; b < result[0].length; b++){
                if (a == row && b == col){
                    result[a][b] = playerTwoSymbol;
                }
                else{
                    result[a][b] = gameState[a][b];
                }
            }
        }

        return result;

        // If there is a winning move available, make that move
        // If not, check if opponent has a winning move, and if so, make a move there

        // If not, move on center space if possible

        // If not, move on corner spaces if possible

        // Otherwise, move in any available spot

    }

    // Given a game state, return an ArrayList of [row, column] positions that are unclaimed on the board
    private static ArrayList<int[]> getValidMoves(char[][] gameState) {
        ArrayList<int[]> unclaimed = new ArrayList<int[]>();

        for (int i = 0; i < gameState.length; i++){
            for (int j = 0; j < gameState[i].length; j++){
                if (gameState[i][j] == emptySpaceSymbol){
                    unclaimed.add(new int[]{i,j});
                }
            }
        }
        return unclaimed;
    }

    // Given player names and the game history, display the past game as in the PDF sample code output
    private static void runGameHistory(String[] playerNames, ArrayList<char[][]> gameHistory) {
        // TODO
        // We have the names of the players in the format [playerOneName, playerTwoName]
        // Player one always gets 'X' while player two always gets 'O'
        // However, we do not know yet which player went first, but we'll need to know...
        // Hint for the above: which symbol appears after one turn is taken?
        int j = 0;

        System.out.println(playerNames[0] + " (X) vs " + playerNames[1] + " (O)");
        System.out.println(displayGameFromState(getInitialGameState()));

        // Hint: iterate over gameHistory using a loop
        for (int i = 0; i < gameHistory.size(); i++){
            if (j == 0){
                System.out.println(playerNames[j] + ":");
                System.out.println();
                System.out.println(displayGameFromState(gameHistory.get(i)));
                j = 1;
            }
            else {
                System.out.println(playerNames[j] + ":");
                System.out.println();
                System.out.println(displayGameFromState(gameHistory.get(i)));
                j = 0;
            }

        }
    }
}