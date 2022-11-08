package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DealerServiceTest {
    private val card1 = Card( CardValue.ACE,  CardSuit.CLUBS)
    private val card2 = Card(CardValue.NINE,  CardSuit.CLUBS)
    private val card3 = Card( CardValue.JACK, CardSuit.CLUBS)
    private val card4 = Card( CardValue.SEVEN,  CardSuit.HEARTS)
    private val card5 = Card( CardValue.TEN,  CardSuit.HEARTS)
    private val card6 = Card( CardValue.JACK,  CardSuit.HEARTS)
    private val card7 = Card( CardValue.ACE,  CardSuit.SPADES)
    private val card8 = Card( CardValue.SEVEN,  CardSuit.SPADES)
    private val card9 = Card( CardValue.QUEEN,  CardSuit.CLUBS)
    private val card10 = Card( CardValue.JACK,  CardSuit.HEARTS)
    private val card11 = Card( CardValue.QUEEN,  CardSuit.DIAMONDS)
    private val card12 = Card( CardValue.KING,  CardSuit.CLUBS)
    private val card13 = Card( CardValue.EIGHT,  CardSuit.CLUBS)
    private val card14 = Card( CardValue.EIGHT,  CardSuit.DIAMONDS)
    private val card15 = Card( CardValue.EIGHT,  CardSuit.SPADES)



    private val listCards1 = mutableListOf<Card>(card1, card2, card3)
    private val listCards2 = mutableListOf<Card>(card4, card5, card6)
    private val listCards3 = mutableListOf<Card>(card7, card8, card9)
    private val listCards4 = mutableListOf<Card>(card10, card11, card12)
    private val listCards5 = mutableListOf<Card>(card13, card14, card15)

    @Test
    fun test()
    {
        val playerlist = listOf<String>("Aziz" , "Tom" , "Ali" ,"Sam")
        val game = MainService()
        game.startNewGame(playerlist)
        val points1 = game.dealerService.calculatePoints(listCards1)
        val points2 = game.dealerService.calculatePoints(listCards2)
        val points3 = game.dealerService.calculatePoints(listCards3)
        val points4 = game.dealerService.calculatePoints(listCards4)
        val points5 = game.dealerService.calculatePoints(listCards5)
        assertEquals(points1,30.0)
        assertEquals(points2,27.0)
        assertEquals(points3,18.0)
        assertEquals(points4,10.0)
        assertEquals(points5,30.5)
        val middleCards = game.dealerService.getMiddleCards()
        assertEquals(middleCards.size,3)
        assertNotNull(middleCards)
        val cardDeck = game.dealerService.getDeck()
        assertNotNull(cardDeck)
        val playerCard = game.dealerService.getPlayerCards(game.currentGame!!.playerList[game.currentGame!!.moveCount])
        assertNotNull(playerCard)
        assertEquals(playerCard.size,3)
        val currentPlayer = game.dealerService.getCurrentPlayer()
        assertNotNull(currentPlayer)
        val nextPlayer = game.dealerService.getNextPlayer()
        assertNotNull((nextPlayer))


    }

}