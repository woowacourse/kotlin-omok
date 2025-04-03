package woowacourse.omok.ui.mapper

import woowacourse.omok.data.model.OmokBoardDto
import woowacourse.omok.data.model.OmokGameDto
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.StoneColor

fun OmokGame.toData(): OmokGameDto =
    OmokGameDto(
        gameId = id,
        lastTurn = currentTurn.name,
        board = board.toData(),
    )

fun OmokGameDto.toUI(): OmokGame =
    OmokGame(
        id = gameId,
        board = board.toUI(),
        firstStone = StoneColor.valueOf(lastTurn),
    )

fun OmokBoard.toData(): OmokBoardDto {
    val board =
        snapshot
            .mapKeys { Pair(it.key.row, it.key.column) }
            .mapValues { it.value.name }
    return OmokBoardDto(board)
}

fun OmokBoardDto.toUI(): OmokBoard {
    val board =
        positions
            .mapKeys { Position(it.key.first, it.key.second) }
            .mapValues { PointState.valueOf(it.value) }
    return OmokBoard(board.toMutableMap())
}
