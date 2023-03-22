package domain.stone

enum class Column(private val x: Int) {
    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6),
    H(7),
    I(8),
    J(9),
    K(10),
    L(11),
    M(12),
    N(13),
    O(14);

    fun right(): Column? = Column.values().getOrNull(ordinal + 1)
    fun left(): Column? = Column.values().getOrNull(ordinal - 1)

    companion object {
        private const val ERROR_COLUMN_RANGE = "[ERROR] COLUMN의 범위는 1에서 15사이입니다."
        private val COLUMNS: Map<Int, Column> = Column.values().associateBy { it.x }
        fun valueOf(x: Int): Column =
            COLUMNS[x] ?: throw IllegalArgumentException(ERROR_COLUMN_RANGE)
    }
}
