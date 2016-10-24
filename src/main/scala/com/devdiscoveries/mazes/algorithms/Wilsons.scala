package com.devdiscoveries.mazes.algorithms

import com.devdiscoveries.mazes.{Cell, Grid}

import scala.util.Random

/**
  * Created by hugovalk on 24-10-16.
  */
trait Wilsons extends MazeAlgorithm {
  this: Grid =>

  override val links: Seq[Link] = {
    def loop(unvisited: List[Cell], results: List[Link]): List[Link] = {
      if (unvisited.isEmpty) results
      else {
        def loopEraseRandomWalk(path: List[Cell]): List[Cell] = {
          if (!unvisited.contains(path.head)) path
          else {
            val newCell = neighbors(path.head)(Random.nextInt(neighbors(path.head).size))
            if(path.contains(newCell)) loopEraseRandomWalk(path.dropWhile(_ != newCell))
            else loopEraseRandomWalk(newCell :: path)
          }
        }
        val path = loopEraseRandomWalk(List(unvisited(Random.nextInt(unvisited.size))))
        val newLinks = path.tail.foldLeft((path.head, List[Link]()))((res, cell) => (cell, (res._1, cell) :: res._2))._2
        loop(unvisited.filter(!path.contains(_)), newLinks ++ results)
      }
    }
    val start = cells(Random.nextInt(cells.size))
    loop(cells.filter(_ != start).toList, List())
  }

}
