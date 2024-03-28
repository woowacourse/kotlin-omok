package woowacourse.omok.model.board

data class CoordsNumber(val number: Int) {
    init {
        require(number in COORDS_RANGE) { "잘못된 좌표 값입니다." }
    }

    companion object {
        private val COORDS_RANGE = 0..14
    }
}
