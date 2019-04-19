package solver

import MineField.BombHitException
import MineField.Tile

class TileRange(val numberTile: Tile, val tiles: List<Tile>) {

    fun getBombPerc(): Float {
        val adjacentUnrevealedTiles = this.getUnrevealedTiles().toFloat()
        if( adjacentUnrevealedTiles > 0 )
            return this.getRemainingBombCount() / adjacentUnrevealedTiles
        return 0F
    }

    fun flagAllBombs() {
        this.tiles.forEach {
            it.flag()
        }
    }

    fun getUnrevealedTiles(): Int {
        return tiles.count { !it.isRevealed && !it.isFlagged }
    }

    fun getRemainingBombCount():Int {
        return numberTile.number - this.getNumberOfAdjacentFlags()
    }

    fun getNumberOfAdjacentFlags(): Int {
        return this.tiles.count { it.isFlagged }
    }

    fun revealFreeIfPossible(): Boolean {
        if( getNumberOfAdjacentFlags() == this.numberTile.number ) {
            var revealedTile = false
            this.tiles.forEach {
                try {
                    revealedTile = revealedTile || it.revealInField()
                } catch(e: BombHitException) {
                    throw BombHitException(e.message + ": False tile flagged.")
                }
            }
            return revealedTile
        }
        return false
    }

    fun cheatRevealOneBomb() {
        this.tiles.shuffled().forEach {
            if( it.getIsBomb() ) {
                it.flag()
                return
            }
        }
    }

    fun cheatRevealOneNoBomb() {
        this.tiles.shuffled().forEach {
            if( !it.getIsBomb() && !it.isRevealed ) {
                it.revealInField()
                return
            }
        }
    }
}