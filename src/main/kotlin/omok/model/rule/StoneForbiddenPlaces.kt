package omok.model.rule

import omok.model.ForbiddenPlace
import omok.model.Stone

class StoneForbiddenPlaces(val blackForbiddenPlaces: List<ForbiddenPlace>, val whiteForbiddenPlaces: List<ForbiddenPlace>) {
    fun forbiddenPlaces(stone: Stone): List<ForbiddenPlace> {
        return if (stone == Stone.BLACK) blackForbiddenPlaces else whiteForbiddenPlaces
    }
}
