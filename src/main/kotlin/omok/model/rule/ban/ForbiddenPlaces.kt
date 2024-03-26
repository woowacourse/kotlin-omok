package omok.model.rule.ban

import omok.model.rule.ContinualStonesStandard

class ForbiddenPlaces(list: List<ForbiddenPlace>) {
    private val _list = list.toMutableList()
    val list = _list.toList()

    constructor(vararg forbiddenPlace: ForbiddenPlace) : this(listOf(*forbiddenPlace))

    fun haveDoubleRule(): Boolean = _list.any { it is DoubleFourForbiddenPlace || it is DoubleOpenThreeForbiddenPlace }

    fun initOverlineStandard(continualStonesStandard: ContinualStonesStandard): ForbiddenPlaces {
        (_list.find { it is OverlineForbiddenPlace2 } as? OverlineForbiddenPlace2)?.continualStonesStandard =
            continualStonesStandard
        return ForbiddenPlaces(list)
    }
}
