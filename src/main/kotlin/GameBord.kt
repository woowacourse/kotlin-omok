class GameBord {
    var stones = listOf<Stone>()

    fun addStone(stone: Stone) {
        stones = stones.plus(stone)
    }
}
