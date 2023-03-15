class PlayingBoard(placedStones: List<Stone>) : BasedBoard(placedStones.toList()) {
    constructor(vararg stone: Stone) : this(stone.toList())

    override val winningColor: Color
        get() {
            throw IllegalStateException(PLAYING_GAME_ERROR)
        }

    override val isFinished: Boolean = false

    override fun isPossiblePut(point: Position): Boolean =
        !placedStones.any { stone -> stone.point == point }

    override fun putStone(stone: Stone): Board {
        // TODO: 함수 분리
        if (isPossiblePut(stone.point).not()) throw IllegalArgumentException(PLACED_STONE_ERROR)
        val nextStones = getStones() + stone
        val omokResult = OmokResult.valueOf(nextStones, stone.color)
        return when (omokResult) {
            OmokResult.FIVE_STONE_WINNING -> FinishedBoard(nextStones, stone.color)
            OmokResult.RUNNING -> PlayingBoard(nextStones)
        }
    }

    companion object {
        private const val PLAYING_GAME_ERROR = "[ERROR] 현재 게임이 진행중입니다."
        private const val PLACED_STONE_ERROR = "[ERROR] 이미 놓아진 돌이 있습니다."
    }
}
