import domain.OmokGame
import domain.Stone
import domain.event.FinishEventManager
import domain.event.PlaceStoneEventManager
import domain.event.StartEventManager
import view.InputView
import view.OutputView

fun main() {
    val omokGame = createOmokGame()
    while (omokGame.isFinished().not()) {
        val stone = getStoneThatCanPlace(omokGame)
        omokGame.place(stone)
    }
}

private fun createOmokGame(): OmokGame {
    val startEventManager = StartEventManager()
    startEventManager.add(OutputView)
    val placeStoneEventManager = PlaceStoneEventManager()
    placeStoneEventManager.add(OutputView)
    val finishEventManager = FinishEventManager()
    finishEventManager.add(OutputView)
    return OmokGame(startEventManager, placeStoneEventManager, finishEventManager)
}

private fun getStoneThatCanPlace(omokGame: OmokGame): Stone {
    var stone = InputView.readStone()
    while (omokGame.canPlace(stone).not()) {
        OutputView.printStoneViolateRuleMessage()
        stone = InputView.readStone()
    }
    return stone
}
