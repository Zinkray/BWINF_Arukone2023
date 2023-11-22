import java.util.*;
import java.util.Scanner;

public class Arukone {
    public static void main(String[] args) throws Exception {

        int dimension = GetDim();
        int pair = GetPair(dimension);
        int[][] board = CreateBoard(dimension, pair);

        Print(board, pair);
    }

    private static int GetDim() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Bitte die gewünschte Größe für ein Gitter n x n  angeben welche nicht kleiner als 4 ist: ");
        int dim = scan.nextInt();
        if (dim < 4) {
            dim = 4;
        }
        System.out.println();
        return dim;
    }

    private static int GetPair(int dim) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Bitte eine Anzahl an Paaren angeben welche mindestens " + dim / 2 + " und maximal "
                + (dim * dim) / 2 + " ist: ");
        int pair = scan.nextInt();
        if (pair < dim / 2) {
            pair = dim / 2;
        } else if (pair > (dim * dim) / 2) {
            pair = (dim * dim) / 2;
        }
        System.out.println();
        return pair;
    }

    private static int[][] CreateBoard(int dimension, int pair) {
        int[][] Board = new int[dimension][dimension];
        boolean finRun = false;

        while (!finRun) {
            for (int i = 0; i < pair; i++) {
                if (Board == null) {
                    Board = new int[dimension][dimension];
                    i = 0;
                }
                Board = DoPath(Board, i + 1, pair);
            }
            if (Board != null)
                finRun = true;
        }
        return Board;
    }

    private static int[][] DoPath(int[][] board, int num, int pair) {
        Random random = new Random();
        boolean notFin = true;
        int count = 0;
        int x = random.nextInt(board.length);
        int y = random.nextInt(board.length);
        int[] posi = new int[2];
        posi[0] = y;
        posi[1] = x;

        while (notFin) {
            if (board[posi[0]][posi[1]] == 0) {
                board[posi[0]][posi[1]] = -num;
                int felder = random.nextInt(1, GetSumOfMatrix(board) / pair);

                for (int i = 0; i < felder; i++) {
                    boolean moved = false;
                    int moveDirection = random.nextInt(4);
                    int reTryCont = 0;
                    while (!moved) {
                        int[] tempPosi = MovePosi(posi, moveDirection, board.length);
                        boolean result = IsFreeField(board, tempPosi);
                        if (result == true) {
                            posi = tempPosi;
                            moved = true;
                        } else {
                            moveDirection++;
                            if (moveDirection >= 4)
                                moveDirection = 0;
                            reTryCont++;
                            if (reTryCont >= 4) {
                                i = felder;
                                notFin = false;
                                board = null;
                                moved = true;
                            }
                        }
                    }
                    count++;
                    if (board != null) {
                        if (count >= felder) {
                            board[posi[0]][posi[1]] = -num;
                            notFin = false;
                        } else {
                            board[posi[0]][posi[1]] = num;
                        }
                    }
                }
            } else {
                posi[0] = random.nextInt(board.length);
                posi[1] = random.nextInt(board.length);
            }
        }
        return board;
    }

    private static boolean IsFreeField(int[][] board, int[] posi) {
        if (board[posi[0]][posi[1]] == 0)
            return true;
        return false;
    }

    private static int[] MovePosi(int[] posi, int direction, int max) {
        int[] hold = new int[posi.length];
        for (int i = 0; i < posi.length; i++) {
            hold[i] = posi[i];
        }
        boolean moved = false;
        while (!moved) {
            if (direction == 0) {
                if (hold[0] == 0) {
                    direction++;
                } else {
                    hold[0]--;
                    moved = true;
                }
            }
            if (direction == 1) {
                if (hold[1] == max - 1) {
                    direction++;
                } else {
                    hold[1]++;
                    moved = true;
                }
            }
            if (direction == 2) {
                if (hold[0] == max - 1) {
                    direction++;
                } else {
                    hold[0]++;
                    moved = true;
                }
            }
            if (direction == 3) {
                if (hold[1] == 0) {
                    direction = 0;
                } else {
                    hold[1]--;
                    moved = true;
                }
            }
        }
        return hold;
    }

    public static void Print(int[][] board, int pair) {

        System.out.println("");
        System.out.println(board.length);
        System.out.println(pair);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int value = board[i][j];
                if (value < 0) {
                    value = Math.abs(value);
                    System.out.print(value + " ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static int GetSumOfMatrix(int[][] board) {
        return board.length * board.length;
    }
}