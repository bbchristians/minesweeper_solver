package MineField

class Tile(val x: Int, val y: Int, private var isBomb: Boolean) {

    var isFlagged = false
    private set

    var number: Int = -1

    var isRevealed = false
    private set

    internal fun setIsBomb() {
        this.isBomb = true
    }

    internal fun flag() {
        if( this.isRevealed ) return
        this.isFlagged = true
    }

    internal fun getIsBomb(): Boolean {
        return this.isBomb;
    }

    internal fun reveal(): Boolean {
        if(this.isBomb) throw BombHitException("Bomb Hit at (${this.x},${this.y})")
        if( this.isFlagged || this.isRevealed ) return false
        this.isRevealed = true
        return true
    }

    override fun toString(): String {
        return if(this.isFlagged) {
            "F"
        } else if(this.isRevealed) {
            if( this.isBomb )
                "X"
            else
                this.number.toString()
        } else {
            "░"
        }
    }
}