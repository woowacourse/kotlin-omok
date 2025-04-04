package woowacourse.omok.model

sealed class AddStoneStatus {

    sealed class Failed : AddStoneStatus() {

        data object IsExist : Failed()

        data object IsThreeThree : Failed()

        data object IsFourFour : Failed()

        data object IsOverFive : Failed()

        data object IsUnAblePosition : Failed()
    }

    data object IsWin : AddStoneStatus()

    data object IsAble : AddStoneStatus()

}
