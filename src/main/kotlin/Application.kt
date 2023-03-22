import controller.OmokController
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import view.OmokInputView
import view.OmokOutputView

fun main() {
    OmokController(
        OmokInputView(),
        OmokOutputView(),
    ).start(BlackRenjuRule(), WhiteRenjuRule())
}
