package MineField

class Tile(val x: Int, val y: Int, private var isBomb: Boolean) {

    var isFlagged = false
    private set

    var number: Int = -1

    fun setIsBomb() {
        this.isBomb = true
    }

    fun flag() {
        this.isFlagged = true
    }

    fun getIsBomb(): Boolean {
        return this.isBomb;
    }
}