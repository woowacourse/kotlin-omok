package woowacourse.omok.model.console

data class PositionConsoleModel(
    val x: Int,
    val y: Int,
) {
    fun format(): String = "${y.formatToPositionX()}${x.formatToPositionY()}"

    private fun Int.formatToPositionX(): String = (this + 'A'.code).toChar().toString()

    private fun Int.formatToPositionY(): String = (15 - this).toString()
}
