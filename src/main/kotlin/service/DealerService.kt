package service

import entity.Card
import entity.Player
import kotlin.math.max

class DealerService(private val mainService: MainService) : RefreshableService() {

    fun calculatePoints(playerCards: List<Card>): Double {
        require(playerCards.size == 3) {
            "Every Player should have 3 Cards , was ${playerCards.size}"
        }

        var points = 0.0

        val card1 = playerCards[0]
        val card2 = playerCards[1]
        val card3 = playerCards[2]
        val cardPoint1 = cardValues(playerCards[0])
        val cardPoint2 = cardValues(playerCards[1])
        val cardPoint3 = cardValues(playerCards[2])

        if (card1.symbol == card2.symbol) {
            if (card2.symbol == card3.symbol) {
                points = cardPoint1 + cardPoint2 + cardPoint3

            }
            points = cardPoint1 + cardPoint2


        } else if (card1.symbol == card3.symbol) {
            points = cardPoint1 + cardPoint3

        } else if (card2.symbol == card3.symbol) {
            points = cardPoint2 + cardPoint3

        } else {

            if (card1.number == card2.number && card1.number == card3.number) {
                points = 30.5

            } else {
                var max = max(cardPoint1, max(cardPoint2, cardPoint3))
                max = points
            }

        }
        return points
    }

    private fun cardValues(card: Card): Double {
        if (card.number.name == "SIX") {
            return 6.0
        }
        if (card.number.name == "SEVEN") {
            return 7.0
        }
        if (card.number.name == "EIGHT") {
            return 8.0
        }
        if (card.number.name == "NINE") {
            return 9.0
        }
        if (card.number.name == "TEN") {
            return 10.0
        }
        if (card.number.name == "JACK") {
            return 10.0
        }
        if (card.number.name == "QUEEN") {
            return 10.0
        }
        if (card.number.name == "KING") {
            return 10.0
        }
        if (card.number.name == "ACE") {
            return 11.0
        }
        return 0.0
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
        var moveCount = game!!.moveCount++
        moveCount = (moveCount % game.playerList.size)
        onAllRefreshables { refreshAfterMove() }
    }

     fun currentPlayer(): Player {
        val game = mainService.currentGame
        val index = game!!.moveCount
        return game.playerList[index]
    }

    fun showNextPlayer() {
        onAllRefreshables { refreshHandCards() }
        onAllRefreshables { refreshPlayerLabel() }
    }

}