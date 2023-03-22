package view

enum class AlphabetCoordinate() {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O;

    companion object {
        fun convertX(alphabet: String): Int? {
            return values().find { it.name == alphabet }?.ordinal
        }

        fun convertAlphabet(num: Int): AlphabetCoordinate {
            return values().find { it.ordinal == num } ?: throw IllegalArgumentException()
        }
    }
}
