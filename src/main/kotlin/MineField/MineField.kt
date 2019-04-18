package MineField

class MineField(val height: Int, val width: Int, val numBombs: Int) {

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
        }

    }

    private fun populateWithBombs(initialX: Int, initialY: Int) {
        // Add bombs 
        this.field.flatMap { 
            it.asIterable() 
        }.filter { 
            Math.abs(it.x - initialX) > 1 || Math.abs(it.y - initialY) > 1 
        }
            .shuffled()
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
                row += if (it.isFlagged) {
                    "F"
                } else if (it.getIsBomb()) {
                    "x"
                } else {
                    it.number
                }

            }
            row
        }
    }
}