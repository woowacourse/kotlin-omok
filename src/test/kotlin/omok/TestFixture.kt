package omok

import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

val horizontalWinStones =
    listOf(
        OmokStone(Position(Column.from('A'), Row(1)), StoneType.BLACK),
        OmokStone(Position(Column.from('B'), Row(1)), StoneType.BLACK),
        OmokStone(Position(Column.from('C'), Row(1)), StoneType.BLACK),
        OmokStone(Position(Column.from('D'), Row(1)), StoneType.BLACK),
    )

val verticalWinStones =
    listOf(
        OmokStone(Position(Column.from('A'), Row(1)), StoneType.BLACK),
        OmokStone(Position(Column.from('A'), Row(2)), StoneType.BLACK),
        OmokStone(Position(Column.from('A'), Row(3)), StoneType.BLACK),
        OmokStone(Position(Column.from('A'), Row(4)), StoneType.BLACK),
    )

val diagonalDownWinStones =
    listOf(
        OmokStone(Position(Column.from('A'), Row(1)), StoneType.BLACK),
        OmokStone(Position(Column.from('B'), Row(2)), StoneType.BLACK),
        OmokStone(Position(Column.from('C'), Row(3)), StoneType.BLACK),
        OmokStone(Position(Column.from('D'), Row(4)), StoneType.BLACK),
    )

val doubleThreeFixture =
    listOf(
        OmokStone(Position(Column.from('B'), Row(6)), StoneType.BLACK),
        OmokStone(Position(Column.from('E'), Row(6)), StoneType.BLACK),
        OmokStone(Position(Column.from('C'), Row(5)), StoneType.BLACK),
        OmokStone(Position(Column.from('E'), Row(5)), StoneType.BLACK),
    )

val doubleFourFixture =
    listOf(
        OmokStone(Position(Column.from('C'), Row(10)), StoneType.BLACK),
        OmokStone(Position(Column.from('C'), Row(11)), StoneType.BLACK),
        OmokStone(Position(Column.from('C'), Row(12)), StoneType.BLACK),
        OmokStone(Position(Column.from('C'), Row(14)), StoneType.BLACK),
        OmokStone(Position(Column.from('C'), Row(15)), StoneType.BLACK),
        OmokStone(Position(Column.from('D'), Row(12)), StoneType.BLACK),
    )

val stoneLongMoveFixture =
    listOf(
        OmokStone(Position(Column.from('B'), Row(2)), StoneType.BLACK),
        OmokStone(Position(Column.from('C'), Row(3)), StoneType.BLACK),
        OmokStone(Position(Column.from('D'), Row(4)), StoneType.BLACK),
        OmokStone(Position(Column.from('F'), Row(6)), StoneType.BLACK),
        OmokStone(Position(Column.from('G'), Row(7)), StoneType.BLACK),
    )
