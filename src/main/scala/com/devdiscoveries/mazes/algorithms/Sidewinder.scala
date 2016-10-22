package com.devdiscoveries.mazes.algorithms

import com.devdiscoveries.mazes.{Cell, Grid}

import scala.util.Random

/**
  * Created by hugovalk on 21-10-16.
  */
trait Sidewinder extends MazeAlgorithm {
  this: Grid =>

  override val links: Seq[(Cell, Cell)] =
    rows.flatMap { row =>
      def shouldCloseRun(cell: Cell) =
        eastOf(cell).isEmpty || (!northOf(cell).isEmpty && Random.nextBoolean())

      def linksFromRun(run: Seq[Cell]): List[(Cell, Cell)] = {
        val links = run.toList.reverse.tail.map(cell => (cell, eastOf(cell).get))
        val cellToLinkWithNorth = run(Random.nextInt(run.size))
        northOf(cellToLinkWithNorth)
            .map(north => (cellToLinkWithNorth, north) :: links)
            .getOrElse(links)
      }

      def doRun(current: Seq[Cell]): List[(Cell, Cell)] = {
        if (current.isEmpty) {
          List()
        } else {
          val runSize = current.takeWhile(cell => !shouldCloseRun(cell)).size + 1
          doRun(current.drop(runSize)) ++ linksFromRun(current.take(runSize))
        }
      }
      doRun(row)
    }

}
