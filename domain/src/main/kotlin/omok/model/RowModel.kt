package omok.model

import omok.domain.board.Row

enum class RowModel(val text: String) {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    ELEVEN("11"),
    TWELVE("12"),
    THIRTEEN("13"),
    FOURTEEN("14"),
    FIFTEEN("15");
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
