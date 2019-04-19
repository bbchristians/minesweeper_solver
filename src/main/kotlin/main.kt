import MineField.MineField
import solver.Solver

fun main() {

    val numBombs = 95
    val numberOfAttempts = 100

    val mineField = MineField(20, 12, numBombs)

    val solver = Solver(mineField, 6, 15)
    val chanceToWin = solver.solve()

    println(mineField)
    println("Flagged ${mineField.getNumberOfFlags()}/$numBombs bombs")
    println("Chance to win: ${chanceToWin*100}%")
    println("Best Chance to win from $numberOfAttempts attempts: ${((0..numberOfAttempts).map {
        val mineField = MineField(20, 12, numBombs)

        val solver = Solver(mineField, 6, 15)
        solver.solve()
    }.max()?:0.0) * 100}%")
}