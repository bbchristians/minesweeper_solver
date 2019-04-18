package solver

import MineField.BombHitException
import MineField.Tile

class TileRange(val numberTile: Tile, val tiles: List<Tile>) {

    fun getBombPerc(): Float {
        val adjacentUnrevealedTiles = tiles.count { !it.isRevealed && !it.isFlagged }.toFloat()
        if( adjacentUnrevealedTiles > 0 )
            return (numberTile.number - tiles.count { it.isFlagged }) / adjacentUnrevealedTiles
        return 0F
    }

    fun flagAllBombs() {
        this.tiles.forEach {
            it.flag()
        }
    }

    fun revealFreeIfPossible(): Boolean {
        if( getNumberOfAdjacentFlags() == this.numberTile.number ) {
            var revealedTile = false
            this.tiles.forEach {
                try {
                    revealedTile = revealedTile || it.reveal()
                } catch(e: BombHitException) {
                    throw BombHitException(e.message + ": False tile flagged.")
                }
            }
            return revealedTile
        }
        return false
    }

    fun getNumberOfAdjacentFlags(): Int {
        return this.tiles.count { it.isFlagged }
    }
}