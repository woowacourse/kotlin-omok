package woowacourse.omok.domain.omok.strategy

import woowacourse.omok.domain.ark.ArkFourFourRule
import woowacourse.omok.domain.ark.ArkOverLineRule
import woowacourse.omok.domain.ark.ArkThreeThreeRule

fun interface ForbiddenPlaceJudge {
    fun isNotForbiddenPlace(
        board: List<List<Int>>,
        point: Pair<Int, Int>,
    ): Boolean
}

class ArkForbiddenPlaceJudge : ForbiddenPlaceJudge {
    override fun isNotForbiddenPlace(
        board: List<List<Int>>,
        point: Pair<Int, Int>,
    ): Boolean {
        val isNotFourFour = ArkFourFourRule.validate(board, point).not()
        val isNotThreeThree = ArkThreeThreeRule.validate(board, point).not()
        val isNotOverLine = ArkOverLineRule.validate(board, point).not()
        return isNotFourFour && isNotThreeThree && isNotOverLine
    }
}
