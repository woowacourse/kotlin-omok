package error

sealed interface CoordinateError : OmokError {
    object OutOfBoard : CoordinateError {
        override val message: String
            get() = "보드의 범위에 벗어나는 좌표입니다."
    }
}
