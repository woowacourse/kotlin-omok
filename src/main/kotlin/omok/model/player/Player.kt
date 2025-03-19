package omok.model.player

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.player.state.PlayerState
import omok.model.stone.StoneColor
import omok.model.stone.StoneState

abstract class Player(
    private val playerState: PlayerState,
    private val stoneState: StoneState,
) {
    abstract val stoneColor: StoneColor

    fun put(
        position: Position,
        omokBoard: OmokBoard,
    ) {
        omokBoard.placeStone(position, stoneState)
    }
}
