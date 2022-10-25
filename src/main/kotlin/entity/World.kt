package entity

class World (
    val middleCards : List<Card> ,
    val moveCount : Int = 0 ,
    val passCount : Int = 0 ,
    val hasPlayerKnocked : Boolean = false,
    val players : List<Player>,
    val cardDeck: Array<Card?> = arrayOfNulls<Card>(32)
    )
{

}