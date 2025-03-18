package omok.model

class Board private constructor(
    private val stones: List<List<StoneState>> = emptyList(),
    private val lastStoneState: StoneState = StoneState.NONE,
) {
    fun placeStone(stone: Stone): Board {
        if (stonePlaced(stone.position)) {
            throw IllegalStateException()
        }
        check(stone.stoneState != lastStoneState) { "같은 색의 돌을 연속하여 착수할 수 없습니다" }

        val (newStoneX, newStoneY) = stone.position

        val newStones: List<List<StoneState>> =
            stones.mapIndexed { y, row ->
                if (y == newStoneY.value) {
                    row.mapIndexed { x, state ->
                        if (x == newStoneX.value) stone.stoneState else state
                    }
                } else {
                    row
                }
            }

        return Board(newStones, stone.stoneState)
    }

    private fun stonePlaced(position: Position): Boolean {
        val (x, y) = position.row to position.col

        return (stones[x.value][y.value] != StoneState.NONE)
    }

    companion object {
        private const val BOARD_SIZE = 15

        fun initBoard(): Board {
            val initStones: List<List<StoneState>> =
                List(BOARD_SIZE) {
                    List(BOARD_SIZE) { StoneState.NONE }
                }
            return Board(initStones)
        }
    }
}
