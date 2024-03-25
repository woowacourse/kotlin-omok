package core.omock.rule

abstract class OMockRule {
    abstract fun threeToThreeCount(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean

    abstract fun fourToFourCount(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean

    abstract fun isClearFourToFour(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean

    abstract fun isReverseTwoAndThree(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean

    abstract fun isGameWon(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Boolean

    companion object Default : OMockRule() {
        private val oMockRule = OMockRuleImpl()

        override fun threeToThreeCount(
            stoneStates: List<List<Int>>,
            row: Int,
            column: Int,
        ): Boolean {
            return oMockRule.threeToThreeCount(stoneStates, row, column)
        }

        override fun fourToFourCount(
            stoneStates: List<List<Int>>,
            row: Int,
            column: Int,
        ): Boolean {
            return oMockRule.fourToFourCount(stoneStates, row, column)
        }

        override fun isClearFourToFour(
            stoneStates: List<List<Int>>,
            row: Int,
            column: Int,
        ): Boolean {
            return oMockRule.isClearFourToFour(stoneStates, row, column)
        }

        override fun isReverseTwoAndThree(
            stoneStates: List<List<Int>>,
            row: Int,
            column: Int,
        ): Boolean {
            return oMockRule.isReverseTwoAndThree(stoneStates, row, column)
        }

        override fun isGameWon(
            stoneStates: List<List<Int>>,
            row: Int,
            column: Int,
        ): Boolean {
            return oMockRule.isGameWon(stoneStates, row, column)
        }
    }
}
