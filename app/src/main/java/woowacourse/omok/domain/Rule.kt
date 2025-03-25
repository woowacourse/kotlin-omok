package woowacourse.omok.domain

interface Rule {
    fun isViolate(
        board: OmokBoard,
        stone: Stone,
    ): Boolean
}
