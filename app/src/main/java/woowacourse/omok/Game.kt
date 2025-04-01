package woowacourse.omok

import omok.domain.Turn
import woowacourse.omok.database.StoneDAO
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.FiveRule
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneType

class Game(
    private val omokBoard: Board,
    private val turn: Turn,
    private val stoneDao: StoneDAO,
) {
    private val fiveRule = FiveRule()

    fun putStone(
        row: Int,
        column: Int,
        color: StoneType,
    ): GameResult {
        if (omokBoard.isInvalidPosition(Position(row, column))) {
            return GameResult.InvalidMove("이미 돌을 놓은 자리입니다.")
        }

        if (turn.color == StoneType.BLACK && omokBoard.isInvalidBlackPosition(Stone(Position(row, column), turn.color))) {
            return GameResult.InvalidMove("흑돌이 놓을 수 없는 금수입니다.")
        }

        omokBoard.put(Position(row, column), turn.color)
        val stoneColor = if (color == StoneType.WHITE) "white" else "black"
        stoneDao.insertStone(row, column, stoneColor)

        return when {
            checkOmok() -> GameResult.Win(turn.color)
            omokBoard.isFull() -> GameResult.Draw
            else -> {
                GameResult.Continue(turn.color)
            }
        }
    }

    fun checkOmok(): Boolean {
        val lastStone = omokBoard.stones.lastStone()
        if (lastStone != null) {
            return fiveRule.isOmok(lastStone, omokBoard.stones)
        }
        return false
    }
}

sealed class GameResult {
    data class Win(val winner: StoneType) : GameResult()
    object Draw : GameResult()
    data class Continue(val nextTurn: StoneType) : GameResult()
    data class InvalidMove(val message: String) : GameResult()
}
