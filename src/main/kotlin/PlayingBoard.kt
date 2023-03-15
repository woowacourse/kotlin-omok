class PlayingBoard(placedStones: List<Stone>) : BasedBoard(placedStones.toList()) {
    constructor(vararg stone: Stone) : this(stone.toList())

    override val isFinished: Boolean = false
    override val isWin: Color
        get() {
            // TODO: 메시지 문구 추가하기
            throw IllegalStateException("")
        }

    override fun isPossiblePut(point: Point): Boolean =
        !placedStones.any { stone -> stone.point == point }

    override fun putStone(stone: Stone): Board {
        // TODO: 함수 분리
        val nextStones = getStones() + stone
        if (isPossiblePut(stone.point).not()) throw IllegalArgumentException("")
        val omokCondition = OmokCondition.valueOf(nextStones, stone.color)
        return when (omokCondition) {
            OmokCondition.FIVE_STONES_WINNING_CONDITION -> FinishedBoard(nextStones, stone.color)
            OmokCondition.RUNNING -> PlayingBoard(nextStones)
        }
    }
}
