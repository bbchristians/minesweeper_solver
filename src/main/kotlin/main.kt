import MineField.MineField
import solver.Solver

fun main() {

    val numBombs = 50

    val mineField = MineField(20, 12, numBombs)

    println("-----------------")
    val solver = Solver(mineField, 6, 15)
    solver.solve()

    println(mineField)
    println("Flagged ${mineField.getNumberOfFlags()}/$numBombs bombs")
}