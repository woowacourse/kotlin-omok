package woowacourse.omok.model.rule.ban

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.DoubleOpenThreePlace
import woowacourse.omok.model.game.PlaceType
import woowacourse.omok.model.rule.RuleAdapter
import woowacourse.omok.model.rule.library.ThreeThreeRule

class DoubleOpenThreeForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(
        board: Board,
        position: Position,
        stone: Stone,
    ): PlaceType {
        if (RuleAdapter.abideRule(ThreeThreeRule(board.size), board, position)) {
            return canPlaceType(stone)
        }
        return DoubleOpenThreePlace
    }
}
