package View

import view.Refreshable

class RefreshableTest : Refreshable {
    var refreshAfterStartNewGame: Boolean = false
        private set
    var refreshHandCards: Boolean = false
        private set
    var refreshPlayerLabel: Boolean = false
        private set
    var refreshMiddleCard: Boolean = false
        private set
    var refreshAfterMove: Boolean = false
        private set
    var refreshAfterGameEnd: Boolean = false
        private set
    var refreshButtons: Boolean = false
        private set
    var refreshGameSetting: Boolean = false
        private set

    fun reset() {
        refreshAfterStartNewGame = false
        refreshHandCards = false
        refreshPlayerLabel = false
        refreshMiddleCard = false
        refreshAfterMove = false
        refreshAfterGameEnd = false
        refreshButtons = false
        refreshGameSetting = false
    }

    override fun refreshAfterStartNewGame() {
        refreshAfterStartNewGame = true
    }

    override fun refreshHandCards() {
        refreshHandCards = true
    }

    override fun refreshPlayerLabel() {
        refreshPlayerLabel = true
    }

    override fun refreshMiddleCard() {
        refreshMiddleCard = true
    }

    override fun refreshAfterMove() {
        refreshAfterMove = true
    }

    override fun refreshAfterGameEnd() {
        refreshAfterGameEnd = true
    }

    override fun refreshButtons() {
        refreshButtons = true
    }

    override fun refreshGameSetting() {
        refreshGameSetting = true
    }


}