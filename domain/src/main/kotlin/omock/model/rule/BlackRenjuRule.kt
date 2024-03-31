package omock.model.rule

import omock.lib.ark.ArkFourFourRule
import omock.lib.ark.ArkOverLineRule
import omock.lib.ark.ArkThreeThreeRule
import omock.model.InvalidFourFourRule
import omock.model.InvalidOverLineRule
import omock.model.InvalidThreeThreeRule
import omock.model.PlaceResult
import omock.model.Position
import omock.model.Stone
import omock.model.Success
import omock.model.board.Block
import omock.model.board.BlockState
import omock.model.board.OmokBoard
import omock.model.isFailure

data object BlackRenjuRule : OmokGameRule {
    override fun placeStone(
        position: Position,
        stone: Stone,
        board: OmokBoard,
    ): PlaceResult {
        val arkBoard = board.toArkOmokBoard()
        val arkPoint = position.toArkOmokPoint()
        val whiteRenjuRuleResult = WhiteRenjuRule.placeStone(position, stone, board)
        if (whiteRenjuRuleResult.isFailure()) return whiteRenjuRuleResult

        return when {
            ArkFourFourRule.validate(arkBoard, arkPoint) -> return InvalidFourFourRule
            ArkThreeThreeRule.validate(arkBoard, arkPoint) -> return InvalidThreeThreeRule
            ArkOverLineRule.validate(arkBoard, arkPoint).not() -> return InvalidOverLineRule
            else -> {
                val newBlock = Block(position, BlockState.from(stone))
                val newBoard = board.updateBoard(newBlock)
                return Success(newBoard)
            }
        }
    }
}
