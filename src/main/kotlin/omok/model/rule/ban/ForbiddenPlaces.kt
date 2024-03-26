package omok.model.rule.ban

import omok.model.rule.ContinualStonesStandard

class ForbiddenPlaces(list: List<ForbiddenPlace>) {
    private val _list = list.toMutableList()
    val list = _list.toList()

    constructor(vararg forbiddenPlace: ForbiddenPlace) : this(listOf(*forbiddenPlace))

    fun hasDoubleRule(): Boolean = list.any { it is DoubleFourForbiddenPlace || it is DoubleOpenThreeForbiddenPlace }

    fun hasOverlineRule(): Boolean = list.any { it is OverlineForbiddenPlace }

    fun initOverlineStandard(continualStonesStandard: ContinualStonesStandard): ForbiddenPlaces {
        (_list.find { it is OverlineForbiddenPlace } as? OverlineForbiddenPlace)?.continualStonesStandard =
            continualStonesStandard
        return ForbiddenPlaces(list)
    }
}
