package com.devdiscoveries.mazes.solvers

import com.devdiscoveries.mazes.{Cell, Grid}
import com.devdiscoveries.mazes.algorithms.MazeAlgorithm

/**
  * Created by hugovalk on 25-10-16.
  */
trait Dijkstra extends MazeSolver {
  this: Grid with MazeAlgorithm =>

  def distances(root: Cell): Seq[Distance] = {
    def loop(currentCells: Seq[Cell], length: Int, visited: Set[Cell], results: Set[Distance]): Set[Distance] = {
      if (visited.size == cells.size) results
      else {
        val newResults = currentCells.map((root, _, length)).toSet
        val newCells = currentCells.flatMap(linkedNeighbors).filter(!visited.contains(_))
        loop(newCells, length + 1, currentCells.toSet ++ visited, newResults ++ results)
      }
    }
    loop(Seq(root), 0, Set(), Set()).toSeq
  }


}
