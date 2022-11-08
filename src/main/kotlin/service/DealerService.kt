package service

import entity.Card
import entity.CardValue
import entity.Player
import kotlin.math.max

class DealerService(private val mainService: MainService) : RefreshableService() {

    fun calculatePoints(playerCards: List<Card>): Double {
        require(playerCards.size == 3) {
            "Every Player should have 3 Cards , was ${playerCards.size}"
        }

        val points: Double
        val card1 = playerCards[0]
        val card2 = playerCards[1]
        val card3 = playerCards[2]
        val cardPoint1 = cardValues(playerCards[0])
        val cardPoint2 = cardValues(playerCards[1])
        val cardPoint3 = cardValues(playerCards[2])

        if (card1.symbol == card2.symbol) {
            if (card2.symbol == card3.symbol) {
                points = cardPoint1 + cardPoint2 + cardPoint3
                return points

            }
            return cardPoint1 + cardPoint2


        } else if (card1.symbol == card3.symbol) {
            return cardPoint1 + cardPoint3


        } else if (card2.symbol == card3.symbol) {
            return cardPoint2 + cardPoint3

        } else {

            if (card1.number == card2.number && card1.number == card3.number) {
                return 30.5

            }
            return max(cardPoint1, max(cardPoint2, cardPoint3))
        }

    }

    private fun cardValues(card: Card): Double {
        val points = when(card.number){
            CardValue.SEVEN -> 7.0
            CardValue.EIGHT -> 8.0
            CardValue.NINE -> 9.0
            CardValue.TEN -> 10.0
            CardValue.QUEEN -> 10.0
            CardValue.KING -> 10.0
            CardValue.JACK -> 10.0
            CardValue.ACE -> 11.0
            else -> 0.0
        }
        return points
    }

    fun getMiddleCards(): MutableList<Card> {
        val game = mainService.currentGame
        return game!!.middleCards
    }

    fun getDeck(): CardDeckStack {
        val game = mainService.currentGame
        return game!!.cardDeck
    }

    fun getPlayerCards(player: Player): MutableList<Card> {
        return player.playerCards
    }

    fun endOfMove() {
        val game = mainService.currentGame
        if (!gameEnd()) {
            if (game!!.passCount == game.playerList.size) {
                game.middleCards = game.cardDeck.draw(3)
                onAllRefreshables { refreshMiddleCard() }
            }
            var moveCount = game.moveCount++
            moveCount = (moveCount % game.playerList.size)
            onAllRefreshables { refreshAfterMove() }
            showNextPlayer()
        } else {
            mainService.exitGame()
        }

    }

    fun currentPlayer(): Player {
        val game = mainService.currentGame
        val index = game!!.moveCount
        return game.playerList[index]
    }

    fun getNextPlayer(): Player {
        val game = mainService.currentGame
        val index = game!!.moveCount
        return game.playerList[index + 1]
    }

    fun showNextPlayer() {
        onAllRefreshables { refreshHandCards() }
        onAllRefreshables { refreshPlayerLabel() }
    }

    private fun gameEnd(): Boolean {
        val game = mainService.currentGame
        if (game!!.passCount == game.playerList.size && game.cardDeck.size <= 2) {
            return true
        }

        if (getNextPlayer().hasPlayerKnocked) {
            return true
        }

        return false

    }

}