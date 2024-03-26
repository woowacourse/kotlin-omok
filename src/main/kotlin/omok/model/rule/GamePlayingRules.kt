package omok.model.rule

import omok.model.rule.ban.ForbiddenPlaces
import omok.model.rule.winning.ContinualStonesWinningCondition

class GamePlayingRules private constructor() {
    companion object {
        fun from(
            continualStonesWinningCondition: ContinualStonesWinningCondition,
            forbiddenPlaces: ForbiddenPlaces,
        ): ForbiddenPlaces {
            return forbiddenPlaces.initOverlineStandard(continualStonesWinningCondition.continualStonesStandard)
        }
    }
}
