package omok.view.ext

import omok.domain.board.StoneStatus

fun StoneStatus.toLabel(): String {
    return when (this) {
        StoneStatus.BLACK -> "흑"
        StoneStatus.WHITE -> "백"
        else -> throw IllegalStateException()
    }
}

fun StoneStatus.format(): Char {
    return when (this) {
        StoneStatus.BLACK -> '●'
        StoneStatus.WHITE -> '○'
        StoneStatus.PROTECTED -> 'x'
        StoneStatus.EMPTY -> throw java.lang.IllegalStateException(ERR_CANNOT_REACHABLE)
    }
}

private const val ERR_CANNOT_REACHABLE = "빈 상태는 포맷팅 할 수 없습니다"
