package domain.rule

import domain.Stone

class BlackRuleAdapter : RuleAdapter {

    override fun checkPutStone(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone) {
        require(
            !ThreeThreeBlackRule().checkRule(blackStones, whiteStones, nextStone)
        ) { "흑돌은 33이면 안됩니다." }
        require(
            !FourFourBlackRule().checkRule(blackStones, whiteStones, nextStone)
        ) { "흑돌은 44면 안됩니다." }
        require(
            !LongMokBlackRule().checkRule(blackStones, whiteStones, nextStone)
        ) { "흑돌은 장목이면 안됩니다." }
    }
}