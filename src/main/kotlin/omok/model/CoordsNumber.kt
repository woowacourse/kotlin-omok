package omok.model

data class CoordsNumber(val number: Int) {
    companion object {
        private val COORDS_RANGE = 0..14
        private val letterToNumberMapping = ('A'..'O').withIndex().associate { it.value to CoordsNumber(it.index) }

        fun fromNumber(value: Int): CoordsNumber? {
            return if (value in COORDS_RANGE) {
                CoordsNumber(value)
            } else {
                null
            }
        }

        fun fromLetter(letter: Char): CoordsNumber? {
            val upperCaseLetter = letter.toUpperCase()
            return letterToNumberMapping[upperCaseLetter]
        }
    }
}
