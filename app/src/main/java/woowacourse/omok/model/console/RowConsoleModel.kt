package woowacourse.omok.model.console

class RowConsoleModel(
    private val rowNumber: Int,
    private val row: List<String>,
) {
    constructor(rowNumber: Int, size: Int) : this(rowNumber, createEmptyRow(rowNumber, size))

    constructor(rowNumber: Int, size: Int, row: Set<BlockConsoleModel>) : this(
        rowNumber,
        createRow(rowNumber, size, row),
    )

    override fun toString(): String {
        return row.joinToString(prefix = formatOrder(rowNumber), separator = "")
    }

    private fun formatOrder(order: Int): String = if (order < FORMAT_ORDER_CONDITION) " $order " else "$order "

    companion object {
        private const val FORMAT_ORDER_CONDITION = 10
        private const val WEIGHT = 2
        private val topStartGrid = listOf("┌")
        private val topMiddleGrid = listOf("──", "┬")
        private val topEndGrid = listOf("──", "┐")
        private val bottomStartGrid = listOf("└")
        private val bottomMiddleGrid = listOf("──", "┴")
        private val bottomEndGrid = listOf("──", "┘")
        private val startGrid = listOf("├")
        private val endGrid = listOf("──", "┤")
        private val middleGrid = listOf("──", "┼")

        private fun weight(position: Int): Int = position * WEIGHT

        private fun createEmptyRow(
            rowNumber: Int,
            size: Int,
        ): List<String> {
            if (rowNumber == size) {
                return topStartGrid + List(size - 2) { topMiddleGrid }.flatten() + topEndGrid
            }
            if (rowNumber == 1) {
                return bottomStartGrid + List(size - 2) { bottomMiddleGrid }.flatten() + bottomEndGrid
            }
            return startGrid + List(size - 2) { middleGrid }.flatten() + endGrid
        }

        private fun createRow(
            rowNumber: Int,
            size: Int,
            row: Set<BlockConsoleModel>,
        ): List<String> {
            val newRow = createEmptyRow(rowNumber, size).toMutableList()
            row.forEach { (x, y, state) ->
                if (state !== BlockStateConsoleModel.EMPTY) newRow[weight(y)] = state.symbol
            }
            return newRow
        }
    }
}
