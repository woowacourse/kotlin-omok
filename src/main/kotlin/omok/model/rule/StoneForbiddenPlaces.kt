package omok.model.rule

import omok.model.ForbiddenPlace
import omok.model.Stone

class StoneForbiddenPlaces(val blackForbiddenPlaces: List<ForbiddenPlace>, val whiteForbiddenPlaces: List<ForbiddenPlace>) {
    fun forbiddenPlaces(stone: Stone): List<ForbiddenPlace> {
        if (stone == Stone.BLACK) return blackForbiddenPlaces
        return whiteForbiddenPlaces
    }
}
