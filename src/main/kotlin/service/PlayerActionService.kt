package service

import entity.Card


class PlayerActionService(private var mainService: MainService) : RefreshableService() {

    fun knock() {
        val game = mainService.currentGame
        val playerList = game!!.playerList

        for (player in playerList) {
            if (player.hasPlayerKnocked) {
                throw IllegalArgumentException("already knocked")
            }
        }
        val currentPlayer = mainService.dealerService.getCurrentPlayer()
        currentPlayer.hasPlayerKnocked = true
        resetPass()
        mainService.dealerService.endOfMove()
    }

    fun pass() {
        val game = mainService.currentGame
        game!!.passCount++
        mainService.dealerService.endOfMove()
    }

    fun changeSingleCard(pCard: Card, mCard: Card) {

        val player = mainService.dealerService.getCurrentPlayer()
        val playerCards = mainService.dealerService.getPlayerCards(player)
        val middleCards = mainService.dealerService.getMiddleCards()

        playerCards.remove(pCard)
        playerCards.add(mCard)
        middleCards.remove(mCard)
        middleCards.add(pCard)

        resetPass()
        mainService.dealerService.endOfMove()
    }

    fun changeAllCards() {
        val game = mainService.currentGame
        val index = game!!.moveCount
        val player = mainService.dealerService.getCurrentPlayer()
        val playerCards = mainService.dealerService.getPlayerCards(player)
        val middleCards = mainService.dealerService.getMiddleCards()
        mainService.currentGame!!.middleCards = playerCards
        mainService.currentGame!!.playerList[index].playerCards = middleCards
        resetPass()
        mainService.dealerService.endOfMove()
    }

    private fun resetPass() {
        val game = mainService.currentGame
        game!!.passCount = 0
    }

}