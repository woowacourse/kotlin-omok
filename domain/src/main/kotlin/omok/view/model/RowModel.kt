package omok.view.model

import omok.domain.board.Row

enum class RowModel(val text: String, val axis: Int) {
    ONE("1", 0),
    TWO("2", 1),
    THREE("3", 2),
    FOUR("4", 3),
    FIVE("5", 4),
    SIX("6", 5),
    SEVEN("7", 6),
    EIGHT("8", 7),
    NINE("9", 8),
    TEN("10", 9),
    ELEVEN("11", 10),
    TWELVE("12", 11),
    THIRTEEN("13", 12),
    FOURTEEN("14", 13),
    FIFTEEN("15", 14);
}

fun Row.toPresentation(): String = when (this) {
    Row.ONE -> RowModel.ONE
    Row.TWO -> RowModel.TWO
    Row.THREE -> RowModel.THREE
    Row.FOUR -> RowModel.FOUR
    Row.FIVE -> RowModel.FIVE
    Row.SIX -> RowModel.SIX
    Row.SEVEN -> RowModel.SEVEN
    Row.EIGHT -> RowModel.EIGHT
    Row.NINE -> RowModel.NINE
    Row.TEN -> RowModel.TEN
    Row.ELEVEN -> RowModel.ELEVEN
    Row.TWELVE -> RowModel.TWELVE
    Row.THIRTEEN -> RowModel.THIRTEEN
    Row.FOURTEEN -> RowModel.FOURTEEN
    Row.FIFTEEN -> RowModel.FIFTEEN
}.text
