package woowacourse.omok.data.db.entity

import domain.domain.BoardState
import domain.domain.CoordinateState
import domain.domain.Position

data class Game(
    val gameId: Int?,
    val turn: CoordinateState,
    val lastPosition: Position,
    val Board: BoardState,
    val userId: Int
)
