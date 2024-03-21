package omok.model.board

data class CoordsNumber(val number: Int) {
    companion object {
        fun of(value: Int): CoordsNumber? {
            if (value !in COORDS_RANGE) return null
            return CoordsNumber(value)
        }

        private val COORDS_RANGE = 0..14
    }
}
