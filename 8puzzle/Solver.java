/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.*;

public class Solver {

    private MinPQ<elementInfo> pq = new MinPQ<>();
    private MinPQ<elementInfo> twinpq = new MinPQ<>();
    private elementInfo lastInfo;

    private class elementInfo implements Comparable<elementInfo>{
        private Board element;
        private elementInfo prevElementInfo;
        private int moves;

        public elementInfo(Board board){
            this.element = board;
            this.prevElementInfo = null;
            this.moves = 0;
        }

        public elementInfo(Board board, elementInfo prev){
            this.element = board;
            this.prevElementInfo = prev;
            this.moves = prevElementInfo.moves + 1;
        }

        public int compareTo(elementInfo element) {
            if(element==null)
                throw  new java.lang.IllegalArgumentException();
            int compareRlt = this.element.manhattan() + this.moves
                    - element.element.manhattan() - element.moves;
            return compareRlt;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        pq.insert(new elementInfo(initial));
        //solvable check
        twinpq.insert(new elementInfo(initial.twin()));
        isSolvable();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        boolean rlt = false;
        //twin이랄 원본이랑 둘중 하나는 무조건 goal이 안됨 그래서 계속 돈다. 하나만 rlt나오면 종료
        while(true){
            if(enqueElement(pq)!=null){
                lastInfo = enqueElement(pq);
                rlt = true;
                break;
            }
            if(enqueElement(twinpq)!=null){
                rlt = false;
                break;
            }
        }
        return rlt;
    }

    private elementInfo enqueElement(MinPQ<elementInfo> minPQ){
        if(minPQ.isEmpty()) return null;

        elementInfo tmpMin = minPQ.delMin();
        //goal이면 반환하고 그만
        if(tmpMin.element.isGoal())
            return tmpMin;

        for(Board neighbor : tmpMin.element.neighbors()) {
            if(tmpMin.prevElementInfo == null || !neighbor.equals(tmpMin.prevElementInfo.element)) {
                pq.insert(new elementInfo(neighbor, tmpMin));
            }
        }
        return null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if(!isSolvable())
            return -1;
        return lastInfo.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){

        if(!isSolvable()) return null;

        Stack<Board> solution = new Stack<>();
        elementInfo tmp = lastInfo;

        while(tmp.prevElementInfo != null) {
            solution.push(tmp.element);
            tmp = tmp.prevElementInfo;
        }
        solution.push(tmp.element);

        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
