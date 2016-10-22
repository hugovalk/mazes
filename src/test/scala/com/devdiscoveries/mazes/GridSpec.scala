package com.devdiscoveries.mazes

import org.scalatest.{Matchers, WordSpec}

/**
  * Testing the Grid.
  */
class GridSpec extends WordSpec with Matchers {
  class SimpleGrid(val nrows: Int, val ncols: Int) extends SquareGrid

  "A square grid" when {
    val grid = new SimpleGrid(10, 20)
    "initialized" should {
      "have rows and cols properties" in {
        grid.nrows should be(10)
        grid.ncols should be(20)
      }
      "have an apply method which" should {
        "be able to return the first cell" in {
          grid(1, 1) should be(Some(Cell(1,1)))
        }
        "be able to return the last cell" in {
          grid(10, 20) should be(Some(Cell(10, 20)))
        }
        "return None when cell coordinates are out of bounds" in {
          grid(0, 1) should be(None)
          grid(10, 21) should be(None)
        }
      }
    }
  }
}
