package service

import entity.*
import view.Refreshable

/**
 * Diese Klasse
 */

class MainService() : RefreshableService() {
    var currentGame: World? = null
    var playerActionService = PlayerActionService(this)
    var dealerService = DealerService(this)
    var cardStack: CardDeckStack? = null


    fun startNewGame(namesList: List<String>) {
        val cardDeck = defaultCardDeck()
        cardStack = CardDeckStack()
        cardStack!!.putOnTop(cardDeck)
        cardStack!!.shuffle()
        val middleCards = cardStack!!.draw(3)
        val playerList = playerSetting(namesList)
        val passCount = 0
        val moveCount = 0
        val game = World(middleCards, moveCount, passCount, playerList, cardStack!!)
        currentGame = game

        onAllRefreshables { refreshAfterStartNewGame() }

    }

    fun playerSetting(namesList: List<String>): MutableList<Player> {

        val playerList = mutableListOf<Player>()

        for (playerName in namesList) {
            val card = cardStack!!.draw(3)
            val player = Player(playerName, 0.0, card, false)
            playerList.add(player)
        }
        return playerList
    }

    fun exitGame() {
        highScoreList()
        onAllRefreshables { refreshAfterGameEnd() }

    }

    private fun defaultCardDeck() = MutableList(32) { index ->
        Card(
            CardValue.values()[(index % 8) + 5],
            CardSuit.values()[index / 8]
        )
    }.shuffled()


    override fun addRefreshable(re: Refreshable) {
        this.addRefreshable(re)
        playerActionService.addRefreshable(re)
        dealerService.addRefreshable(re)
    }

    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }

    private fun highScoreList()  {
        val playersList = currentGame!!.playerList
        for (player in playersList) {
            player.score = dealerService.calculatePoints(player.playerCards)
        }
         playersList.sortByDescending{it.score}
    }

}