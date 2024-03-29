package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Player
import woowacourse.omok.domain.model.Position

sealed interface ValidPosition {
    fun valid(
        board: Board,
        position: Position,
        player: Player,
    ): Boolean
}

class EmptyPosition(private val handling: (Player, Position, String) -> Unit) : ValidPosition {
    override fun valid(
        board: Board,
        position: Position,
        player: Player,
    ): Boolean {
        if (!board.emptyPosition(position)) {
            handling(player, position, MESSAGE)
            return false
        }
        return true
    }

    companion object {
        private const val MESSAGE = "이미 돌이 있는 위치입니다."
    }
}

class AbideForbiddenRules(private val handling: (Player, Position, String) -> Unit) : ValidPosition {
    override fun valid(
        board: Board,
        position: Position,
        player: Player,
    ): Boolean {
        if (!board.validPosition(position, player)) {
            handling(player, position, MESSAGE)
            return false
        }
        return true
    }

    companion object {
        private const val MESSAGE = "금수 규칙에 따라 돌을 둘 수 없는 위치입니다."
    }
}
