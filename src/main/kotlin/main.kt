import MineField.MineField
import solver.Solver

fun main() {

    val mineField = MineField(20, 12, 95)

    println("-----------------")
    val solver = Solver(mineField, 6, 15)
    solver.solve()

    println(mineField)
}