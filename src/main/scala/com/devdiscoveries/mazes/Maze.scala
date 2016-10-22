package com.devdiscoveries.mazes

import com.devdiscoveries.mazes.algorithms.MazeAlgorithm

/**
  * Created by hugovalk on 19-10-16.
  */
class Maze(val nrows: Int, val ncols: Int) {
  this: MazeAlgorithm with Grid =>

  def eastBoundary(cell: Cell): String =
    if (eastOf(cell).exists(linked(cell, _))) {
      " "
    } else {
      "|"
    }

  def southBoundary(cell: Cell): String =
    if (southOf(cell).exists(linked(cell, _))) {
      "   "
    } else {
      "---"
    }

  def printMaze = {
    println("+" + "---+" * ncols)
    rows.foreach{ row =>
      print("|")
      row.foreach{ cell =>
        print("   ")
        print(eastBoundary(cell))
      }
      print("\n+")
      row.foreach{ cell =>
        print(southBoundary(cell))
        print("+")
      }
      println("")
    }
  }
}
