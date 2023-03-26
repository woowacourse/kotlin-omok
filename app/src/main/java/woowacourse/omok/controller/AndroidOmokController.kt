package woowacourse.omok.controller

import domain.game.Omok
import domain.point.Point
import domain.rule.OmokRule
import kotlinx.coroutines.channels.Channel
import repository.Repository

class AndroidOmokController(
    private val controller: ConsoleOmokController,
    private val omokRepo: Repository<Point>,
) {
    private lateinit var omok: Omok
    val pointChannel = Channel<Point>()

    suspend fun startGame(blackRule: OmokRule, whiteRule: OmokRule) {
        omok = Omok(blackRule, whiteRule)
        controller.startGame(omok)
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
