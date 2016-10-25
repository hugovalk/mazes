package com.devdiscoveries.mazes

import java.awt.{BasicStroke, Color}
import java.awt.image.BufferedImage

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

  def renderMaze(cellWidth: Int): BufferedImage = {
    val image = new BufferedImage(cellWidth * (ncols + 2), cellWidth * (nrows + 2), BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()
    graphics.setColor(Color.white)
    graphics.fillRect(0,0,cellWidth * (ncols + 2), cellWidth * (nrows + 2))
    graphics.setStroke(new BasicStroke(2))
    graphics.setColor(Color.black)

    for {
      row <- rows.zipWithIndex
      cell <- row._1.zipWithIndex
    } yield {
      val x_offset = (cell._2 + 1) * cellWidth
      val y_offset = (row._2 + 1) * cellWidth
      if (northOf(cell._1).isEmpty) graphics.drawLine(x_offset,y_offset,x_offset + cellWidth,y_offset)
      if (westOf(cell._1).isEmpty) graphics.drawLine(x_offset,y_offset,x_offset,y_offset + cellWidth)
      if (!eastOf(cell._1).exists(linked(cell._1, _))) graphics.drawLine(x_offset + cellWidth,y_offset,x_offset + cellWidth,y_offset + cellWidth)
      if (!southOf(cell._1).exists(linked(cell._1, _))) graphics.drawLine(x_offset,y_offset + cellWidth,x_offset + cellWidth,y_offset + cellWidth)
    }

    image
  }

  def save = ???
}
