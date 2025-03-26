package model

sealed class AddStoneStatus {
    data object IsExist : AddStoneStatus()

    data object IsThreeThree : AddStoneStatus()

    data object IsFourFour : AddStoneStatus()

    data object IsWin : AddStoneStatus()

    data object IsAble : AddStoneStatus()

    data object IsOverFive : AddStoneStatus()

    data object IsUnAblePosition : AddStoneStatus()
}
