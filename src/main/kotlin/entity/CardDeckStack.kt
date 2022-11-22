package entity


import kotlin.random.Random

/**
 * Diese Klasse basiert hauptsächlich auf das [Beispiel-War]
 */

class CardDeckStack(private val random: Random = Random) {

    /**
     * Data structure that holds [Card] objects and provides stack-like
     * access to them (with e.g. [putOnTop], [draw]).
     *
     * @param [random] can be provided to ensure deterministic behavior of [shuffle]
     */


    /**
     * The actual backing data structure. As there is no dedicated stack implementation
     * in Kotlin, a "double-ended queue" (Deque) is used.
     */
    private val cards: ArrayDeque<Card> = ArrayDeque(32)

    /**
     * the amount of cards in this stack
     */
    val size: Int get() = cards.size


    /**
     * Shuffles the cards in this stack
     */
    fun shuffle() {
        cards.shuffle(random)
    }

    /**
     * Draws [amount] cards from this stack.
     *
     * @param amount the number of cards to draw; defaults to 1 if omitted.
     *
     * @throws IllegalArgumentException if not enough cards on stack to draw the desired amount.
     */
    fun draw(amount: Int = 1): MutableList<Card> {
        require(amount in 1..cards.size) { "can't draw $amount cards from $cards" }
        return MutableList(amount) { cards.removeFirst() }
    }



    fun peekAll(): List<Card> = cards.toList()


    /**
     * puts a given list of cards on top of this card stack, so that
     * the last element of the passed parameter [cards] will be on top of
     * the stack afterwards.
     *
     */
    fun putOnTop(cards: List<Card>) {
        cards.forEach(this.cards::addFirst)
    }

    /**
     * puts the given card on top of this card stack
     */


    override fun toString(): String = cards.toString()
}

