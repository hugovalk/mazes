package com.devdiscoveries.mazes.algorithms

import com.devdiscoveries.mazes.Cell

/**
  * Created by hugovalk on 19-10-16.
  */
trait MazeAlgorithm {
  type Link = (Cell, Cell)
  def links: Seq[Link]

  def linked(cell1: Cell, cell2: Cell): Boolean =
    links.contains((cell1, cell2)) || links.contains((cell2, cell1))
}
