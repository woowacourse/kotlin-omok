package omok.model.rule

@JvmInline
value class ContinualStonesStandard(val count: Int) {
    init {
        require(count in 4..7) { "정상적인 게임 진행을 위해 사목 부터 칠목으로 지정 가능합니다. " }
    }
}
