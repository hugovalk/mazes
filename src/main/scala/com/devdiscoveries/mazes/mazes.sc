import com.devdiscoveries.mazes._
import com.devdiscoveries.mazes.algorithms.{AldousBroder, BinaryTree}

val maze = new Maze(40, 40)
  with SquareGrid
  with AldousBroder

maze.printMaze
