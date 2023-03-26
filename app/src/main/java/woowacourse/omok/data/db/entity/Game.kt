package woowacourse.omok.data.db.entity

import domain.domain.BoardState
import domain.domain.CoordinateState

data class Game(
    val gameId: Int,
    val turn: CoordinateState,
    val lastPosition: CoordinateState,
    val Board: BoardState,
    val userId: Int
)
