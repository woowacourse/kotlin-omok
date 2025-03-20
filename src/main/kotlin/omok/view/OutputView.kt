package omok.view

class OutputView {
    fun printTurn(omokStoneUiModel: OmokStoneUiModel) {
        println("${omokStoneUiModel.stone}의 차례입니다. (마지막 돌의 위치: ${omokStoneUiModel.position}")
    }
}
