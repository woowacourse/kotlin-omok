package omok.domain

enum class StoneState(val korean: String) {
    BLACK("흑"), WHITE("백"), EMPTY("");

    fun next() = when (this) {
        BLACK -> WHITE
        WHITE -> BLACK
        else -> throw IllegalStateException()
    }

    fun checkForbidden(omokBoard: OmokBoard, point: OmokPoint) = when (this) {
        BLACK -> {
            OmokRule(omokBoard).countOpenThrees(point) <= 1 && OmokRule(omokBoard).countOpenFours(point) <= 1
        }
        WHITE -> {
            true
        }
        else -> throw IllegalStateException()
    }
}
