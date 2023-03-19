// import domain.board.Turn
// import domain.board.RunningTurn
// import domain.judgement.FiveStoneWinningCondition
// import domain.judgement.ForbiddenCondition
// import domain.judgement.RenjuRuleForbiddenCondition
// import domain.judgement.WinningCondition
// import domain.stone.Color
// import domain.stone.Position
// import domain.stone.Stone
//
// fun Stone(x: Int, y: Int, color: Color = Color.BLACK) = Stone(Position(x - 1, y - 1), color)
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
