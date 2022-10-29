package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Testfälle für die Klasse [Welt]
 */
class WorldTest {

    /**
     * Hier werden einige Welten instanziiert , um den Test durchzuführen
     */

    private val card1 = Card( CardValue.ACE,  CardSuit.CLUBS)
    private val card2 = Card(CardValue.EIGHT,  CardSuit.DIAMONDS)
    private val card3 = Card( CardValue.QUEEN, CardSuit.SPADES)

    private val listCards1 = listOf<Card>(card1, card2, card3)
    private val listCards2 = listOf<Card>(card1, card3, card3)

    private val player1 = Player("Abdul", 20.0, listCards1)
    private val player2 = Player("Tom", 0.0, listCards2)
    private val player3 = Player("Jack", 30.5 ,listCards1)
    private val player4 = Player("Kim", 20.0, listCards2)

    private val playerList1 = listOf<Player>(player1,player2,player3)
    private val playerList2 = listOf<Player>(player1,player2,player4)

    private val world1 = World(listCards1,0,0,false,playerList1,listCards1+listCards2)
    private val world2 = World(listCards1,0,0,false,playerList1,listCards1+listCards2)
    private val world3 = World(listCards2,0,0,true,playerList2,listCards2+listCards2)


    /**
     * vergeleiche zwischen zwei unterschiedlichen Welten
     */

    @Test
    fun testWorld()
    {
        assertEquals(world1,world2)
        assertNotEquals(world1,world3)
    }

}