package woowacourse.omok.controller

import controller.GameController
import controller.StoneMapper.toDTO
import controller.StoneMapper.toDomain
import domain.Stones
import dto.StoneDTO
import woowacourse.omok.db.OmokDBHelper
import woowacourse.omok.domain.Stage
import woowacourse.omok.domain.User
import woowacourse.omok.view.AndroidRoomView

class RoomController(
    private val omokDBHelper: OmokDBHelper,
    private val roomView: AndroidRoomView,
    private val omokController: GameController
) {
    fun process() {
        roomView.setUp(::onSelectUser, ::onSelectStage)
        getAllUsers()
        omokController.process()
    }

    private fun getAllUsers() {
        val users = omokDBHelper.selectAllUsers()
        roomView.setAllUsers(users)
        getAllStagesByUserId(users.value[0])
    }

    private fun getAllStagesByUserId(user: User) {
        val stages = omokDBHelper.selectAllStagesByUserId(user)
        roomView.setAllStages(stages)
    }

    private fun onSelectUser(user: User) {
        getAllStagesByUserId(user)
    }

    private fun onSelectStage(stage: Stage) {
        omokController.onPlaceStone = {
            onPlaceStone(it.toDTO(), stage)
            getAllUsers()
        }
        omokController.resetStage(Stones(stage.value.value.map { it.toDomain() }))
    }

    private fun onPlaceStone(stoneDTO: StoneDTO, stage: Stage) {
        omokDBHelper.insertStoneToStage(stoneDTO, stage)
    }
}
