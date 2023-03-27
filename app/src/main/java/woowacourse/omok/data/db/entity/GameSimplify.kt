package woowacourse.omok.data.db.entity

import domain.domain.BoardState
import domain.domain.CoordinateState

data class GameSimplify(
    val gameId: Int?,
    val turn: CoordinateState,
    val board: BoardState
)
