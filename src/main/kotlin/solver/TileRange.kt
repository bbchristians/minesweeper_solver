package solver

import MineField.Tile

class TileRange(val numberTile: Tile, val tiles: List<Tile>) {

    fun getBombPerc(): Float {
        val adjacentUnrevealedTiles = tiles.count { !it.isRevealed && !it.isFlagged }.toFloat()
        if( adjacentUnrevealedTiles > 0 )
            return (numberTile.number - tiles.count { it.isFlagged }) / adjacentUnrevealedTiles
        return 0F
    }

    fun getNumberOfPossibleTilesInOverlap(other: TileRange): Int {
        return tiles.filter{
            it in other.tiles
        }.count { !it.isRevealed && !it.isFlagged }
    }

    fun overlaps(other: TileRange): Boolean {
        this.tiles.forEach { thisTile -> other.tiles.forEach { if( it == thisTile ) return true } }
        return false
    }

    fun flagAllBombs() {
        this.tiles.forEach {
            it.flag()
        }
    }

    fun revealFreeIfPossible(): Boolean {
        if( this.tiles.count { it.isFlagged } == this.numberTile.number ) {
            var revealedTile = false
            this.tiles.forEach {
                revealedTile = revealedTile || it.reveal()
            }
            return revealedTile
        }
        return false
    }
}