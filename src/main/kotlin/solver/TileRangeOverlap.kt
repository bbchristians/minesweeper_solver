package solver

import MineField.Tile
import java.lang.Integer.max
import java.lang.Integer.min

class TileRangeOverlap(val range1: TileRange, val range2: TileRange) {

    val overlap by lazy {
        range1.tiles.filter {
            it in range2.tiles
        }
    }

    val hasOverlap by lazy { overlap.isNotEmpty() }

    val noOverlap1 by lazy {
        range1.tiles.filter {
            it !in this.overlap && !it.isFlagged && !it.isRevealed
        }
    }

    val noOverlap2 by lazy {
        range2.tiles.filter {
            it !in this.overlap && !it.isFlagged && !it.isRevealed
        }
    }

    fun hasMeaningfulOverlapFirst(): Boolean {
        if( noOverlap1.size + range2.numberTile.number == range1.getRemainingBombCount() ) return true
        return false
    }

    fun hasMeaningfulOverlapSecond(): Boolean {
        if( noOverlap2.size + range1.numberTile.number == range2.getRemainingBombCount() ) return true
        return false
    }

    fun getNumberOfBombsRequiredInOverlap(): Int {
        return max(range1.getRemainingBombCount() - noOverlap1.size, range2.getRemainingBombCount() - noOverlap2.size)
    }

    fun getMaxNumberOfUnflaggedBombsInOverlap(): Int {
        return min(min(range1.getRemainingBombCount(), range2.getRemainingBombCount()), overlap.size)
    }

    fun allInNoOverlap1AreBombs(): Boolean {
        return range1.getRemainingBombCount() - getMaxNumberOfUnflaggedBombsInOverlap() == noOverlap1.size
    }

    fun noneInNoOverlap1AreBombs(): Boolean {
        return range1.getRemainingBombCount() == getNumberOfBombsRequiredInOverlap()
    }

    fun allInNoOverlap2AreBombs(): Boolean {
        return range2.getRemainingBombCount() - getMaxNumberOfUnflaggedBombsInOverlap() == noOverlap2.size
    }

    fun noneInNoOverlap2AreBombs(): Boolean {
        return range2.getRemainingBombCount() == getNumberOfBombsRequiredInOverlap()
    }

    fun flagNoOverlap1(): Boolean {
        return noOverlap1.map ( Tile::flag ).contains(true)
    }

    fun flagNoOverlap2(): Boolean {
        return noOverlap2.map ( Tile::flag ).contains(true)
    }

    fun revealNoOverlap1(): Boolean {
        return noOverlap1.map( Tile::revealInField ).contains(true)
    }

    fun revealNoOverlap2(): Boolean {
        return noOverlap2.map( Tile::revealInField ).contains(true)
    }
}