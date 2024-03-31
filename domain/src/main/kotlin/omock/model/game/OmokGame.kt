package omock.model.game

import omock.model.PlaceResult
import omock.model.Position
import omock.model.Stone
import omock.model.board.BlockState
import omock.model.board.OmokBoard
import omock.model.onSuccess

class OmokGame(
    private var board: OmokBoard,
) {
    fun placeStone(position: Position): PlaceResult {
        return board.placeStone(position, determinePlaceStone())
            .onSuccess { board = it }
    }

    fun isEnd(): Boolean = board.hasOmok()

    fun currentBoard(): OmokBoard = board

    fun lastGameResult(): GameResult {
        val lastBlock = board.lastBlockOrNull() ?: error("게임이 시작되지 않았습니다.")
        return GameResult(board, lastBlock)
    }

    private fun determinePlaceStone(): Stone {
        val lastBlockState = board.lastBlockOrNull()?.state ?: BlockState.EMPTY
        return when (lastBlockState) {
            BlockState.BLACK_STONE -> Stone.WHITE
            BlockState.WHITE_STONE -> Stone.BLACK
            BlockState.EMPTY -> Stone.BLACK
        }
    }
}
