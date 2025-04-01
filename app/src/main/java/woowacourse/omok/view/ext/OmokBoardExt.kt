package woowacourse.omok.view.ext

import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Empty
import omok.domain.place.OmokStones
import omok.domain.place.Place
import omok.domain.place.Protected
import omok.domain.place.White
import omok.view.ext.toPlace

private const val DESERIALIZE_ERR = "역직렬화 중 오류가 발생하였습니다"

fun OmokBoard.getPointAt(
    x: Int,
    y: Int,
): Place {
    return omokStones.getPointAt(y, x)
}

fun OmokBoard.serialize(): String {
    val target = omokStones.places + latestPlace
    return target.joinToString("/") {
        val placeChar =
            when (it) {
                is Black -> "B"
                is White -> "W"
                is Protected -> "P"
                is Empty -> "E"
            }
        "$placeChar|${it.x}|${it.y}"
    }
}

fun OmokBoard.deserialize(value: String): OmokBoard? {
    return runCatching {
        val places =
            value.split("/")
                .map { it.toPlace() }

        OmokBoard(
            omokStones = OmokStones(places),
            omokRules = omokRules,
            latestPlace = places.lastOrNull() ?: Empty.dummy(),
        )
    }.getOrNull()
}
