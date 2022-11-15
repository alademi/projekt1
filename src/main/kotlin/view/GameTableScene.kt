package view

import service.MainService
import entity.*
import service.CardImageLoader
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.util.Font

class GameTableScene(private val mainService: MainService): BoardGameScene(1920, 1080),Refreshable {
    val game = mainService.currentGame

    private fun Player.scoreString(): String =" ${this.name}"





    private val PassCounter = Label(
        width = 300, height = 50,
        posX = 200, posY = 800,
        text = "Pass Counter : 0",
        font = Font(size = 22)
    )

    private val hasPlayerKnocked = Label(
        width = 300, height = 50,
        posX = 200, posY = 840,
        text = "Knocked : No ",
        font = Font(size = 22)
    )



    private val PlayStack = LabeledStackView(posX = 200, posY = 500, "play stack")
    private val PlayerHand = LinearLayout<CardView>(  height = 220,
        width = 800,
        posX = 560,
        posY = 750,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50))

    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()

    init {
        background = ColorVisual(108, 168, 59)

        addComponents(
            PlayStack,
            PlayerHand,
            PassCounter,
            hasPlayerKnocked
        )
    }

    override fun refreshAfterStartNewGame() {
        val game = mainService.currentGame
        cardMap.clear()
        val cardImageLoader = CardImageLoader()

        initializeStackView(game!!.cardDeck.peekAll().toMutableList(), PlayStack, cardImageLoader)
//        initializeCardView(game.playerList[game.moveCount].playerCards, PlayerHand, cardImageLoader)
       // initializeCardView(game.middleCards, MidStack, cardImageLoader)
      //  Player.text = game.players[game.currentPlayer].scoreString()
      //  val nextPlayerText = rootService.gameService.getNextPlayer().scoreString()
      //  nextPlayer.text = "Next Player --> $nextPlayerText
    }

    private fun initializeStackView(stack: MutableList<Card>, stackView: LabeledStackView, cardImageLoader: CardImageLoader) {
        stackView.clear()
        stack.reversed().forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.symbol, card.number)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            stackView.add(cardView)
            cardMap.add(card to cardView)
        }
    }

    override fun refreshHandCards() {
        TODO("Not yet implemented")
    }

    override fun refreshPlayerLabel() {
        TODO("Not yet implemented")
    }

    override fun refreshMiddleCard() {
        TODO("Not yet implemented")
    }

    override fun refreshAfterMove() {
        TODO("Not yet implemented")
    }

    override fun refreshAfterGameEnd() {
        TODO("Not yet implemented")
    }

    override fun refreshGameSetting() {
        TODO("Not yet implemented")
    }

}