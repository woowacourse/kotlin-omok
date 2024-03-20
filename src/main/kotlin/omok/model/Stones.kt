package omok.model

class Stones {
    val blackStones: BlackStones = BlackStones()
    val whiteStones: WhiteStones = WhiteStones()

    fun putStone(stone: Stone) {
        val possibility = checkOccupiedCoordinate(stone.coordinate)
        when (possibility) {
            true -> addStone(stone)
            false -> throw IllegalStateException(ERROR_CANT_PUT_STONE)
        }
    }

    private fun checkOccupiedCoordinate(coordinate: Coordinate): Boolean {
        val blackStonesOccupied = blackStones.checkOccupiedCoordinate(coordinate)
        val whiteStonesOccupied = whiteStones.checkOccupiedCoordinate(coordinate)
        return !blackStonesOccupied && !whiteStonesOccupied
    }

    private fun addStone(stone: Stone) {
        when (stone.color) {
            Color.BLACK -> blackStones.put(stone)
            Color.WHITE -> whiteStones.put(stone)
        }
    }

    companion object {
        const val ERROR_CANT_PUT_STONE = "이미 돌이 착수된 위치입니다."
    }
}
