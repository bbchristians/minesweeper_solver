package solver

import MineField.MineField

class Solver(val mineField: MineField, initialX: Int, initialY: Int) {

    init {
        mineField.revealTile(initialX, initialY)
    }

    fun solve() {
        var actionMade: Boolean
        do {
            actionMade = false
            val tileRanges: MutableList<TileRange> = mutableListOf()
            mineField.getRevealedTiles().forEach { tile ->
                val newRange = TileRange(tile, this.mineField.getAdjacentTiles(tile.x, tile.y))
                if( newRange.getBombPerc() == 1F ) {
                    newRange.flagAllBombs()
                    actionMade = true
                }
                else {
                    tileRanges.forEach { existentRange ->
                        val overlap = TileRangeOverlap(existentRange, newRange)
                        if (overlap.hasMeaningfulOverlapFirst()) {
                            overlap.flagNoOverlap1()
                            println("Bombs Flagged")
                        }
                        if (overlap.hasMeaningfulOverlapSecond()) {
                            overlap.flagNoOverlap2()
                            println("Bombs Flagged")
                        }
                    }
                    tileRanges.add(newRange)
                }
            }
            // Clean up any free to open spaces
            tileRanges.forEach { range ->
                actionMade = actionMade || range.revealFreeIfPossible()
            }
        } while(actionMade)
    }


}