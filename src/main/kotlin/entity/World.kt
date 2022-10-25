package entity

class World (
    var middleCards : List<Card> ,
    var moveCount : Int = 0 ,
    var passCount : Int = 0 ,
    var hasPlayerKnocked : Boolean = false,
    val players : List<Player>,
    var cardDeck: Array<Card?> = arrayOfNulls<Card>(32)
    )
{

}