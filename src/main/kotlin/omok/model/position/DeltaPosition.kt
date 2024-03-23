package omok.model.position

data class DeltaPosition(val deltaRow: Int, val deltaCol: Int) {
    operator fun unaryMinus(): DeltaPosition {
        return DeltaPosition(-deltaRow, -deltaCol)
    }
}