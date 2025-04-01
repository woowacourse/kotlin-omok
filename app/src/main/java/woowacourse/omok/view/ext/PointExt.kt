package omok.view.ext

import omok.domain.place.Black
import omok.domain.place.Empty
import omok.domain.place.Place
import omok.domain.place.Protected
import omok.domain.place.White

private const val DESERIALIZE_ERR = "역직렬화 중 오류가 발생하였습니다"

fun Place.toLabel(): String {
    return when (this) {
        is Black -> "흑"
        is White -> "백"
        else -> throw IllegalStateException()
    }
}

fun Place.serialize(): String {
    val placeChar =
        when (this) {
            is Black -> "B"
            is White -> "W"
            is Protected -> "P"
            is Empty -> "E"
        }
    return "$placeChar|$x|$y"
}

fun String.toPlace(): Place {
    val info = split("|")
    val x = info[1].toInt()
    val y = info[2].toInt()
    return when (info[0]) {
        "B" -> Black(x, y)
        "W" -> White(x, y)
        "P" -> Protected(x, y)
        "E" -> Empty(x, y)
        else -> throw IllegalStateException(DESERIALIZE_ERR)
    }
}
