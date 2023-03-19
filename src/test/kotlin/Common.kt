import domain.board.BasedBoard
import domain.board.Board
import domain.board.PlayingBoard
import domain.judgement.FiveStoneWinningConditionChecker
import domain.judgement.ForbiddenPositionChecker
import domain.judgement.RenjuRuleForbiddenPositionChecker
import domain.judgement.WinningConditionChecker
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

fun Stone(x: Int, y: Int, color: Color = Color.BLACK) = Stone(Position(x - 1, y - 1), color)
fun BasedBoard(vararg stones: Stone) = object : BasedBoard(stones.toList()) {
    override val isFinished: Boolean
        get() = false

    override val winningColor: Color
        get() {
            throw IllegalStateException("")
        }

    override fun isPossiblePut(position: Position): Boolean {
        return true
    }

    override fun addStone(stone: Stone): Board {
        return this
    }
}

fun PlayingBoard(
    stones: List<Stone>,
    winningConditionChecker: WinningConditionChecker = FiveStoneWinningConditionChecker(),
    forbiddenPositionChecker: ForbiddenPositionChecker = RenjuRuleForbiddenPositionChecker()
): PlayingBoard = PlayingBoard(stones, winningConditionChecker, forbiddenPositionChecker)

fun PlayingBoard(
    vararg stone: Stone,
    winningConditionChecker: WinningConditionChecker = FiveStoneWinningConditionChecker(),
    forbiddenPositionChecker: ForbiddenPositionChecker = RenjuRuleForbiddenPositionChecker()
): PlayingBoard = PlayingBoard(stone.toList(), winningConditionChecker, forbiddenPositionChecker)
