package woowacourse.omok.domain

import woowacourse.omok.domain.exception.RuleViolationException
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.domain.stone.Stones

class GameBoard {
    val stones = Stones()

    fun putStone(
        position: Position,
        omokGameStatus: OmokGame,
    ): Result<StoneColor> {
        val turnColor = omokGameStatus.turnColor()
        val stone = Stone.of(position, turnColor)
        val violationType = omokGameStatus.violation(stone, stones, omokGameStatus.currentPlayer())
        if (violationType.isNone()) {
            stones.add(stone)
            return Result.success(turnColor)
        }

        return Result.failure(RuleViolationException(violationType))
    }

    fun placedAllStones(): List<Stone> = stones.value

    fun restoreStones(
        existedStones: List<Stone>,
        omokGameStatus: OmokGame,
    ) {
        existedStones.forEach { stone -> stones.add(stone) }
        omokGameStatus.lastStone(this)?.let { stone ->
            omokGameStatus.adjustPlayerTurn(stone)
        }
    }
}
