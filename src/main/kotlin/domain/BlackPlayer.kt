package domain

class BlackPlayer(override val color: Color = Color.BLACK) : Player() {
    override fun validateOmokRule(stone: Stone, omokRule: OmokRule): Boolean {
        return !omokRule.isThreeToThree(stone) &&
            !omokRule.isFourToFour(stone) &&
            omokRule.findScore(stone) < LARGE_PLACE
    }

    companion object {
        const val LARGE_PLACE = 5
    }
}
