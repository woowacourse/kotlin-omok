package omok.view.model

data class PositionUiModel(
    val x: Int,
    val y: Int,
) {
    fun format(): String = "${x.formatToPositionX()}${y.formatToPositionY()}"

    private fun Int.formatToPositionX(): String = (this + 'A'.code).toChar().toString()

    private fun Int.formatToPositionY(): String = (this + '0'.code + 1).toChar().toString()
}
