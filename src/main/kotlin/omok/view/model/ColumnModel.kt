package omok.view.model

import omok.domain.board.Column

enum class ColumnModel(val text: String) {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H"),
    I("I"),
    J("J"),
    K("K"),
    L("L"),
    M("M"),
    N("N"),
    O("O");
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
