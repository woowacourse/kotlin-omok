package omok.model

enum class Column(val title: Char, val value: Int) {
    A('A', 1),
    B('B', 2),
    C('C', 3),
    D('D', 4),
    E('E', 5),
    F('F', 6),
    G('G', 7),
    H('H', 8),
    I('I', 9),
    J('J', 10),
    K('K', 11),
    L('L', 12),
    M('M', 13),
    N('N', 14),
    O('O', 15),
    ;

    companion object {
        fun titleOf(col: Char): Column? {
            return entries.find {
                it.title == col
            }
        }
    }
}
