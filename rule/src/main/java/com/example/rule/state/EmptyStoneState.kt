package com.example.rule.state

import com.example.rule.ArkBoard
import com.example.rule.OmokPoint

object EmptyStoneState : StoneState {
    override val korean: String
        get() = ""

    override fun checkForbidden(arkBoard: ArkBoard, point: OmokPoint): Boolean {
        throw IllegalStateException(ERROR_EMPTY_STONE_NO_RULE)
    }

    override fun validateWinner(arkBoard: ArkBoard, point: OmokPoint): Boolean {
        throw IllegalStateException(ERROR_EMPTY_DO_NOT_BECOME_WINNER)
    }

    override fun next(): StoneState {
        throw IllegalStateException(ERROR_EMPTY_NO_NEXT)
    }

    private const val ERROR_EMPTY_STONE_NO_RULE = "빈 돌은 규칙을 확인 할 수 없습니다."
    private const val ERROR_EMPTY_DO_NOT_BECOME_WINNER = "빈 돌은 승리자가 될 수 없습니다."
    private const val ERROR_EMPTY_NO_NEXT = "빈 돌은 다음 순서가 없습니다."
}
