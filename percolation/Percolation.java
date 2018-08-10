import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class Percolation{
    public boolean[][] openSites;
    public WeightedQuickUnionUF union;
    public int bottom;
    public int top;
    public int size;
    public int openSitesCount = 0;

    public Percolation(int n){
        if(n < 0){
            throw new IllegalArgumentException(
                "Size cannot be less than zero"
                );
        }
        top = 0;
        size = n;
        bottom = size * size + 1;
        openSites = new boolean[n][n];
        union = new WeightedQuickUnionUF(size * size + 2);
    }

    public void raiseOutOfBounds(int row, int col){
        if(row < 1 || row > size || col < 1 || col > size){
            throw new java.lang.IllegalArgumentException("Out of bounds");
        }
    }

    public void open(int row, int col){
        raiseOutOfBounds(row, col); 
        openSites[row - 1][col - 1] = true;
        openSitesCount++;

        if(row == 1){
            union.union(unionIndex(row, col), top);
        }
        if(row == size){
            union.union(unionIndex(row, col), bottom);
        }
        if( col > 1 && isOpen(row, col - 1)){
            union.union(unionIndex(row, col), unionIndex(row, col - 1));
        }
        if( col < size && isOpen(row, col + 1)){
            union.union(unionIndex(row, col), unionIndex(row, col + 1));
        }
        if( row > 1 && isOpen(row - 1, col)){
            union.union(unionIndex(row, col), unionIndex(row - 1, col));
        }
        if( row < size && isOpen(row + 1, col)){
            union.union(unionIndex(row, col), unionIndex(row + 1, col));
        }
    }

    public boolean isOpen(int row, int col){
        raiseOutOfBounds(row, col); 
        return openSites[row - 1][col - 1];
    }

    public boolean isFull(int row, int col){
        raiseOutOfBounds(row, col); 
        return union.connected(unionIndex(row, col), top);
    }

    public int numberOfOpenSites(){
       return openSitesCount;
    }

    public boolean percolates(){
        return union.connected(top, bottom);
    }

    public static void main(String[] args){

    }

    private int unionIndex(int i, int j){
        return size * (i - 1) + j;
    }
}
