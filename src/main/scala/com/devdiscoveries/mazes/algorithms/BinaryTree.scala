package com.devdiscoveries.mazes.algorithms

import com.devdiscoveries.mazes.{Cell, Grid}

import scala.util.Random

/**
  * Created by hugovalk on 19-10-16.
  */
trait BinaryTree extends MazeAlgorithm {
  this: Grid =>

  override val links: Seq[Link] =
    cells.map{ cell =>
      val neighbor = List(northOf(cell), eastOf(cell)).filter(_.isDefined).map(_.get) match {
        case List() => None
        case neighbors => Some(neighbors(Random.nextInt(neighbors.size)))
      }
      neighbor.map((cell, _))
    }.filter(_.isDefined).map(_.get)
}
