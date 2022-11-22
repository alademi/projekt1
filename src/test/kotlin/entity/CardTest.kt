


package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Testfälle der Klasse [Card]
 */

class CardTest {

    /**
     * Hier werden einige Karten instanziiert, um den Test durchzuführen
     */

    private val card1 = Card( CardValue.ACE,  CardSuit.CLUBS)
    private val card2 = Card(CardValue.EIGHT,  CardSuit.DIAMONDS)
    private val card3 = Card( CardValue.QUEEN, CardSuit.SPADES)
    private val card4 = Card( CardValue.JACK,  CardSuit.HEARTS)
    private val card5 = Card( CardValue.TWO,  CardSuit.DIAMONDS)
    private val card6 = Card( CardValue.TWO,  CardSuit.DIAMONDS)
    private val card7 = Card( CardValue.JACK,  CardSuit.DIAMONDS)
    private val card8 = Card( CardValue.ACE, CardSuit.SPADES)
    private val card9 = Card( CardValue.THREE,  CardSuit.DIAMONDS)
    private val card10 = Card( CardValue.FOUR,  CardSuit.DIAMONDS)
    private val card11 = Card( CardValue.FIVE,  CardSuit.DIAMONDS)
    private val card12 = Card( CardValue.SIX, CardSuit.SPADES)
    private val card13 = Card( CardValue.SEVEN, CardSuit.SPADES)


    /**
     *  vergleiche zwischen zwei unterschiedlichen Karten
     */

    @Test
    fun testCard()
    {
        assertNotEquals(card1,card2)
        assertNotEquals(card2,card3)
        assertNotEquals(card3,card8)
        assertNotEquals(card7,card4)
        assertEquals(card5,card6)
        assertEquals(card2.number.toString(),CardValue.EIGHT.toString())
        assertEquals(card1.symbol.toString(),CardSuit.CLUBS.toString())
        assertEquals(card1.number.toString(),CardValue.ACE.toString())
        assertEquals(card3.number.toString(),CardValue.QUEEN.toString())
        assertEquals(card4.number.toString(),CardValue.JACK.toString())
        assertEquals(card5.number.toString(),CardValue.TWO.toString())
        assertEquals(card2.symbol.toString(),CardSuit.DIAMONDS.toString())
        assertEquals(card3.symbol.toString(),CardSuit.SPADES.toString())
        assertEquals(card4.symbol.toString(),CardSuit.HEARTS.toString())
        assertEquals(card10.number.toString(),CardValue.FOUR.toString())
        assertEquals(card9.number.toString(),CardValue.THREE.toString())
        assertEquals(card11.number.toString(),CardValue.FIVE.toString())
        assertEquals(card12.number.toString(),CardValue.SIX.toString())
        assertEquals(card13.number.toString(),CardValue.SEVEN.toString())


    }
}



