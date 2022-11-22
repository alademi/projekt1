package entity

import org.junit.jupiter.api.Test
import kotlin.test.*


internal class CardDeckStackTest {

    private val cardDeck = CardDeckStack()
    private val card1 = Card( CardValue.ACE,  CardSuit.CLUBS)
    private val card2 = Card(CardValue.EIGHT,  CardSuit.DIAMONDS)
    private val card3 = Card( CardValue.QUEEN, CardSuit.SPADES)
    private val card4 = Card( CardValue.JACK,  CardSuit.HEARTS)
    private val cardList = listOf(card1,card2,card3,card4)

    @Test
    fun getSize() {
     cardDeck.putOnTop(cardList)
        assertEquals(cardDeck.size,4)
    }

    @Test
    fun shuffle() {
        cardDeck.putOnTop(cardList)
        val cardDeck2 = cardDeck.shuffle()
       assertTrue(!cardDeck.equals(cardDeck2))
    }

    @Test
    fun draw() {
        cardDeck.putOnTop(cardList)
        cardDeck.draw(4)
        assertEquals(cardDeck.size,0)

    }

    @Test
    fun peekAll() {
        cardDeck.putOnTop(cardList)
        cardDeck.peekAll()
        assertNotNull(cardDeck)
    }

    @Test
    fun putOnTop() {
        cardDeck.putOnTop(cardList)
    }



}