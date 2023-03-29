package error

sealed interface StoneReadError : OmokError {
    object ColumnNotAlpha : StoneReadError {
        override val message: String
            get() = "행은 반드시 영문자여야 합니다."
    }

    object RowNotNumeric : StoneReadError {
        override val message: String
            get() = "열은 반드시 숫자여야 합니다."
    }

    object Empty : StoneReadError {
        override val message: String
            get() = "빈 입력입니다."
    }
}
