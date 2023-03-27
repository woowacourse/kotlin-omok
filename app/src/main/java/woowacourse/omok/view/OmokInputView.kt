package woowacourse.omok.view

import domain.point.Point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import woowacourse.omok.view.mapper.toDomain
import woowacourse.omok.view.model.PointModel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class OmokInputView : InputView {
    override suspend fun readPosition(): Point = suspendCoroutine { continuation ->
        print(ASK_POSITION_MESSAGE)
        val colRow = readln()
        if (colRow.length !in POSITION_INPUT_RANGE) {
            println(INVALID_FORMAT_ERROR_MESSAGE)
            CoroutineScope(Dispatchers.Main).launch { readPosition() }
        }

        val newPoint = PointModel(
            row = colRow.substring(ROW_INPUT_SIZE),
            col = colRow.first().toString(),
        ).toDomain()
        newPoint?.let { continuation.resume(it) }
            ?: CoroutineScope(Dispatchers.Main).launch { readPosition() }
    }

    companion object {
        private const val ASK_POSITION_MESSAGE = "위치를 입력하세요: "
        private const val INVALID_FORMAT_ERROR_MESSAGE = "포맷에 맞지 않는 입력값입니다."

        private const val ROW_INPUT_SIZE = 1
        private const val MIN_POSITION_INPUT_SIZE = 2
        private const val MAX_POSITION_INPUT_SIZE = 3
        private val POSITION_INPUT_RANGE = MIN_POSITION_INPUT_SIZE..MAX_POSITION_INPUT_SIZE
    }
}
