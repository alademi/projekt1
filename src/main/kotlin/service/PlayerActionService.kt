package service

import entity.Card


class PlayerActionService(private var mainService: MainService) : RefreshableService() {

    fun knock() {
        val game = mainService.currentGame
        val playerIndex = game!!.moveCount
        val playerList = game.playerList

        for (player in playerList) {
            if (player.hasPlayerKnocked) {
                throw IllegalArgumentException("already knocked")
            }
        }
        val currentPlayer = mainService.dealerService.currentPlayer()
        currentPlayer.hasPlayerKnocked = true
        resetPass()
        mainService.dealerService.endOfMove()
        mainService.dealerService.showNextPlayer()

    }

    fun pass() {
        val game = mainService.currentGame
        game!!.passCount++
        mainService.dealerService.endOfMove()
        mainService.dealerService.showNextPlayer()
    }

    fun changeSingleCard(pCard: Card, mCard: Card) {
        val game = mainService.currentGame
        val player = mainService.dealerService.currentPlayer()
        val playerCards = mainService.dealerService.getPlayerCards(player)
        val middleCards = mainService.dealerService.getMiddleCards()
        playerCards.remove(pCard)
        middleCards.remove(mCard)
        playerCards.add(mCard)
        middleCards.add(pCard)
        resetPass()
        mainService.dealerService.endOfMove()
        mainService.dealerService.showNextPlayer()
    }

    fun changeAllCards() {
        val game = mainService.currentGame
        val index = game!!.moveCount
        val player = mainService.dealerService.currentPlayer()
        val playerCards = mainService.dealerService.getPlayerCards(player)
        val middleCards = mainService.dealerService.getMiddleCards()
        mainService.currentGame!!.middleCards = playerCards
        mainService.currentGame!!.playerList[index].playerCards = middleCards
        resetPass()
        mainService.dealerService.endOfMove()
        mainService.dealerService.showNextPlayer()
    }

    private fun resetPass() {
        val game = mainService.currentGame
        game!!.passCount++
    }

}