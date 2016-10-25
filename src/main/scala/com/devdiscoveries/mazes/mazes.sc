import java.io.File
import javax.imageio.ImageIO

import com.devdiscoveries.mazes._
import com.devdiscoveries.mazes.algorithms.{AldousBroder, BinaryTree, Sidewinder, Wilsons}

val maze = new Maze(50, 50)
  with SquareGrid
  with AldousBroder

//maze.printMaze

val image = maze.renderMaze(50)
val path = "/home/hugovalk/projects/mazes/test.png"
val file = new File(path)
ImageIO.write(image, "PNG", file)