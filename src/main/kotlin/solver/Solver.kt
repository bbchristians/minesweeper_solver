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
                                actionMade = actionMade or overlap.revealNoOverlap2()
                            }
                            if (overlap.hasMeaningfulOverlapSecond()) {
                                overlap.flagNoOverlap2()
                                actionMade = actionMade or overlap.revealNoOverlap1()
                            }
//                            if (overlap.allInNoOverlap1AreBombs()) {
//                                overlap.flagNoOverlap1()
//                            }
//                            if (overlap.allInNoOverlap2AreBombs()) {
//                                overlap.flagNoOverlap2()
//                            }
                            if (overlap.noneInNoOverlap1AreBombs()) {
                                actionMade = actionMade or overlap.revealNoOverlap1()
                            }
                            if (overlap.noneInNoOverlap2AreBombs()) {
                                actionMade = actionMade or overlap.revealNoOverlap2()
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
                    val bestGuessRange = tileRanges.minBy { range ->
                        val perc = range.getBombPerc()
                        if( perc == 0F ) 1F else perc
                    }
                    if( bestGuessRange?.getBombPerc() == 0F ) continue
                    println(this.mineField)
                    chanceToWin *= 1 - (bestGuessRange?.getBombPerc() ?: 0F)
                    println("--win%=${chanceToWin*100}%--")
                    // We can cheat to reveal the bomb, so we record the chance of failure up until this point
                    bestGuessRange?.cheatRevealOneNoBomb()
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