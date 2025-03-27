package omok.model.omokGame

import omok.model.board.Position
import omok.model.stone.StoneState

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
