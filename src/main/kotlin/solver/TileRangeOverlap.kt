package solver

import MineField.Tile

class TileRangeOverlap(val range1: TileRange, val range2: TileRange) {

    val overlap by lazy {
        range1.tiles.filter {
            it in range2.tiles
        }
    }

    val hasOverlap by lazy { overlap.isNotEmpty() }

    val noOverlap1 by lazy {
        range1.tiles.filter {
            it !in this.overlap
        }
    }

    val noOverlap2 by lazy {
        range2.tiles.filter {
            it !in this.overlap
        }
    }

    fun hasMeaningfulOverlapFirst(): Boolean {
        if( noOverlap1.size - range2.numberTile.number == range1.numberTile.number - range1.getNumberOfAdjacentFlags() ) return true
        return false
    }

    fun hasMeaningfulOverlapSecond(): Boolean {
        if( noOverlap2.size - range1.numberTile.number == range2.numberTile.number - range2.getNumberOfAdjacentFlags() ) return true
        return false
    }

    fun flagNoOverlap1() {
        noOverlap1.forEach ( Tile::flag )
    }

    fun flagNoOverlap2() {
        noOverlap2.forEach ( Tile::flag )
    }
}