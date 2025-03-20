package omok.domain

interface Turn {
    val color: StoneType
    fun stone(position: String): Stone
        val row =
            RowType.entries.find { it.name == rowString }?.value ?: throw IllegalArgumentException(ERROR_NOT_FIND)
}