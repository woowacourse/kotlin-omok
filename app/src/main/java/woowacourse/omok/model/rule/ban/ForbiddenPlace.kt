package woowacourse.omok.model.rule.ban

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.BlackStonePlace
import woowacourse.omok.model.game.PlaceType
import woowacourse.omok.model.game.WhiteStonePlace

interface ForbiddenPlace {
    fun availablePosition(
        board: Board,
        position: Position,
        stone: Stone,
    ): PlaceType

    fun canPlaceType(stone: Stone): PlaceType {
        return if (stone == Stone.BLACK) BlackStonePlace else WhiteStonePlace
    }
}
