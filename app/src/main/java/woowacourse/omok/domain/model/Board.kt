package woowacourse.omok.domain.model

class Board(
    val column: Int,
    val row: Int,
) {
    constructor(totalSize: Int) : this(totalSize, totalSize)
}
