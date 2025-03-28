package omok.view.ext

import omok.domain.place.Black
import omok.domain.place.Place
import omok.domain.place.White

fun Place.toLabel(): String {
    return when (this) {
        is Black -> "흑"
        is White -> "백"
        else -> throw IllegalStateException()
    }
}
