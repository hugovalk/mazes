package com.devdiscoveries.mazes.algorithms

import com.devdiscoveries.mazes.{Cell, Grid}

import scala.util.Random

/**
  * Created by hugovalk on 22-10-16.
  */
trait AldousBroder extends MazeAlgorithm {
  this: Grid =>

  override lazy val links: Seq[Link] = {
    def loop(currentCell: Cell, visited: Set[Cell], result: List[Link]): List[Link] = {
      if (visited.size == cells.size) result
      else {
        val _neighbours = neighbors(currentCell)
        val next = _neighbours(Random.nextInt(_neighbours.size))
        visited.contains(next) match {
          case true => loop(next, visited + currentCell, result)
          case false => loop(next, visited + currentCell, (currentCell, next) :: result)
        }
      }
    }
    loop(cells(Random.nextInt(cells.size)), Set(), List())
  }

}
