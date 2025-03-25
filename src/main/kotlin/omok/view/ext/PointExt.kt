package omok.view.ext

import omok.domain.board.OmokBoard
import omok.domain.stone.Black
import omok.domain.stone.Empty
import omok.domain.stone.Protected
import omok.domain.stone.Stone
import omok.domain.stone.White

fun Stone.position(): String {
    val dx = OmokBoard.COLUMN_POOL[this.x - 1].toString()
    val dy = this.y
    return dx + dy
}

fun Stone.toLabel(): String {
    return when (this) {
        is Black -> "흑"
        is White -> "백"
        else -> throw IllegalStateException()
    }
}

fun Stone.format(): Char {
    return when (this) {
        is Black -> '●'
        is White -> '○'
        is Protected -> 'x'
        is Empty -> throw java.lang.IllegalStateException(ERR_CANNOT_REACHABLE)
    }
}

private const val ERR_CANNOT_REACHABLE = "빈 상태는 포맷팅 할 수 없습니다"
