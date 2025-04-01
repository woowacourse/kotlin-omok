package omok.model.entity.position

interface GridElement {
    val value: Int
}

@JvmInline
value class DefaultGridElement(
    override val value: Int,
) : GridElement {
    init {
        require(value >= MIN_VALUE) { ERROR_MESSAGE_GRID_MUST_POSITIVE_NUMBER.format(value) }
    }

    companion object {
        private const val MIN_VALUE = 0
        private const val ERROR_MESSAGE_GRID_MUST_POSITIVE_NUMBER = "그리드 요소는 $MIN_VALUE 이상의 값만 가능합니다. value : %s"
    }
}
