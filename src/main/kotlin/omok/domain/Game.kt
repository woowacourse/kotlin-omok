package omok.domain

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.rule.OmokRule
import omok.domain.model.state.Finish
import omok.domain.model.state.OmokState
import omok.domain.model.state.Turn
import omok.domain.model.stone.StoneType

class Game(private val board: Board, private val rule: OmokRule) {
    private var currentPosition: Position? = null

    fun play(
        onBoardState: (Board, StoneType, Position?) -> Unit,
        onPlace: (Board) -> Position,
        stoneType: StoneType,
    ): StoneType {
        val state = Turn(board, rule, stoneType)
        return progress(onBoardState, onPlace, stoneType, state).stoneType
    }

    private tailrec fun progress(
        onBoardState: (Board, StoneType, Position?) -> Unit,
        onPlace: (Board) -> Position,
        stoneType: StoneType,
        state: OmokState,
    ): OmokState {
        onBoardState(state.board, stoneType, currentPosition)
        val position = onPlace(board)
        val next = state.placeStone(position)
        currentPosition = position
        if (next is Finish) return next
        return progress(onBoardState, onPlace, stoneType, next)
    }
}
