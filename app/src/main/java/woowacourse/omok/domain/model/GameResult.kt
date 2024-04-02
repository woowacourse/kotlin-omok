package woowacourse.omok.domain.model

sealed interface GameResult

data object Success : GameResult

sealed class Failure(val message: String) : GameResult {
    data object ThreeThree : Failure("3-3 금수입니다.")

    data object FourFour : Failure("4-4 금수입니다.")

    data object MoreThanFive : Failure("5개 이상의 연속된 돌은 놓을 수 없습니다.")

    data object DuplicatePoint : Failure("이미 돌이 놓인 자리입니다.")
}
