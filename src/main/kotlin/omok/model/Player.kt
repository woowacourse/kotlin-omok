package omok.model

class Player(val color: Color) {
    var isWin: Boolean = false
        private set

    fun getStone(getCoordinate: () -> Coordinate): Stone {
        return Stone(color, getCoordinate())
    }

    fun checkOmok(
        stones: Stones,
        stone: Stone,
    ) {
        isWin = stones.findOmok(stone)
    }
}
