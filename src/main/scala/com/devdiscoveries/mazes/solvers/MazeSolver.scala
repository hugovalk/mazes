package com.devdiscoveries.mazes.solvers

import com.devdiscoveries.mazes.Cell

/**
  * Created by hugovalk on 25-10-16.
  */
trait MazeSolver {
  type Distance = (Cell, Cell, Int)
}
