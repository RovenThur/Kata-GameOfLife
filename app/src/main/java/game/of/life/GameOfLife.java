/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package game.of.life;

public class GameOfLife {
    private boolean[][] grid;
    private int rows = 0;
    private int columns = 0;

    /**
     * Constructor
     * @param row Le nombre de lignes de notre grille
     * @param column Le nombre de colonnes de la grille
     */
    public GameOfLife(int rows, int columns) {
        grid = new boolean[rows][columns];
        this.rows = rows;
        this.columns = columns;
        this._initializeGrid();
    }

    private void _initializeGrid() {
        for(int row = 0; row < rows; row ++) {
            for ( int column = 0; column < columns; column ++) {
                grid[row][column] = false;
            }
        }
    }

    /**
     * Retourne les dimensions de la grille
     * @return
     */
    public int[] getGridSize() {
        return new int[]{rows, columns};
    }
    
    /**
     * Retourne l'état de la cellule ciblée
     * @param row
     * @param column
     * @return
     */
    public boolean isAlive(int row, int column) throws OutOfRangeException {
        if (!_isInRange(row, column)) throw new OutOfRangeException();
        return grid[row][column];
    }
    
    private boolean _isInRange(int row, int column) {
        return row >= 0 && column >= 0 && row < rows && column < columns;
    }

    /**
     * Donne vie à la cellule ciblée
     * @param row
     * @param column
     * @return
     */
    public void setLivingCell(int row, int column) throws OutOfRangeException {
        if (!_isInRange(row, column)) throw new OutOfRangeException();
        this.grid[row][column] = true;
    }

    /**
     * Compte le nombre de cellules en vie autour de la cellule ciblée
     * @param row numéro de ligne de la cellule
     * @param column numéro de colonne de la cellule
     * @return le nombre de cellules en vies autour de la cellule donnée en paramètres
     */
    public int countLivingNeighbors(int row, int column) throws OutOfRangeException {
        int aliveNeighbors = 0;
        if (!_isInRange(row, column)) throw new OutOfRangeException();
        
        for (int parsingRow = row - 1; parsingRow <= row + 1; parsingRow ++) {
            for (int parsingColumn = column - 1; parsingColumn <= column + 1; parsingColumn ++) {
                // Si outOfRange on ignore
                if(!_isInRange(parsingRow, parsingColumn)) continue;

                // Si c'est la case, on l'ignore
                if ((row == parsingRow) && (column == parsingColumn)) continue;

                // Sinon on incrémente
                if (isAlive(parsingRow, parsingColumn)) aliveNeighbors++;
            }
        }
        return aliveNeighbors;
    }

    /**
     * Met à jour la grille en appliquant les règles du Jeu de la Vie.
     * Si une cellule a moins de deux voisins en vie, elle meure
     * Si une cellule a plus de trois voisins en vie, elle meure
     * Si une cellule en vie a 2 ou 3 voisins en vie, elle reste en vie
     * Si une cellule morte a trois voisins en vie, elle prend vie 
     * 
     * Toutes les règles sont appliquées simultanément
     */ 
    public void nextGeneration() {
        boolean[][] nextGenerationGrid = new boolean[rows][columns];
        for (int parsingRow = 0; parsingRow < rows; parsingRow++) {
            for (int parsingColumn = 0; parsingColumn < columns; parsingColumn ++) {
                nextGenerationGrid[parsingRow][parsingColumn] = computeNextState(parsingRow, parsingColumn);
            }
        }
        this.grid = nextGenerationGrid;
    }

    private boolean computeNextState(int row, int column) {
        try {
            int neighbors = this.countLivingNeighbors(row, column);
            boolean alive = isAlive(row, column);

            if (alive) {
                if (neighbors < 2) return false;
                if (neighbors > 3) return false;

                return true;
            } else if (neighbors == 3) return true;
        } catch (OutOfRangeException e) {}
        return false;
    }
}
