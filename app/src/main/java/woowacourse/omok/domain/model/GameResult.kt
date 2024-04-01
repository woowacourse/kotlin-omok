package woowacourse.omok.domain.model

sealed interface GameResult {
    val message: String

    data object Success : GameResult {
        override val message: String = ""
    }

    data object ThreeThree : GameResult {
        override val message: String = "3-3 금수입니다."
    }

    data object FourFour : GameResult {
        override val message: String = "4-4 금수입니다."
    }

    data object MoreThanFive : GameResult {
        override val message: String = "5개 이상의 연속된 돌은 놓을 수 없습니다."
    }

    data object DuplicatePoint : GameResult {
        override val message: String = "이미 돌이 놓인 자리입니다."
    }
}
