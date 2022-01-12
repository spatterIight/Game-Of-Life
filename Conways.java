import java.awt.*;

public class Conways {
 public static void main(String[] args) {

  //Initialize 2D Array and Fill w/ False
  char q = '~';
  boolean[][] grid = new boolean[25][25];

  //Initialize Canvas and Draw Gridlines
  StdDraw.enableDoubleBuffering();
  StdDraw.setXscale(0, 25);
  StdDraw.setYscale(0, 25);

  //Draw Gridlines
  drawGridlines();
  StdDraw.show();

  //Controls and Rules
  System.out.println();
  System.out.println("Controls: \n\tclick = fill square \n\tw = next tick \n\t1 = 10-Cell Pattern\n\t2 = Glider Pattern\n\t3 = Tumbler Pattern\n\t0 = Reset");
  System.out.println("Rules: \n\t1. Any live cell with fewer than two live neighbours dies, as if by underpopulation. \n\t2. Any live cell with two or three live neighbours lives on to the next generation. \n\t3. Any live cell with more than three live neighbours dies, as if by overpopulation. \n\t4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.");


 //Infinite Game Loop
 while (true) {
  if (StdDraw.hasNextKeyTyped())
   q = StdDraw.nextKeyTyped();

  //Interpret Keyboard Input
  if (q == '1') {
    grid = cell10(grid);
    q = '~';
  }
  if (q == '2') {
   grid = glider(grid);
   q = '~';
  }
  if (q == '3') {
   grid = tumbler(grid);
   q = '~';
  }
  if (q == '0') {
   grid = falsify();
   q = '~';
  }
  if (q == 'w' || q == 'k') {
   grid = nextTickDraw(grid);
   if (q == 'w')
    q = '~';
   else
    StdDraw.pause(1000);
  }

  //Interpret Mouse Input -- Save to Grid & Draw
  if (StdDraw.isMousePressed()) {
   double cursorX = ((int) StdDraw.mouseX()) + .5;
   double cursorY = ((int) StdDraw.mouseY()) + .5;
   grid[(24 - (int) cursorY)][((int) cursorX)] = true;
   StdDraw.filledSquare(cursorX, cursorY, .4);
   StdDraw.show();
  }
 }

 //End of Main
 }

 //Perform 1 Tick/Turn to Internal 2D Array and Draw Changes
 public static boolean[][] nextTickDraw(boolean[][] grid) {
  boolean[][] temp = new boolean[25][25];
  for (int r = 0; r < grid.length; r++)
   for (int c = 0; c < temp[0].length; c++)
    temp[r][c] = false;
  for (int r = 0; r < grid.length; r++) {
   for (int c = 0; c < grid[0].length; c++) {
    final int numNeighbors = countNeighbors(grid, r, c);
    if ((numNeighbors < 2) || (numNeighbors > 3)) {
     temp[r][c] = false;
     if (grid[r][c] == true)
      drawCell(c, r, true, .45);
    }
    if (numNeighbors == 2)
     temp[r][c] = grid[r][c];
    if (numNeighbors == 3) {
     temp[r][c] = true;
     if (grid[r][c] == false)
      drawCell(c, r, false, .4);
    }
   }
  }
  StdDraw.show();
  return temp;
 }

 //Evaluate Each Cell, Return Neighbors
 public static int countNeighbors(final boolean[][] generation, final int row, final int col) {
  int numNeighbors = 0;
  if ((row - 1 >= 0) && (col - 1 >= 0)) {
   numNeighbors = generation[row - 1][col - 1] ? numNeighbors + 1 : numNeighbors;
  }
  if ((row >= 0) && (col - 1 >= 0)) {
   numNeighbors = generation[row][col - 1] ? numNeighbors + 1 : numNeighbors;
  }
  if ((row + 1 < generation.length) && (col - 1 >= 0)) {
   numNeighbors = generation[row + 1][col - 1] ? numNeighbors + 1 : numNeighbors;
  }
  if ((row + 1 < generation.length) && (col < generation[0].length)) {
   numNeighbors = generation[row + 1][col] ? numNeighbors + 1 : numNeighbors;
  }
  if ((row + 1 < generation.length) && (col + 1 < generation[0].length)) {
   numNeighbors = generation[row + 1][col + 1] ? numNeighbors + 1 : numNeighbors;
  }
  if ((row < generation.length) && (col + 1 < generation[0].length)) {
   numNeighbors = generation[row][col + 1] ? numNeighbors + 1 : numNeighbors;
  }
  if ((row - 1 >= 0) && (col + 1 < generation[0].length)) {
   numNeighbors = generation[row - 1][col + 1] ? numNeighbors + 1 : numNeighbors;
  }
  if ((row - 1 >= 0) && (col < generation[0].length)) {
   numNeighbors = generation[row - 1][col] ? numNeighbors + 1 : numNeighbors;
  }
  return numNeighbors;
 }

 //Erase Cell to Canvas at Point
 public static void drawCell(double col, double row, boolean white, double size) {
  if (white)
   StdDraw.setPenColor(StdDraw.WHITE);
  StdDraw.filledSquare(col + .5, 24 - row + .5, size);
  StdDraw.setPenColor(StdDraw.BLACK);
 }

 //Draw Gridlines to Canvas
 public static void drawGridlines() {
   for (int i = 0; i < 25; i += 1)
    StdDraw.line(0, i, 25, i);
   for (int i = 0; i < 25; i += 1)
    StdDraw.line(i, 0, i, 25);
 }

 //Clear Canvas & Array
 public static boolean[][] falsify() {
   StdDraw.clear();
   drawGridlines();

   boolean[][] temp = new boolean[25][25];
   for (int r = 0; r < temp.length; r++) {
    for (int c = 0; c < temp[0].length; c++) {
     temp[r][c] = false;
 }
}

   StdDraw.show();
   return temp;
 }

 //10-Cell Pattern
 public static boolean[][] cell10(boolean[][] temp) {

   for (int r = 0; r < temp.length; r++) {
    for (int c = 0; c < temp[0].length; c++) {
     if( (r == 9 && c == 7) || (r == 9 && c == 8) || (r == 9 && c == 9) || (r == 9 && c == 10) || (r == 9 && c == 11) || (r == 9 && c == 12) || (r == 9 && c == 13) || (r == 9 && c == 14) || (r == 9 && c == 15) || (r == 9 && c == 16) ) {
     drawCell(c, r, false, .4);
     temp[r][c] = true;
   }
 }
}

   StdDraw.show();
   return temp;
 }

 //Tumbler Pattern
 public static boolean[][] tumbler(boolean[][] temp) {

   for (int r = 0; r < temp.length; r++) {
    for (int c = 0; c < temp[0].length; c++) {
     if( (r == 11 && c == 7) || (r == 12 && c == 6) || (r == 13 && c == 6) || (r == 12 && c == 8) || (r == 13 && c == 9) || (r == 14 && c == 8) || (r == 15 && c == 8) || (r == 15 && c == 9) || (r == 15 && c == 11) || (r == 15 && c == 12) || (r == 14 && c == 12) || (r == 13 && c == 11) || (r == 12 && c == 12) || (r == 11 && c == 13) || (r == 12 && c == 14) || (r == 13 && c == 14) ) {
     drawCell(c, r, false, .4);
     temp[r][c] = true;
   }
 }
}

   StdDraw.show();
   return temp;
 }

 //Glider Pattern
 public static boolean[][] glider(boolean[][] temp) {

   for (int r = 0; r < temp.length; r++) {
    for (int c = 0; c < temp[0].length; c++) {
     if( (r == 1 && c == 1) || (r == 2 && c == 2) || (r == 2 && c == 3) || (r == 1 && c == 3) || (r == 3 && c == 2) ) {
     drawCell(c, r, false, .4);
     temp[r][c] = true;
   }
 }
}

   StdDraw.show();
   return temp;
 }

 //End of Class
}
