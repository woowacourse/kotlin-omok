package woowacourse.omok.model.omokGame

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.stone.StoneState

interface OmokGame {
    fun placeStone(
        position: Position,
        stoneState: StoneState,
    )

    fun blackResult(
        position: Position,
        stoneState: StoneState,
    ): TurnResult

    fun whiteResult(
        position: Position,
        stoneState: StoneState,
    ): TurnResult
}
