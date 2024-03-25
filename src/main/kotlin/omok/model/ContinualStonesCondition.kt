package omok.model

enum class ContinualStonesCondition {
    EXACT,
    CAN_OVERLINE,
    ;

    fun map(
        actualContinualCount: Int,
        standardContinualCount: Int,
    ): Boolean {
        return when (this) {
            EXACT -> (actualContinualCount == standardContinualCount)
            CAN_OVERLINE -> (actualContinualCount >= standardContinualCount)
        }
    }
}
