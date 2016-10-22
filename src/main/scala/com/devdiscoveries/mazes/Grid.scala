package com.devdiscoveries.mazes

import scala.util.Random

sealed trait Grid {
  def nrows: Int
  def ncols: Int
  def apply(row: Int, col: Int): Option[Cell]
  def rows: Seq[Seq[Cell]]
  def cells: Seq[Cell]
  def neighbors(cell: Cell): Seq[Cell] =
    List(northOf(cell), southOf(cell), eastOf(cell), westOf(cell))
      .filter(_.isDefined).map(_.get)
  def northOf(cell: Cell): Option[Cell]
  def southOf(cell: Cell): Option[Cell]
  def westOf(cell: Cell): Option[Cell]
  def eastOf(cell: Cell): Option[Cell]
}

trait SquareGrid extends Grid {

  val _cells: Vector[Vector[Option[Cell]]] =
    for {
      (row, rowIndex) <- Vector.fill(nrows)(Vector()).zipWithIndex
    } yield Vector.fill(ncols)(rowIndex).zipWithIndex.map(e => Some(Cell(e._1 + 1, e._2 + 1)))

  def apply(row: Int, col: Int): Option[Cell] =
      _cells.applyOrElse(row - 1, (e: Int) => Vector[Option[Cell]]())
          .applyOrElse(col - 1, (e:Int) => None)

  def randomCell: Cell = {
    val row = Random.nextInt(nrows) + 1
    val col = Random.nextInt(ncols) + 1
    apply(row, col).get
  }

  def size: Int = nrows * ncols

  def foreachRow[U](f: Vector[Option[Cell]] => U) =
    _cells.foreach(f)

  def foreach[U](f: Cell => U) =
    _cells.flatten.foreach(someCell => f(someCell.get))

  def cells: Seq[Cell] = _cells.flatten.map(_.get)

  def rows: Seq[Seq[Cell]] = _cells.map(row => row.map(_.get))

  def northOf(cell: Cell): Option[Cell] = apply(cell.row - 1, cell.col)
  def southOf(cell: Cell): Option[Cell] = apply(cell.row + 1, cell.col)
  def westOf(cell: Cell): Option[Cell] = apply(cell.row, cell.col - 1)
  def eastOf(cell: Cell): Option[Cell] = apply(cell.row, cell.col + 1)
}

case class Cell(row: Int, col: Int)