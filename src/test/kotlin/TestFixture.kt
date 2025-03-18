import domain.model.Column
import domain.model.Position
import domain.model.Row
import domain.model.Stone

val horizontalWinStones =
    mapOf(
        Position(Column.from('A'), Row(1)) to Stone.BLACK,
        Position(Column.from('B'), Row(1)) to Stone.BLACK,
        Position(Column.from('C'), Row(1)) to Stone.BLACK,
        Position(Column.from('D'), Row(1)) to Stone.BLACK,
        Position(Column.from('E'), Row(1)) to Stone.BLACK,
    )

val verticalWinStones =
    mapOf(
        Position(Column.from('A'), Row(1)) to Stone.BLACK,
        Position(Column.from('A'), Row(2)) to Stone.BLACK,
        Position(Column.from('A'), Row(3)) to Stone.BLACK,
        Position(Column.from('A'), Row(4)) to Stone.BLACK,
        Position(Column.from('A'), Row(5)) to Stone.BLACK,
    )

val diagonalDownWinStones =
    mapOf(
        Position(Column.from('A'), Row(1)) to Stone.BLACK,
        Position(Column.from('B'), Row(2)) to Stone.BLACK,
        Position(Column.from('C'), Row(3)) to Stone.BLACK,
        Position(Column.from('D'), Row(4)) to Stone.BLACK,
        Position(Column.from('E'), Row(5)) to Stone.BLACK,
    )
