package woowacourse.omok.domain.model

data class Point(val x: Int, val y: Int) {
    companion object {
        const val MESSAGE_INVALID_POINT_INPUT = "잘못된 위치 좌표입니다. 재입력 해주세요."
    }
}
