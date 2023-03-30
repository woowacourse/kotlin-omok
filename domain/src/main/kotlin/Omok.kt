import domain.OmokGame
import domain.event.GameEventManager
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
    val gameEventManager = GameEventManager()
    gameEventManager.add(OutputView)
    return OmokGame(gameEventManager)
}

private fun getStoneThatCanPlace(omokGame: OmokGame): Stone {
    var stone = InputView.readStone()
    while (omokGame.canPlace(stone).not()) {
        OutputView.printStoneViolateRuleMessage()
        stone = InputView.readStone()
    }
    return stone
}
