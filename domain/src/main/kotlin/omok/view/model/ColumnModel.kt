package omok.view.model

import omok.domain.board.Column

enum class ColumnModel(val text: String, val axis: Int) {
    A("A", 0),
    B("B", 1),
    C("C", 2),
    D("D", 3),
    E("E", 4),
    F("F", 5),
    G("G", 6),
    H("H", 7),
    I("I", 8),
    J("J", 9),
    K("K", 10),
    L("L", 11),
    M("M", 12),
    N("N", 13),
    O("O", 14);
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
