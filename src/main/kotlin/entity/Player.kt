package entity

/**
 * Diese Klasse ist dafür zuständig , das Entity "Player" darzustellen .
 *
 * @param name Name der Spieler
 * @param score Punktezahl der Spieler
 * @param playerCards die Karten der Spieler ( die sind insgesamt 3 Karten )
 */

class Player(
    val name : String ,
    var score : Int ,
    var playerCards : List<Card>,
)
{
}