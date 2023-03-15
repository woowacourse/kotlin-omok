class PlayingBoard(placedStones: List<Stone>) : BasedBoard(placedStones.toList()) {
    constructor(vararg stone: Stone) : this(stone.toList())

    override val winningColor: Color
        get() {
            // TODO: 메시지 문구 추가하기
            throw IllegalStateException("")
        }

    override val isFinished: Boolean = false

    override fun isPossiblePut(point: Point): Boolean =
        !placedStones.any { stone -> stone.point == point }

    override fun putStone(stone: Stone): Board {
        // TODO: 함수 분리
        val nextStones = getStones() + stone
        if (isPossiblePut(stone.point).not()) throw IllegalArgumentException("")
        val omokResult = OmokResult.valueOf(nextStones, stone.color)
        return when (omokResult) {
            OmokResult.FIVE_STONE_WINNING -> FinishedBoard(nextStones, stone.color)
            OmokResult.RUNNING -> PlayingBoard(nextStones)
        }
    }
}
