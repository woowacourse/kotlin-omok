package domain.turn

import domain.board.PlacedBoard
import domain.judgement.ForbiddenCondition
import domain.judgement.WinningCondition
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class BlackTurn(
    board: PlacedBoard,
    previousBoard: PlacedBoard?,
    latestStone: Stone?,
    private val winningCondition: WinningCondition,
    private val forbiddenCondition: ForbiddenCondition
) : RunningTurn(board) {

    constructor(board: PlacedBoard, winningCondition: WinningCondition, forbiddenCondition: ForbiddenCondition) : this(
        board,
        null,
        null,
        winningCondition,
        forbiddenCondition
    )

    override val curColor: Color = Color.BLACK

    init {
        // check(
        //     (
        //         previousBoard?.getBoards()?.filter { it.value != null }?.isEmpty() == true &&
        //             latestStone == null
        //         ) || latestStone?.color == Color.WHITE
        // ) {
        //     ERROR_PREVIOUS_BOARD_STATE
        // }
    }

    override fun nextBoard(newBoard: PlacedBoard, position: Position): Turn {
        val latestStone = Stone(position, curColor)
        return when {
            forbiddenCondition.isForbidden(board.getBoards(), latestStone) ->
                WhiteWin(newBoard, board, latestStone, winningCondition, forbiddenCondition)

            winningCondition.isWin(board.getBoards(), latestStone) ->
                BlackWin(newBoard, board, latestStone, winningCondition, forbiddenCondition)

            else -> WhiteTurn(newBoard, latestStone, winningCondition, forbiddenCondition)
        }
    }

    companion object {
        private const val ERROR_PREVIOUS_BOARD_STATE = "[ERROR] 직전에 돌이 안 놓여져있었거나 흰 돌이 놨어야 합니다."
    }
}
