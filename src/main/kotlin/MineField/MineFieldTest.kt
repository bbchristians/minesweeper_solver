package MineField

fun main() {
    val mineField = MineField(20, 12, 95)
    mineField.revealTile(6, 15)
    mineField.flagBomb(6, 17)
    println(mineField)
}