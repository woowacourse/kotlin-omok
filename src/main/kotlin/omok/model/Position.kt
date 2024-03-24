package omok.model

data class Position(
    val horizontalCoordinate: HorizontalCoordinate,
    val verticalCoordinate: VerticalCoordinate,
) {
    companion object {
        private const val MIN_ROW = 1
        private const val MAX_ROW = 15
        private const val MIN_COL = 'A'
        private const val MAX_COL = 'O'
        private const val EXCEPTION_ROW_RANGE = "$MIN_ROW 부터 ${MAX_ROW}사이의 정수를 입력해야 합니다"
        private const val EXCEPTION_COL_RANGE = "$MIN_COL 부터 ${MAX_COL}사이의 알파벳을 입력해야 합니다"

        fun of(
            horizontalCoordinate: Int,
            verticalCoordinate: Char,
        ): Position {
            require(horizontalCoordinate in MIN_ROW..MAX_ROW) { EXCEPTION_ROW_RANGE }
            require(verticalCoordinate in MIN_COL..MAX_COL) { EXCEPTION_COL_RANGE }

            return Position(
                horizontalCoordinate =
                    HorizontalCoordinate.valueOf(horizontalCoordinate)
                        ?: throw IllegalArgumentException(EXCEPTION_ROW_RANGE),
                verticalCoordinate =
                    VerticalCoordinate.valueOf(verticalCoordinate)
                        ?: throw IllegalArgumentException(EXCEPTION_COL_RANGE),
            )
        }
    }
}
