package woowacourse.omok.domain.mapper

import woowacourse.omok.data.model.OmokGameInfoDto
import woowacourse.omok.domain.model.game.OmokGameEntity
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.StoneColor

fun OmokGameInfoDto.toDomain(): OmokGameEntity {
    val board =
        board
            .mapKeys { Position(it.key.first, it.key.second) }
            .mapValues { PointState.valueOf(it.value) }
    return OmokGameEntity(
        lastTurn = StoneColor.valueOf(lastTurn),
        board = OmokBoard(board.toMutableMap()),
    )
}

fun OmokGameEntity.toData(): OmokGameInfoDto {
    val board =
        board.snapshot
            .mapKeys { Pair(it.key.row, it.key.column) }
            .mapValues { it.value.name }
    return OmokGameInfoDto(
        lastTurn = lastTurn.name,
        board = board,
    )
}
