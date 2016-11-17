package com.devdiscoveries.mazes2


import scala.util.Random

/**
  * Created by hugovalk on 15-11-16.
  */
trait MazeService {
  import Maze._

  def linked[Cell](cell1: Cell, cell2: Cell, links: Seq[Link[Cell]]): Boolean =
    links.contains((cell1, cell2)) || links.contains((cell2, cell1))

  def printMaze[Cell](maze: Maze[Cell]) = maze.grid match {
    case squareMaze: SquareGrid[Cell] => printSquareMaze(squareMaze, maze.links)
    case _ => println("not supported")
  }

  def printSquareMaze[Cell](squareGrid: SquareGrid[Cell], links: Seq[Link[Cell]]) = {
    def eastBoundary(cell: Cell): String =
      if (squareGrid.eastOf(cell).exists(linked(cell, _, links))) " " else "|"

    def southBoundary(cell: Cell): String =
      if (squareGrid.southOf(cell).exists(linked(cell, _, links))) "   " else "---"

    println("+" + "---+" * squareGrid.nCols)
    squareGrid._cells.foreach{ row =>
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
object MazeService extends MazeService

case class Maze[Cell](grid: Grid[Cell], links: Seq[Maze.Link[Cell]])

object Maze {
  import MazeAlgorithms._
  type Link[Cell] = (Cell, Cell)

  def aldousBroderMaze[Cell](grid: Grid[Cell]): Maze[Cell] =
    Maze(grid, aldousBroder(grid))
}

object MazeAlgorithms {
  import Maze._
  def aldousBroder[Cell](grid: Grid[Cell]): Seq[Link[Cell]]= {
    def loop(currentCell: Cell, numberOfCellsInGrid: Int, visited: Set[Cell], result: List[Link[Cell]]): List[Link[Cell]] = {
      if (visited.size == numberOfCellsInGrid) result
      else {
        val _neighbours = grid.neighbors(currentCell)
        val next = _neighbours(Random.nextInt(_neighbours.size))
        visited.contains(next) match {
          case true => loop(next, numberOfCellsInGrid, visited + currentCell, result)
          case false => loop(next, numberOfCellsInGrid, visited + currentCell, (currentCell, next) :: result)
        }
      }
    }
    loop(grid.cells(Random.nextInt(grid.cells.size)), grid.cells.size, Set(), List())
  }
}

sealed trait Grid[Cell] {
  def cells: Seq[Cell]
  def neighbors(cell: Cell): Seq[Cell]
}

object Grid {
  def squareGrid[Cell](nRows: Int, nCols: Int, f: (Int,Int) => Cell): SquareGrid[Cell] = {
    val cells: Vector[Vector[Cell]] =
      for {
        (row, rowIndex) <- Vector.fill(nRows)(Vector()).zipWithIndex
      } yield Vector.fill(nCols)(rowIndex).zipWithIndex.map(e => f(e._1 + 1, e._2 + 1))
    SquareGrid(cells, nRows, nCols)
  }
}

case class SquareGrid[Cell] private (_cells: Vector[Vector[Cell]], nRows: Int, nCols: Int) extends Grid[Cell] {
  def apply(row: Int, col: Int): Option[Cell] =
    _cells.applyOrElse(row - 1, (e: Int) => Vector[Cell]()).map(Some(_))
      .applyOrElse(col - 1, (e: Int) => None)

  def rowOf(cell: Cell): Int =
    _cells.find(_.contains(cell)).map(_cells.indexOf(_) + 1).getOrElse(-1)

  def colOf(cell: Cell): Int =
    _cells.find(_.contains(cell)).map(_.indexOf(cell) + 1).getOrElse(-1)


  override def cells: Seq[Cell] = _cells.flatten

  override def neighbors(cell: Cell): Seq[Cell] =
    List(northOf(cell), southOf(cell), eastOf(cell), westOf(cell))
      .filter(_.isDefined).map(_.get)

  def northOf(cell: Cell): Option[Cell] = apply(rowOf(cell) - 1, colOf(cell))
  def southOf(cell: Cell): Option[Cell] = apply(rowOf(cell) + 1, colOf(cell))
  def westOf(cell: Cell): Option[Cell] = apply(rowOf(cell), colOf(cell) - 1)
  def eastOf(cell: Cell): Option[Cell] = apply(rowOf(cell), colOf(cell) + 1)
}

case class Cell(row: Int, col: Int)