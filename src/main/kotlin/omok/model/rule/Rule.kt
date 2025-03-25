package omok.model.rule

interface Rule {
    fun validate(
        board: List<List<Int>>,
        position: RulePosition,
    ): Boolean
}
