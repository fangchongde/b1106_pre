# Tic Tac Toe Variant Game

## Overview
This is a variant of the classic Tic Tac Toe game with modified winning and losing conditions.

## Game Rules

1. **Board Size**: 5 x 5 grid
2. **Players**: Two players (X and O) take turns placing their marks on the board
3. **Winning Condition**: The first player to connect **4** consecutive marks in a row (horizontally, vertically, or diagonally) **WINS**
4. **Losing Condition**: If a player connects **3** consecutive marks first, they **LOSE** immediately
5. **Input Range**: Coordinates range from 0 to 4 for both rows and columns
6. **Draw**: If the board is filled without a winner or loser, the game ends in a tie

## Features

- Input validation for coordinate entries
- Real-time board display after each move
- Automatic detection of winning/losing conditions
- Turn-based gameplay between two players

## How to Play

1. Run the program
2. Players take turns entering row and column coordinates
3. The game automatically checks for win/loss conditions after each move
4. The game ends when:
   - A player connects 4 marks (winner)
   - A player connects 3 marks (loser)
   - The board is full (tie)

## Technical Details

- **Language**: Java
- **Main Class**: `Main`
- **Game Logic Class**: `TicTacToe`
- **Board Representation**: 2D String array
- **Coordinate System**: Zero-indexed (0-4)

## Compilation and Execution

```bash
# Compile
javac Main.java

# Run
java Main
```
