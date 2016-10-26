package com.devdiscoveries.mazes.algorithms

import com.devdiscoveries.mazes.{Cell, Grid}

/**
  * Created by hugovalk on 19-10-16.
  */
trait MazeAlgorithm {
  this: Grid =>
  type Link = (Cell, Cell)
  def links: Seq[Link]

  def linkedNeighbors(cell: Cell) = neighbors(cell).filter(linked(_, cell))

  def linked(cell1: Cell, cell2: Cell): Boolean =
    links.contains((cell1, cell2)) || links.contains((cell2, cell1))
}
