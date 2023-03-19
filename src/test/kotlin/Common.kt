// import domain.turn.Turn
// import domain.turn.RunningTurn
// import domain.judgement.FiveStoneWinningCondition
// import domain.judgement.ForbiddenCondition
// import domain.judgement.RenjuRuleForbiddenCondition
// import domain.judgement.WinningCondition
import domain.stone.Color
import domain.stone.Column
import domain.stone.Position
import domain.stone.Row
import domain.stone.Stone

//
fun Stone(x: Int, y: Int, color: Color = Color.BLACK) = Stone(Position(x - 1, y - 1), color)

private val POSITIONS: List<Position> = Column.values().flatMap { column ->
    Row.values().map { row ->
        Position(column, row)
    }
}

fun List<Stone>.convertToBoard(): Map<Position, Color?> {
    val map: MutableMap<Position, Color?> = POSITIONS.associateWith { null }.toMutableMap()
    forEach {
        map[it.position] = it.color
    }
    return map
}
// fun BasedBoard(vararg stones: Stone) = object : StartedTurn(stones.toList()) {
//     override val isFinished: Boolean
//         get() = false
//
//     override val winningColor: Color
//         get() {
//             throw IllegalStateException("")
//         }
//
//     override fun isPossiblePut(position: Position): Boolean {
//         return true
//     }
//
//     override fun addStone(stone: Stone): Turn {
//         return this
//     }
// }
//
// fun PlayingBoard(
//     stones: List<Stone>,
//     winningConditionChecker: WinningCondition = FiveStoneWinningCondition(),
//     forbiddenPositionChecker: ForbiddenCondition = RenjuRuleForbiddenCondition()
// ): RunningTurn = RunningTurn(stones, winningConditionChecker, forbiddenPositionChecker)
//
// fun PlayingBoard(
//     vararg stone: Stone,
//     winningConditionChecker: WinningCondition = FiveStoneWinningCondition(),
//     forbiddenPositionChecker: ForbiddenCondition = RenjuRuleForbiddenCondition()
// ): RunningTurn = RunningTurn(stone.toList(), winningConditionChecker, forbiddenPositionChecker)
