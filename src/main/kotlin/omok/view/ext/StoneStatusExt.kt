package omok.view.ext

import omok.domain.point.Black
import omok.domain.point.Empty
import omok.domain.point.Point2
import omok.domain.point.Protected
import omok.domain.point.White

fun Point2.toLabel(): String {
    return when (this) {
        is Black -> "흑"
        is White -> "백"
        else -> throw IllegalStateException()
    }
}

fun Point2.format(): Char {
    return when (this) {
        is Black -> '●'
        is White -> '○'
        is Protected -> 'x'
        is Empty -> throw java.lang.IllegalStateException(ERR_CANNOT_REACHABLE)
    }
}

private const val ERR_CANNOT_REACHABLE = "빈 상태는 포맷팅 할 수 없습니다"
