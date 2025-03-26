package omok.model.rule

import omok.mapper.BlackRuleChecker
import omok.model.board.Board
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position
import woowacourse.omok.model.rule.PlacementError

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
