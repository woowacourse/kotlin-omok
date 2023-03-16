package domain.stone

class Board {

    val board: MutableList<MutableList<StoneType>> = MutableList(16) { MutableList(16) { StoneType.EMPTY } }
    val stones: Stones = Stones()

    fun putStone(stone: Stone) {
        board[(stone.position.y)][stone.position.x] = stone.type
        stones.add(stone)
    }

    companion object {
        const val BOARD_SIZE = 15
    }
}
