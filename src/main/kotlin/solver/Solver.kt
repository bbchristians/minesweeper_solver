package solver

import MineField.MineField
import MineField.BombHitException

class Solver(val mineField: MineField, initialX: Int, initialY: Int) {

    init {
        mineField.revealTile(initialX, initialY)
    }

    fun solve() {
        var actionMade: Boolean
        do {
            actionMade = false
            try {
                val tileRanges: MutableList<TileRange> = mutableListOf()
                mineField.getRevealedTiles().forEach { tile ->
                    if( tile.number == 0 ) return@forEach
                    val newRange = TileRange(tile, this.mineField.getAdjacentTiles(tile.x, tile.y))
                    if (newRange.getBombPerc() == 1F) {
                        newRange.flagAllBombs()
                        actionMade = true
                    } else {
                        tileRanges.forEach { existentRange ->
                            val overlap = TileRangeOverlap(existentRange, newRange)
                            if (overlap.hasMeaningfulOverlapFirst()) {
                                println(overlap.hasMeaningfulOverlapFirst())
                                overlap.flagNoOverlap1()
                                println("Bombs Flagged")
                            }
                            if (overlap.hasMeaningfulOverlapSecond()) {
                                println("stop")
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
            } catch (e: BombHitException) {
                e.printStackTrace()
                actionMade = false
            }
        } while(actionMade)
    }


}