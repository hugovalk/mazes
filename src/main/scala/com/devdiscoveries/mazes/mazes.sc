import com.devdiscoveries.mazes._
import com.devdiscoveries.mazes.algorithms.{AldousBroder, BinaryTree, Sidewinder, Wilsons}

val maze = new Maze(10, 10)
  with SquareGrid
  with Sidewinder

maze.printMaze
