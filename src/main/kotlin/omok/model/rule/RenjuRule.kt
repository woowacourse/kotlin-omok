package omok.model.rule

import lib.ark.ark.ArkFourFourRule
import lib.ark.ark.ArkOverLineRule
import lib.ark.ark.ArkThreeThreeRule
import omok.mapper.toArkOmokBoard
import omok.mapper.toArkOmokPoint
import omok.model.OmokStone
import omok.model.board.Board

object RenjuRule : OmokGameRule {
    override fun canPlaceStone(
        stone: OmokStone,
        board: Board,
    ): Boolean {
        if (board.canPlace(stone).not()) return false
        val arkBoard = board.toArkOmokBoard()
        val arkPoint = stone.position.toArkOmokPoint()
        val isNotFourFour = ArkFourFourRule.validate(arkBoard, arkPoint).not()
        val isNotThreeThree = ArkThreeThreeRule.validate(arkBoard, arkPoint).not()
        val isNotJangMok = ArkOverLineRule.validate(arkBoard, arkPoint)
        return isNotFourFour && isNotThreeThree && isNotJangMok
    }
}
