import java.lang.StringBuilder;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;

public class Board {
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private int[][] blocks;
    private int length;
    private int manhattan = -1;
    public Board(int[][] blocks){
        this.blocks = blocks;
        this.length = blocks.length * blocks.length;
    }

    // board dimension n
    public int dimension(){
        return blocks.length;
    }                 

    // number of blocks out of place
    public int hamming(){
        int count = 0;
        int hamming = 0;
        for(int i = 0; i < this.blocks.length; i++){
            for(int j = 0; j < this.blocks[i].length; j++){
                int block = blocks[i][j];
                if(block != count + 1 && block != 0){
                    hamming++;
                } 
                count++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan(){
        if(manhattan != -1){
            return manhattan;
        }
        manhattan = 0;
        int N = length;
        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < blocks[i].length; j++){
                int block = blocks[i][j];
                if(block != 0){
                    int targetRow = (block - 1) / blocks.length; 
                    int targetCol = (block - 1) % blocks.length;
                    int dRow = Math.abs(i - targetRow);
                    int dCol = Math.abs(j - targetCol); 
                    manhattan += dRow + dCol;
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return this.equals(this.getSolvedBoard()); 
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin(){
        int[] origin = getRandomNonEmptyCell();
        int[] destination = origin;
        while(destination == origin){
            destination = getRandomNonEmptyCell();
        }
        int[][] copy = new int[blocks.length][blocks.length];
        System.arraycopy(blocks,0, copy, 0, blocks.length);
        int temp = copy[destination[0]][destination[1]];
        copy[destination[0]][destination[1]] = copy[origin[0]][origin[1]];
        copy[origin[0]][origin[1]] = temp; 
        return new Board(copy);
    }

    // does this board equal y?
    public boolean equals(Object y){
        Board yCasted;
        if(!(y instanceof Board)){
            return false;
        }
        else{
            yCasted = (Board) y;
        }
        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < blocks.length; j++){
                if(blocks[i][j] != yCasted.blocks[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        return null;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.blocks.length);
        builder.append("\n");
        for(int i = 0; i < this.blocks.length; i++){
            for(int j = 0; j < this.blocks[i].length; j++){
                builder.append(String.format("%5s", blocks[i][j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args){
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Board board = new Board(blocks);
        board.testToString();
        board.testManhattan();
        board.testHamming();
        board.testEquals();
        board.testIsGoal();
        board.testTwin();
    }

    private void testTwin(){
        Board board = twin();
        System.out.println(board);
    }

    private int[] getRandomNonEmptyCell(){
        int value = 0;
        int row = 0;
        int column = 0;
        while(value == 0){
            row = StdRandom.uniform(blocks.length);
            column = StdRandom.uniform(blocks.length); 
            value = blocks[row][column];
        }
        return new int[]{row, column};
    }

    private void testIsGoal(){
        System.out.println("Testing isGoal()");
        System.out.println(isGoal());
    }

    private void testEquals(){
        System.out.println("Testing equals");
        System.out.println(equals(this));
    }

    private void testManhattan(){
        System.out.println("Testing manhattan");
        System.out.println(manhattan());
    }

    private void testHamming(){
        System.out.println("Testing hamming");
        System.out.println(hamming());
    }

    private void testToString(){
        System.out.println("Testing toString()");
        System.out.println(toString());
    }

    private Board getSolvedBoard(){
        int[][] solvedBlocks = new int[this.blocks.length][this.blocks.length];
        int count = 0;
        for(int i = 0; i < this.blocks.length; i++){
            int [] currentRow = new int[this.blocks.length];
            for(int j = 0; j < this.blocks[i].length; j++){
                if(count == length - 1){
                    currentRow[j] = 0; 
                }
                else{
                    currentRow[j] = count + 1;
                }
                count++;
            }
            solvedBlocks[i] = currentRow;
        }
        return new Board(solvedBlocks);
    }
}