package woowacourse.omok.model.console

data class BoardConsoleModel(
    private val size: Int,
    private val box: List<RowConsoleModel>,
) {
    constructor(size: Int) : this(size, createEmptyBox(size))
    constructor(box: List<Set<BlockConsoleModel>>) : this(
        box.size,
        box.mapIndexed { i, set ->
            RowConsoleModel(
                rowNumber = box.size - i,
                size = box.size,
                row = set,
            )
        },
    )

    private fun alphabets(size: Int): String = WHITE_SPACE + ALPHABET.take(size).joinToString("  ")

    override fun toString(): String {
        return buildString {
            box.forEach(::appendLine)
            append(alphabets(size))
        }
    }

    companion object {
        private val ALPHABET = ('A'..'Z').toList()
        private const val WHITE_SPACE = "   "

        fun createEmptyBox(size: Int): List<RowConsoleModel> {
            return List(size) { RowConsoleModel(it, size) }
        }
    }
}
