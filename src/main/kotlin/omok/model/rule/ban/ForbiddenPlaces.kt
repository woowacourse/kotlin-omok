package omok.model.rule.ban

import omok.model.rule.ContinualStonesStandard

class ForbiddenPlaces(list: List<ForbiddenPlace>) {
    val list = list.toList()

    constructor(vararg forbiddenPlace: ForbiddenPlace) : this(listOf(*forbiddenPlace))

    fun initOverlineStandard(continualStonesStandard: ContinualStonesStandard): ForbiddenPlaces {
        (list.find { it is OverlineForbiddenPlace2 } as? OverlineForbiddenPlace2)?.continualStonesStandard =
            continualStonesStandard
        return ForbiddenPlaces(list)
    }
}
