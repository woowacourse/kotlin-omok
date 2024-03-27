package omok.model

data class BoardCoordinate(private val number: Int) {
    fun getNumber(): Int = number

    companion object {
        private val COORDS_RANGE = 0..14
        private val letterToNumberMapping = ('A'..'O').withIndex().associate { it.value to BoardCoordinate(it.index) }

        fun from(value: Any): BoardCoordinate? {
            if (value in COORDS_RANGE) return BoardCoordinate(value as Int)
            return letterToNumberMapping[value]
        }
    }
}
