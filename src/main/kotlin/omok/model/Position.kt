package omok.model

typealias Col = Int
typealias Row = Int

data class Position(
    val x: Col,
    val y: Row,
) {
    init {
        require(x in MIN_POSITION..MAX_POSITION && y in MIN_POSITION..MAX_POSITION) { ERROR_MESSAGE_INVALID_POSITION }
    }

    companion object {
        const val MIN_POSITION = 1
        const val MAX_POSITION = 15
        private const val ERROR_MESSAGE_INVALID_POSITION = "바둑돌은 ${MIN_POSITION}과 ${MAX_POSITION} 사이에만 둘 수 있습니다."
    }
}
