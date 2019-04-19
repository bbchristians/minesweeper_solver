package solver

import MineField.MineField
import MineField.BombHitException

class Solver(val mineField: MineField, initialX: Int, initialY: Int) {

    init {
        mineField.revealTile(initialX, initialY)
    }

    fun solve(): Double {
        var actionMade: Boolean
        var chanceToWin = 1.0
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
                                overlap.flagNoOverlap1()
                            }
                            if (overlap.hasMeaningfulOverlapSecond()) {
                                overlap.flagNoOverlap2()
                            }
                        }
                        tileRanges.add(newRange)
                    }
                }
                // Clean up any free to open spaces
                tileRanges.forEach { range ->
                    actionMade = actionMade || range.revealFreeIfPossible()
                }
                // No guaranteed actions were possible, so we must guess
                if( !actionMade ) {
                    val bestGuessRange = tileRanges.maxBy { range ->
                        range.getBombPerc()
                    }
                    if( bestGuessRange?.getBombPerc() == 0F ) continue
                    chanceToWin *= (bestGuessRange?.getBombPerc() ?: 1F)
                    bestGuessRange?.cheatRevealOneBomb()
                    actionMade = true
                }
            } catch (e: BombHitException) {
                e.printStackTrace()
                actionMade = false
            }
        } while(actionMade)
        return chanceToWin
    }


}