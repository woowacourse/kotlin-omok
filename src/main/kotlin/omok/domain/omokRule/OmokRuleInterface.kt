package omok.domain.omokRule

interface OmokRuleInterface {
    fun validate(board: List<List<Int>>, position: Pair<Int, Int>): Boolean
}
