package domain.library.position

enum class Column(val x: Int) {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10),
    K(11),
    L(12),
    M(13),
    N(14),
    O(15);

    fun right(): Column? = values().find { it.x == x + 1 }
    fun left(): Column? = values().find { it.x == x - 1 }

    companion object {
        private const val ERROR_COLUMN_RANGE = "[ERROR] COLUMN의 범위는 1에서 15사이입니다."
        fun valueOf(x: Int): Column = values().find { it.x == x } ?: throw IllegalArgumentException(ERROR_COLUMN_RANGE)
    }
}
