package domain.player

import domain.point.Point
import domain.point.Points
import domain.rule.OmokRule
import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.StoneColor

class BlackPlayer(state: PlayerState = PlayingState(), rule: OmokRule) : Player(state, rule) {
    override fun putStone(stone: Point, otherStones: Points): Player = BlackPlayer(state.add(stone, otherStones, rule), rule)

    override fun getStoneColor(): StoneColor = StoneColor.BLACK
}
