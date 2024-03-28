package woowacourse.omok.model.rule

import woowacourse.lib.ark.ark.ArkFourFourRule
import woowacourse.lib.ark.ark.ArkOverLineRule
import woowacourse.lib.ark.ark.ArkThreeThreeRule
import woowacourse.omok.mapper.toArkOmokBoard
import woowacourse.omok.mapper.toArkOmokPoint
import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone

object RenjuRule : StonePlaceRule() {
    override fun canPlace(
        stone: OmokStone,
        board: Board,
    ): Boolean {
        if (super.canPlace(stone, board).not()) return false
        val arkBoard = board.toArkOmokBoard()
        val arkPoint = stone.position.toArkOmokPoint()
        val isNotFourFour = ArkFourFourRule.validate(arkBoard, arkPoint).not()
        val isNotThreeThree = ArkThreeThreeRule.validate(arkBoard, arkPoint).not()
        val isNotJangMok = ArkOverLineRule.validate(arkBoard, arkPoint)
        return isNotFourFour && isNotThreeThree && isNotJangMok
    }
}
