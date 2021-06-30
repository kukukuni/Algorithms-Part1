/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class Board {

    private int[][] tiles2 = null;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // if (tiles.length < 2 || tiles.length >= 128) {
        //     throw new IndexOutOfBoundsException("size error : " + dimension());
        // }
        this.tiles2 = tiles.clone();
    }

    // string representation of this board
    public String toString() {
        String boardState = "";
        boardState = dimension() + "\n";
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                boardState += tiles2[i][j];
            }
            boardState += "\n";
        }
        return boardState;
    }

    // board dimension n
    public int dimension() {
        return tiles2.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingRlt = 0;
        int answer = 1;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (i == dimension() - 1 && j == dimension() - 1) {
                    answer = 0;
                }
                if (tiles2[i][j] != 0 && answer != tiles2[i][j]) {
                    hammingRlt += 1;
                }
            }
            answer += 1;
        }
        return hammingRlt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int mantattenRlt = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int q = tiles2[i][j] / dimension();//몫
                int r = (tiles2[i][j] % dimension()) - 1;//나머지

                if (q > i) mantattenRlt += q - i;
                else mantattenRlt += i - q;
                if (r > i) mantattenRlt += r - i;
                else mantattenRlt += i - r;
            }
        }
        return mantattenRlt;
    }


    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;

        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                if (this.tiles2[i][j] != that.tiles2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();

        int row = 0;
        int col = 0;

        for (row = 0; row < dimension(); row++) {
            for (col = 0; col < dimension(); col++) {
                if (tiles2[row][col] == 0) break;
            }
        }

        //0의 좌표 i, j
        //0 =>
        if (row > 0) {
            int[][] neighbor = tiles2.clone();
            neighbor[row][col] = neighbor[row][col + 1];
            neighbor[row][col + 1] = 0;
            q.enqueue(new Board(neighbor));
        }

        // <= 0
        if (row < dimension() - 1) {
            int[][] neighbor = tiles2.clone();
            neighbor[row][col] = neighbor[row][col - 1];
            neighbor[row][col - 1] = 0;
            q.enqueue(new Board(neighbor));
        }

        // 0 up
        if (col > 0) {
            int[][] neighbor = tiles2.clone();
            neighbor[row][col] = neighbor[row - 1][col];
            neighbor[row - 1][col] = 0;
            q.enqueue(new Board(neighbor));
        }

        // 0 dowmn
        if (col < dimension() - 1) {
            int[][] neighbor = tiles2.clone();
            neighbor[row][col] = neighbor[row + 1][col];
            neighbor[row + 1][col] = 0;
            q.enqueue(new Board(neighbor));
        }
        return q;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return new Board(tiles2);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
