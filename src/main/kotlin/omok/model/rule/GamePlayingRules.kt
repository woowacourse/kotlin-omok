package omok.model.rule

import omok.model.rule.ban.ForbiddenPlaces
import omok.model.rule.ban.OverlineForbiddenPlace2
import omok.model.rule.winning.ContinualStonesWinningCondition

class GamePlayingRules private constructor(
    continualStonesWinningCondition: ContinualStonesWinningCondition,
    private val forbiddenPlaces: ForbiddenPlaces,
) {
    init {
        if (!continualStonesWinningCondition.canHaveDoubleRule()) {
            require(!forbiddenPlaces.haveDoubleRule())
        }
    }

    fun overlineRule(): OverlineForbiddenPlace2? = forbiddenPlaces.list.find { it is OverlineForbiddenPlace2 } as? OverlineForbiddenPlace2

    companion object {
        fun from(
            continualStonesWinningCondition: ContinualStonesWinningCondition,
            forbiddenPlaces: ForbiddenPlaces,
        ): GamePlayingRules {
            return GamePlayingRules(
                continualStonesWinningCondition,
                forbiddenPlaces.initOverlineStandard(continualStonesWinningCondition.continualStonesStandard),
            )
        }
    }
}
