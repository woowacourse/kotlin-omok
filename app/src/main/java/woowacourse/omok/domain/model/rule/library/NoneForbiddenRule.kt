package woowacourse.omok.domain.model.rule.library

data object NoneForbiddenRule : OmokRule() {
    override fun abide(board: List<List<Int>>, position: Pair<Int, Int>): Boolean = true
}
