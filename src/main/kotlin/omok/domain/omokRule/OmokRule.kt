package omok.domain.omokRule

interface OmokRule {
    fun validate(board: List<List<Int>>, position: Pair<Int, Int>): Boolean
}
