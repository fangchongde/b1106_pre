import java.util.Arrays;
import java.util.Scanner;

/**
 * 井字棋变体游戏类
 * 规则：先连成 4 个获胜，但如果先连成 3 个则输
 */
class TicTacToe {

    // 棋盘大小常量
    private static final int BOARD_SIZE = 5;
    
    // 输赢判定长度常量
    private static final int LOSE_LENGTH = 3;  // 连成 3 个则输
    private static final int WIN_LENGTH = 4;   // 连成 4 个则赢

    // 棋盘单元格标记常量
    private static final String EMPTY = " ";   // 空位
    private static final String PLAYER_X = "X"; // 玩家 X
    private static final String PLAYER_O = "O"; // 玩家 O

    private int moveCount = 0;  // 记录总移动次数

    /**
     * 坐标类，用于存储棋盘位置
     */
    private static class Coord {
        int row, col;

        Coord(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    /**
     * 初始化棋盘，所有位置设为空
     * @return 初始化后的棋盘
     */
    private String[][] initialiseBoard() {
        String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
        for (String[] row : board) {
            Arrays.fill(row, EMPTY);
        }
        return board;
    }

    /**
     * 显示当前棋盘状态
     * @param board 棋盘
     */
    private void displayBoard(String[][] board) {
        for (String[] row : board) {
            System.out.println(String.join(" | ", row));
            System.out.println("-".repeat(BOARD_SIZE * 4 - 3));
        }
    }

    /**
     * 验证坐标是否在棋盘范围内
     * @param coord 待验证的坐标
     * @return 如果坐标有效返回 true，否则返回 false
     */
    private boolean isValidCoord(Coord coord) {
        boolean rowIsValid = coord.row >= 0 && coord.row < BOARD_SIZE;
        boolean colIsValid = coord.col >= 0 && coord.col < BOARD_SIZE;
        return rowIsValid && colIsValid;
    }

    /**
     * 获取玩家输入的移动坐标，包含输入验证
     * @return 玩家输入的有效坐标
     */
    private Coord getPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        Coord move = new Coord(-1, -1);

        try {
            while (true) {
                try {
                    System.out.printf("Enter the row (0 to %d): ", BOARD_SIZE - 1);
                    move.row = Integer.parseInt(scanner.nextLine());
                    System.out.printf("Enter the column (0 to %d): ", BOARD_SIZE - 1);
                    move.col = Integer.parseInt(scanner.nextLine());

                    if (isValidCoord(move)) {
                        break;
                    }
                    throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Invalid row or column. Try again.");
                }
            }
        } finally {
            scanner.close();
        }
        return move;
    }

    /**
     * 检查指定位置是否为空
     * @param board 棋盘
     * @param coord 坐标
     * @return 如果为空返回 true，否则返回 false
     */
    private boolean isCellEmpty(String[][] board, Coord coord) {
        return board[coord.row][coord.col].equals(EMPTY);
    }

    /**
     * 检查指定位置是否为指定玩家的标记
     * @param board 棋盘
     * @param player 玩家标记
     * @param coord 坐标
     * @return 如果是该玩家的标记返回 true，否则返回 false
     */
    private boolean isPlayerCell(String[][] board, String player, Coord coord) {
        return board[coord.row][coord.col].equals(player);
    }

    /**
     * 在棋盘上放置玩家标记
     * @param board 棋盘
     * @param player 玩家标记
     * @param coord 坐标
     */
    private void makeMove(String[][] board, String player, Coord coord) {
        board[coord.row][coord.col] = player;
        moveCount++;
    }

    /**
     * 检查玩家是否输了（连成 LOSE_LENGTH 个）
     * @param board 棋盘
     * @param player 玩家标记
     * @param lastMove 最后一步的坐标
     * @return 如果输了返回 true，否则返回 false
     */
    private boolean checkLoser(String[][] board, String player, Coord lastMove) {
        // 定义四个方向：水平、垂直、主对角线、反对角线
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
        Coord coord = new Coord(-1, -1);

        for (int[] dir : directions) {
            int count = 1;  // 计数包含当前格子

            // 向正方向统计连续相同标记数
            for (int i = 1; i < LOSE_LENGTH; i++) {
                coord.row = lastMove.row + dir[0] * i;
                coord.col = lastMove.col + dir[1] * i;
                if (!isValidCoord(coord)) {
                    break;
                }
                if (!isPlayerCell(board, player, coord)) {
                    break;
                }
                count++;
            }

            // 向反方向统计连续相同标记数
            for (int i = 1; i < LOSE_LENGTH; i++) {
                coord.row = lastMove.row - dir[0] * i;
                coord.col = lastMove.col - dir[1] * i;
                if (!isValidCoord(coord)) {
                    break;
                }
                if (!isPlayerCell(board, player, coord)) {
                    break;
                }
                count++;
            }

            // 如果正好连成 LOSE_LENGTH 个，则该玩家输
            if (count == LOSE_LENGTH) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查玩家是否赢了（连成 WIN_LENGTH 个）
     * @param board 棋盘
     * @param player 玩家标记
     * @param lastMove 最后一步的坐标
     * @return 如果赢了返回 true，否则返回 false
     */
    private boolean checkWinner(String[][] board, String player, Coord lastMove) {
        // 定义四个方向：水平、垂直、主对角线、反对角线
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
        Coord coord = new Coord(-1, -1);

        for (int[] dir : directions) {
            int count = 1;  // 计数包含当前格子

            // 向正方向统计连续相同标记数
            for (int i = 1; i < WIN_LENGTH; i++) {
                coord.row = lastMove.row + dir[0] * i;
                coord.col = lastMove.col + dir[1] * i;
                if (!isValidCoord(coord)) {
                    break;
                }
                if (!isPlayerCell(board, player, coord)) {
                    break;
                }
                count++;
            }

            // 向反方向统计连续相同标记数
            for (int i = 1; i < WIN_LENGTH; i++) {
                coord.row = lastMove.row - dir[0] * i;
                coord.col = lastMove.col - dir[1] * i;
                if (!isValidCoord(coord)) {
                    break;
                }
                if (!isPlayerCell(board, player, coord)) {
                    break;
                }
                count++;
            }

            // 如果正好连成 WIN_LENGTH 个，则该玩家赢
            if (count == WIN_LENGTH) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查棋盘是否已满
     * @return 如果棋盘已满返回 true，否则返回 false
     */
    private boolean isBoardFull() {
        return moveCount == BOARD_SIZE * BOARD_SIZE;
    }

    /**
     * 显示游戏规则说明
     */
    private void displayRules() {
        System.out.println("========================================");
        System.out.println("       欢迎来到井字棋变体游戏！");
        System.out.println("========================================");
        System.out.println("游戏规则：");
        System.out.println("1. 棋盘大小：" + BOARD_SIZE + " x " + BOARD_SIZE);
        System.out.println("2. 玩家轮流在棋盘上放置自己的标记（X 或 O）");
        System.out.println("3. 先连成 " + WIN_LENGTH + " 个相同标记的玩家获胜");
        System.out.println("4. 注意：如果先连成 " + LOSE_LENGTH + " 个相同标记则会输掉游戏！");
        System.out.println("5. 输入坐标范围：0 到 " + (BOARD_SIZE - 1));
        System.out.println("6. 如果棋盘填满仍未分出胜负，则游戏为平局");
        System.out.println("========================================");
        System.out.println();
    }

    /**
     * 游戏主循环方法
     */
    public void playTicTacToe() {
        String currentPlayer = PLAYER_X;
        String otherPlayer;
        String[][] board = initialiseBoard();

        displayRules();

        while (true) {
            otherPlayer = currentPlayer.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;

            displayBoard(board);
            System.out.printf("Player %s's turn.%n", currentPlayer);

            Coord move = getPlayerMove();
            
            if (!isCellEmpty(board, move)) {
                System.out.println("Position is already taken. Try again.");
                continue;
            }
            
            makeMove(board, currentPlayer, move);

            if (checkLoser(board, currentPlayer, move)) {
                displayBoard(board);
                System.out.printf("Player %s got %d in a row and LOSES!%n", currentPlayer, LOSE_LENGTH);
                System.out.printf("Player %s WINS!%n", otherPlayer);
                break;
            } else if (checkWinner(board, currentPlayer, move)) {
                displayBoard(board);
                System.out.printf("Player %s got %d in a row and WINS!%n", currentPlayer, WIN_LENGTH);
                break;
            } else if (isBoardFull()) {
                displayBoard(board);
                System.out.println("The game ends in a tie!");
                break;
            }

            currentPlayer = otherPlayer;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.playTicTacToe();
    }
}