import com.devdiscoveries.mazes2.{Cell, Grid, Maze}
import com.devdiscoveries.mazes2.MazeService._

val grid = Grid.squareGrid(10, 10, (r,c) => Cell(r, c))

val maze = Maze.aldousBroderMaze(grid)
printMaze(maze)


