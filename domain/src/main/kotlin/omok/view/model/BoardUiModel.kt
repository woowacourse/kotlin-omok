package omok.view.model

data class BoardUiModel(
    private val size: Int,
    private val box: List<RowUiModel>,
) {
    constructor(size: Int) : this(size, createEmptyBox(size))

    constructor(box: List<Set<StoneUiModel>>) : this(
        box.size,
        box.mapIndexed { i, set -> RowUiModel(box.size - i, box.size, set) },
    )

    operator fun plus(stone: StoneUiModel): BoardUiModel {
        val newBox =
            box.mapIndexed { index, rowUiModel ->
                if (index == stone.x) rowUiModel + stone else rowUiModel
            }
        return BoardUiModel(size, newBox)
    }

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

        fun createEmptyBox(size: Int): List<RowUiModel> {
            return List(size) { RowUiModel(it, size) }
        }
    }
}
