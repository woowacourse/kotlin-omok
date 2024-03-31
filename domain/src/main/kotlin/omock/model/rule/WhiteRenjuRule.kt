package omock.model.rule

import omock.model.InvalidDuplicatedPlaced
import omock.model.InvalidGameOver
import omock.model.InvalidOutOfBound
import omock.model.PlaceResult
import omock.model.Position
import omock.model.Stone
import omock.model.Success
import omock.model.board.Block
import omock.model.board.BlockState
import omock.model.board.OmokBoard

data object WhiteRenjuRule : OmokGameRule {
    override fun placeStone(
        position: Position,
        stone: Stone,
        board: OmokBoard,
    ): PlaceResult {
        when {
            board.hasOmok() -> return InvalidGameOver
            isNotInBounds(position, board) -> return InvalidOutOfBound
            isDuplicate(position, board) -> return InvalidDuplicatedPlaced
            else -> {
                val newBlock = Block(position, BlockState.from(stone))
                val newBoard = board.updateBoard(newBlock)
                return Success(newBoard)
            }
        }
    }

    private fun isNotInBounds(
        position: Position,
        board: OmokBoard,
    ): Boolean {
        return board.size.isInBounds(position).not()
    }

    private fun isDuplicate(
        position: Position,
        board: OmokBoard,
    ): Boolean {
        val blocks: Map<Position, BlockState> = board.blockRecords.toBlockStateMap()
        return blocks
            .getOrDefault(position, BlockState.EMPTY) != BlockState.EMPTY
    }
}
