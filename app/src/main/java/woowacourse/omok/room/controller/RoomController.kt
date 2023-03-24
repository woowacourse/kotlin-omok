package woowacourse.omok.room.controller

import controller.GameController
import controller.StoneMapper.toDTO
import controller.StoneMapper.toDomain
import domain.Stones
import dto.StoneDTO
import woowacourse.omok.room.db.OmokDBHelper
import woowacourse.omok.room.domain.Stage
import woowacourse.omok.room.domain.User
import woowacourse.omok.room.view.AndroidRoomView

class RoomController(
    private val omokDBHelper: OmokDBHelper,
    private val roomView: AndroidRoomView,
    private val omokController: GameController
) {
    fun process() {
        roomView.setUp(::onSelectUser, ::onSelectStage)
        roomView.userAddObserver.subscribe(::addUser)
        getAllUsers()
        omokController.process()
    }

    private fun getAllUsers() {
        val users = omokDBHelper.selectAllUsers()
        roomView.setAllUsers(users)
        getAllStagesByUserId(users.value[0])
    }

    private fun getAllStagesByUserId(user: User) {
        val stages = omokDBHelper.selectAllStagesByUser(user)
        roomView.setAllStages(stages)

        roomView.stageAddObserver.unsubscribeAll()
        roomView.stageAddObserver.subscribe {
            addStage(user)
        }
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

    private fun addUser() {
        omokDBHelper.insertUser((0..300).random().toString())
        getAllUsers()
    }

    private fun addStage(user: User) {
        val stageId = omokDBHelper.insertStageByUser(user)
        getAllStagesByUserId(user)
        onSelectStage(Stage(stageId))
    }
}
