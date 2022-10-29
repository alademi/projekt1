package entity

import entity.Card
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


/**
 * Testfälle für die Klasse [Player]
 */

class PlayerTest {

    /**
     *  Hier werden einige Spieler instanziiert , um den Test durchzuführen
     */

    private val card1 = Card( CardValue.ACE,  CardSuit.CLUBS)
    private val card2 = Card(CardValue.EIGHT,  CardSuit.DIAMONDS)
    private val card3 = Card( CardValue.QUEEN, CardSuit.SPADES)
    private val card4 = Card( CardValue.JACK,  CardSuit.HEARTS)
    private val card5 = Card( CardValue.TWO,  CardSuit.DIAMONDS)

    private val listCards1 = listOf<Card>(card1, card2, card3)
    private val listCards2 = listOf<Card>(card1, card3, card5)
    private val listCards3 = listOf<Card>(card1, card2, card4)


    private val player1 = Player("Abdul", 20.0, listCards1)
    private val player2 = Player("Abdul", 0.0, listCards2)
    private val player3 = Player("Jack", 30.5 ,listCards1)
    private val player4 = Player("Kim", 20.0, listCards3)
    private val player5 = Player("Tom", 20.0, listCards1)


    /**
     * vergleiche zwischen zwei unterschiedlichen Spieler
     */
    @Test
    fun playerTest()
    {
        assertNotEquals(player1,player2)
        assertNotEquals(player1,player3)
        assertNotEquals(player1,player4)
        assertNotEquals(player1,player5)
        assertEquals(player1.name,player2.name)
        assertEquals(player1.score,player4.score)
        assertEquals(player1.playerCards,player5.playerCards)



    }
}