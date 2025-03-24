import controller.OmokController
import view.InputView
import view.ResultView

fun main() {
    val omokController = OmokController(InputView(), ResultView())
    omokController.run()
}
