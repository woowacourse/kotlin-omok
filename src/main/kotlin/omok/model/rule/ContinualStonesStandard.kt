package omok.model.rule

data class ContinualStonesStandard(val count: Int) : Comparable<ContinualStonesStandard> {
    init {
        require(count in CONTINUAL_STONES_COUNT_RANGE) {
            "정상시적인 게임 진행을 위해 ${MIN_CONTINUAL_STONES_COUNT}목 부터 ${MAX_CONTINUAL_STONES_COUNT}목으로 지정 가능합니다. "
        }
    }

    override fun compareTo(other: ContinualStonesStandard): Int = this.count - other.count

    companion object {
        const val MIN_CONTINUAL_STONES_COUNT = 4
        private const val MAX_CONTINUAL_STONES_COUNT = 6
        private val CONTINUAL_STONES_COUNT_RANGE = MIN_CONTINUAL_STONES_COUNT..MAX_CONTINUAL_STONES_COUNT
    }
}
