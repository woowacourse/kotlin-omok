package omok.model

class Board(val stones: Stones) {
    fun putStone(stone: Stone) {
        stones.putStone(stone)
    }
}
