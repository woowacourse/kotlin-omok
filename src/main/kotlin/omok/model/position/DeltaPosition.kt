package omok.model.position

data class DeltaPosition(val deltaRow: Int, val deltaColumn: Int) {
    operator fun unaryMinus(): DeltaPosition {
        return DeltaPosition(-deltaRow, -deltaColumn)
    }
}
