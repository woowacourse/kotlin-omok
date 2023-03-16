package domain.stone

enum class Column(val x: Int) {
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

    fun right(): Column? = values().find { it.x == x + 1 }
    fun left(): Column? = values().find { it.x == x - 1 }

    companion object {
        fun valueOf(x: Int): Column = values().find { it.x == x } ?: throw IllegalArgumentException("")
    }
}
