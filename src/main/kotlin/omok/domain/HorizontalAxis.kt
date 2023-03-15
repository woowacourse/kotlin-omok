package omok.domain

enum class HorizontalAxis(val value: Int) {
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

    companion object {
        fun getHorizontalAxis(other: Int): HorizontalAxis {
            HorizontalAxis.values().forEach {
                if (it.value == other) {
                    return it
                }
            }
            return throw IllegalStateException("일치하는 값이 없습니다.")
        }
    }
}
