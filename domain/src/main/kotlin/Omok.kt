import domain.OmokGame
import domain.event.CreateEventManager
import domain.event.FinishEventManager
import domain.event.PlaceStoneEventManager
import domain.stone.Stone
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
    val createEventManager = CreateEventManager()
    createEventManager.add(OutputView)
    val placeStoneEventManager = PlaceStoneEventManager()
    placeStoneEventManager.add(OutputView)
    val finishEventManager = FinishEventManager()
    finishEventManager.add(OutputView)
    return OmokGame(createEventManager, placeStoneEventManager, finishEventManager)
}

private fun getStoneThatCanPlace(omokGame: OmokGame): Stone {
    var stone = InputView.readStone()
    while (omokGame.canPlace(stone).not()) {
        OutputView.printStoneViolateRuleMessage()
        stone = InputView.readStone()
    }
    return stone
}
