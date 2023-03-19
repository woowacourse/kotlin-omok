import controller.OmokController
import domain.rule.RenjuRule
import view.OmokInputView
import view.OmokOutputView

fun main() {
    OmokController(
        OmokInputView(),
        OmokOutputView()
    ).start(RenjuRule())
}
