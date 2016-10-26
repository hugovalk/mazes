import java.io.File
import javax.imageio.ImageIO

import com.devdiscoveries.mazes._
import com.devdiscoveries.mazes.algorithms.{AldousBroder, BinaryTree, Sidewinder, Wilsons}
import com.devdiscoveries.mazes.solvers.Dijkstra

val maze = new Maze(5, 5)
  with SquareGrid
  with AldousBroder
  with Dijkstra

maze.printMaze
val distances = maze.distances(maze(1,1).get)
maze.rows.foreach{row =>
  val ds = row.map(cell => distances.find(_._2 == cell))
      .map(_.map(_._3).getOrElse(0))
  println(ds.mkString(" "))
}

val image = maze.
  renderMaze(50)
val path = "/home/hugovalk/projects/mazes/test.png"
val file = new File(path)
//ImageIO.write(image, "PNG", file)