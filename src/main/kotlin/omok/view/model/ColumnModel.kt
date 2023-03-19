package omok.view.model

import omok.domain.board.Column

enum class ColumnModel(val text: String, val axis: Int) {
    A("A", 1),
    B("B", 2),
    C("C", 3),
    D("D", 4),
    E("E", 5),
    F("F", 6),
    G("G", 7),
    H("H", 8),
    I("I", 9),
    J("J", 10),
    K("K", 11),
    L("L", 12),
    M("M", 13),
    N("N", 14),
    O("O", 15);
}

fun Column.toPresentation(): String = when (this) {
    Column.A -> ColumnModel.A
    Column.B -> ColumnModel.B
    Column.C -> ColumnModel.C
    Column.D -> ColumnModel.D
    Column.E -> ColumnModel.E
    Column.F -> ColumnModel.F
    Column.G -> ColumnModel.G
    Column.H -> ColumnModel.H
    Column.I -> ColumnModel.I
    Column.J -> ColumnModel.J
    Column.K -> ColumnModel.K
    Column.L -> ColumnModel.L
    Column.M -> ColumnModel.M
    Column.N -> ColumnModel.N
    Column.O -> ColumnModel.O
}.text
