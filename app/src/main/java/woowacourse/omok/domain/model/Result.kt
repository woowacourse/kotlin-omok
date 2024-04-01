package woowacourse.omok.domain.model

sealed interface Result {
    val message: String

    data object Success : Result {
        override val message: String = ""
    }

    data object ThreeThree : Result {
        override val message: String = "3-3 금수입니다."
    }

    data object FourFour : Result {
        override val message: String = "4-4 금수입니다."
    }

    data object MoreThanFive : Result {
        override val message: String = "5개 이상의 연속된 돌은 놓을 수 없습니다."
    }

    data object DuplicatePoint : Result {
        override val message: String = "이미 돌이 놓인 자리입니다."
    }
}
