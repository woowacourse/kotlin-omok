package woowacourse.omok.ui.mapper

import woowacourse.omok.domain.model.game.OmokGameEntity
import woowacourse.omok.domain.model.omokboard.OmokGame

fun OmokGameEntity.toUI() =
    OmokGame(
        board = board,
        savedTurn = lastTurn,
    )
