package woowacourse.omok.model.player

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.BlackStonePlace
import woowacourse.omok.model.game.PlaceType
import woowacourse.omok.model.game.WhiteStonePlace
import woowacourse.omok.model.rule.ban.ForbiddenPlace

class Player(val stone: Stone, private val forbiddenPlaces: List<ForbiddenPlace> = listOf()) {
    fun placeType(
        board: Board,
        position: Position,
    ): PlaceType {
        val placeTypes = forbiddenPlaces.map { it.availablePosition(board, position, stone) }
        return placeTypes.firstOrNull { !it.canPlace() } ?: run {
            if (stone == Stone.BLACK) BlackStonePlace else WhiteStonePlace
        }
    }
}
