package woowacourse.omok.model.player

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.rule.ban.ForbiddenPlace

class Player(val stone: Stone, private val forbiddenPlaces: List<ForbiddenPlace> = listOf()) {
    fun canPlace(
        board: Board,
        position: Position,
    ): Boolean {
        return forbiddenPlaces.all { it.availablePosition(board, position) }
    }
}
