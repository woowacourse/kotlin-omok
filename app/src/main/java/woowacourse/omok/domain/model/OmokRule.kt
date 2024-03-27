package woowacourse.omok.domain.model

interface OmokRule {
    fun validPosition(
        board: List<List<Int>>,
        x: Int,
        y: Int,
    ): Boolean
}