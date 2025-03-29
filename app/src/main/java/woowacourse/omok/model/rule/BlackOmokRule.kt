package woowacourse.omok.model.rule

import omok.model.stone.position.Position
import woowacourse.omok.mapper.BlackRuleChecker
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class BlackOmokRule(
    private val blackRuleChecker: BlackRuleChecker,
) : OmokRule {
    override fun isWin(
        board: Board,
        lastStone: Stone,
    ): Boolean {
        if (lastStone.stoneColor != StoneColor.BLACK) return false
        val blackStones = board.getBlackStones()
        val whiteStones = board.getWhiteStones()

        return blackRuleChecker.checkWin(blackStones, whiteStones, lastStone.position)
    }

    override fun validate(
        board: Board,
        nextPosition: Position,
        color: StoneColor,
    ): PlacementError {
        val blackStones = board.getBlackStones()
        val whiteStones = board.getWhiteStones()

        return blackRuleChecker.checkFoul(blackStones, whiteStones, nextPosition)
    }
}
