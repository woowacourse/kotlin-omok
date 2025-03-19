package omok.domain

class BlackTurn: Turn {
    override val color: StoneType = StoneType.BLACK
    override fun stone(position: String): Stone {
        val regex = """([A-Z]+)(\d+)""".toRegex()
        val matchResult = regex.matchEntire(position) ?: throw IllegalArgumentException(ERROR_NOT_FIND)
        val (rowString, column) = matchResult.destructured
        val row = Position.entries.find { it.name == rowString }?.value ?: throw IllegalArgumentException(ERROR_NOT_FIND)
        val stone = Stone(row, (14 - column.toInt()), color)
        return stone
    }

    companion object {
        private const val ERROR_NOT_FIND = "유효하지 않은 입력입니다. 다시 입력해주세요."
    }
}