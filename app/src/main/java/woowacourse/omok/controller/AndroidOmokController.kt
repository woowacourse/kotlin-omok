package woowacourse.omok.controller

import domain.game.Omok
import domain.point.Point
import domain.rule.OmokRule
import kotlinx.coroutines.channels.Channel
import repository.Repository
import woowacourse.omok.console.InputView
import woowacourse.omok.console.OutputView

class AndroidOmokController(
    inputView: InputView,
    outputView: OutputView,
    private val omokRepo: Repository<Point>,
) : OmokController(inputView, outputView) {
    private lateinit var omok: Omok
    val pointChannel = Channel<Point>()

    override suspend fun startGame(blackRule: OmokRule, whiteRule: OmokRule) {
        omok = Omok(blackRule, whiteRule)
        outputView.startGame()

        while (omok.canPlay()) {
            takeTurn(omok) { putResult ->
                processPutResult(putResult)
                outputView.showCurrentTurnColor(omok.curPlayerColor, omok.lastPoint)
            }
        }
    }

    suspend fun loadPreviousPoints(onLoaded: (List<Point>, pointsCount: Int) -> Unit) {
        val previousPoints = omokRepo.getAll()
        previousPoints.forEach { point -> omok.put { point } }
        onLoaded(previousPoints, previousPoints.size)
    }

    fun clearPoints() {
        omokRepo.clear()
    }

    fun savePoint(point: Point) {
        omokRepo.insert(point)
    }

    fun hasPreviousGameHistory(): Boolean = !omokRepo.isEmpty()

    suspend fun putStone(row: Int, col: Int) {
        pointChannel.send(Point(row, col))
    }
}
