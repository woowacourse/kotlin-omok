package domain

abstract class Player {
    abstract val color: Color
    abstract fun validateOmokRule(stone: Stone, omokRule: OmokRule): Boolean
}
