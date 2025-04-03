package woowacourse.omok.ui.mapper

import woowacourse.omok.data.model.OmokBoardDto
import woowacourse.omok.data.model.OmokGameDto
import woowacourse.omok.data.model.OmokGamesDto
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.OmokGameEntity
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerName
import woowacourse.omok.domain.model.player.StoneColor

fun OmokGameEntity.toData(): OmokGameDto =
    OmokGameDto(
        id = id,
        host = host.value,
        lastTurn = lastTurn.name,
        board = board.toData(),
    )

fun OmokGameDto.toDomain(): OmokGameEntity =
    OmokGameEntity(
        id = id,
        host = PlayerName.create(host),
        board = board.toDomain(),
        lastTurn = StoneColor.valueOf(lastTurn),
    )

fun OmokBoard.toData(): OmokBoardDto {
    val board =
        snapshot
            .mapKeys { Pair(it.key.row, it.key.column) }
            .mapValues { it.value.name }
    return OmokBoardDto(board)
}

fun OmokBoardDto.toDomain(): OmokBoard {
    val board =
        positions
            .mapKeys { Position(it.key.first, it.key.second) }
            .mapValues { PointState.valueOf(it.value) }
    return OmokBoard(board.toMutableMap())
}

fun OmokGamesDto.toDomain(): List<OmokGameEntity> = games.map { it.toDomain() }
