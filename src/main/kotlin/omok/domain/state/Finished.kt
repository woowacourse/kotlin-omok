package omok.domain.state

import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones
import rule.wrapper.point.Point

abstract class Finished(
    override val blackStones: BlackStones,
    override val whiteStones: WhiteStones,
) : State {
    abstract val winnerColor: StoneColor?

    override fun place(
        point: Point,
        boardSize: Int,
    ): State = throw IllegalStateException(ERROR_GAME_FINISHED)

    override fun lastStonePoint(): Point = throw IllegalStateException(ERROR_GAME_FINISHED)

    override fun nextStoneColor(): StoneColor = throw IllegalStateException(ERROR_GAME_FINISHED)

    companion object {
        private const val ERROR_GAME_FINISHED = "[ERROR] 게임이 종료되었습니다."
    }
}
