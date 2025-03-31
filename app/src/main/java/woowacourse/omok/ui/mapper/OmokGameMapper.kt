package woowacourse.omok.ui.mapper

import woowacourse.omok.data.model.OmokGameDto
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.StoneColor

fun OmokGameDto.toUI(): OmokGame {
    val board =
        board
            .mapKeys { Position(it.key.first, it.key.second) }
            .mapValues { PointState.valueOf(it.value) }
    return OmokGame(
        id = gameId,
        board = OmokBoard(board.toMutableMap()),
        firstStone = StoneColor.valueOf(lastTurn),
    )
}

fun OmokGame.toData(): OmokGameDto {
    val board =
        board.snapshot
            .mapKeys { Pair(it.key.row, it.key.column) }
            .mapValues { it.value.name }
    return OmokGameDto(
        gameId = id,
        lastTurn = currentTurn.name,
        board = board,
    )
}
