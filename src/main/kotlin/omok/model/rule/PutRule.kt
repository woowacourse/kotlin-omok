package omok.model.rule

import lib.ark.ark.ArkFourFourRule
import lib.ark.ark.ArkOverLineRule
import lib.ark.ark.ArkThreeThreeRule
import omok.mapper.toArkOmokBoard
import omok.mapper.toArkOmokPoint
import omok.model.Board
import omok.model.OmokStone

interface PutRule {
    fun canPut(
        stone: OmokStone,
        board: Board,
    ): Boolean
}

object BlackPutRule : PutRule {
    override fun canPut(
        stone: OmokStone,
        board: Board,
    ): Boolean {
        if (WhiteCanPutRule.canPut(stone, board).not()) return false
        val arkBoard = board.toArkOmokBoard()
        val arkPoint = stone.position.toArkOmokPoint()
        val isNotFourFour = ArkFourFourRule.validate(arkBoard, arkPoint).not()
        val isNotThreeThree = ArkThreeThreeRule.validate(arkBoard, arkPoint).not()
        val isNotJangMok = ArkOverLineRule.validate(arkBoard, arkPoint)
        return isNotFourFour && isNotThreeThree && isNotJangMok
    }
}

object WhiteCanPutRule : PutRule {
    override fun canPut(
        stone: OmokStone,
        board: Board,
    ): Boolean {
        val isEmptyPosition = board.isEmptyPosition(stone)
        val isInRange = board.isInRange(stone)
        return isEmptyPosition && isInRange
    }
}
