package MineField

import java.util.*

class MineField(val height: Int, val width: Int, val numBombs: Int) {

    val random = Random(1231212324)

    var isInitialized = false

    private val field = Array(
        height
    ) { y ->
        Array(
            this.width
        ) { x ->
            Tile(x, y, false)
        }
    }

    fun flagBomb(x: Int, y: Int) {
        this.field[y][x].flag()
    }

    fun revealTile(x: Int, y: Int) {
        if( !isInitialized ) {
            isInitialized = true
            populateWithBombs(x, y)
            getAdjacentTiles(x, y).forEach { tile ->
                revealTile(tile.x, tile.y)
            }
        } else {
            val tile = this.field[y][x]
            if( tile.isRevealed ) return
            tile.reveal()
            if( tile.number == 0 ) {
                getAdjacentTiles(x, y).forEach { tile ->
                    revealTile(tile.x, tile.y)
                }
            }
        }
    }

    fun getAdjacentTiles(x: Int, y: Int): List<Tile> {
        return ((Math.max(0, x-1) until (Math.min(this.width, x+2)))).flatMap { x ->
            ((Math.max(0, y-1) until (Math.min(this.height, y+2)))).map { y ->
                this.field[y][x]
            }
        }
    }

    fun getRevealedTiles(): List<Tile> {
        return this.field.flatMap { row ->
            row.filter { it.isRevealed }
        }
    }

    private fun populateWithBombs(initialX: Int, initialY: Int) {
        // Add bombs 
        this.field.flatMap { 
            it.asIterable() 
        }.filter { 
            Math.abs(it.x - initialX) > 1 || Math.abs(it.y - initialY) > 1 
        }
            .shuffled(random)
            .subList(0, this.numBombs)
            .forEach(Tile::setIsBomb)
        // Add Numbers
        this.field.forEachIndexed { y, arrayOfTiles ->
            arrayOfTiles.forEachIndexed { x, tile ->
                if( !tile.getIsBomb() ) {
                    tile.number = this.field.asList().subList(Math.max(0, y-1), Math.min(this.height, y+2)).flatMap {
                        it.asList().subList(Math.max(0, x-1), Math.min(this.width, x+2))
                    }.count {
                        it.getIsBomb()
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return this.field.joinToString("\n") {
            var row = ""
            it.forEach {
                row += it

            }
            row
        }
    }
}