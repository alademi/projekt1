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
import tools.aqua.bgw.event.KeyCode
import tools.aqua.bgw.util.Font

class GameTableScene(private val mainService: MainService) : BoardGameScene(1920, 1080), Refreshable {


    private var playerCard: Card? = null
    private var middleCard: Card? = null
    private var flag = false


    private val playerName = Label(
        width = 300, height = 50,
        posX = 560, posY = 800,
        text = "Player name :",
        font = Font(size = 22)
    )

    private val middleCardsLabel = Label(
        width = 300, height = 50,
        posX = 560, posY = 300,
        text = "Middle Cards",
        font = Font(size = 22)
    )


    private val passButton = Button(
        width = 150, height = 50,
        posX = 1700, posY = 700,
        text = "Pass",
        font = Font(size = 22)
    ).apply {
        onMouseClicked = {
            mainService.playerActionService.pass()
        }
    }

    private val knockButton = Button(
        width = 150, height = 50,
        posX = 1700, posY = 760,
        text = "Knock",
        font = Font(size = 22)
    ).apply {
        onMouseClicked = { mainService.playerActionService.knock() }
    }

    private val swapButton = Button(
        width = 150, height = 50,
        posX = 1700, posY = 820,
        text = "Swap",
        font = Font(size = 22)
    ).apply {
        onMouseClicked = {
           selectPlayerCard()
        }
    }

    private val swapAllButton = Button(
        width = 150, height = 50,
        posX = 1700, posY = 880,
        text = "Swap all",
        font = Font(size = 22)
    ).apply {
        onMouseClicked = { mainService.playerActionService.changeAllCards() }
    }


    private val passCounter = Label(
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

    private val playStack = LabeledStackView(posX = 200, posY = 500, "play stack")

    private val currentPlayerHand = LinearLayout<CardView>(
        height = 220,
        width = 800,
        posX = 560,
        posY = 750,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private val middleCards = LinearLayout<CardView>(
        height = 220,
        width = 800,
        posX = 560,
        posY = 250,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()

    init {
        background = ColorVisual(108, 168, 59)

        addComponents(
            playStack,
            currentPlayerHand,
            passCounter,
            hasPlayerKnocked,
            passButton,
            knockButton,
            swapButton,
            swapAllButton,
            middleCards,
            playerName,
            middleCardsLabel
        )
    }


    private fun initializeStackView(
        stack: CardDeckStack,
        stackView: LabeledStackView,
        cardImageLoader: CardImageLoader
    ) {
        stackView.clear()
        stack.peekAll().reversed().forEach { card ->
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

    private fun initializeCardView(
        Cards: MutableList<Card>,
        view: LinearLayout<CardView>,
        cardImageLoader: CardImageLoader
    ) {
        view.clear()
        Cards.reversed().forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.symbol, card.number)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            cardView.showFront()
            view.add(cardView)
            cardMap.add(card to cardView)
        }

    }


    private fun selectPlayerCard() {

        val pCards = mainService.dealerService.getCurrentPlayer().playerCards
        flag = true

        if (flag) {
            onKeyPressed = { event ->
                when (event.keyCode) {
                    KeyCode.R -> {
                        playerCard = pCards[0]
                        selectMiddleCard()
                    }

                    KeyCode.M -> {
                        playerCard = pCards[1]
                        selectMiddleCard()
                    }

                    KeyCode.L -> {
                        playerCard = pCards[2]
                        selectMiddleCard()
                    }

                    else -> {
                        throw Exception("Error by choosing a player Card")
                    }
                }

            }
        }

    }

    private fun selectMiddleCard() {
        val mCards = mainService.dealerService.getMiddleCards()

        if (flag) {
            onKeyPressed = { event ->
                when (event.keyCode) {
                    KeyCode.R -> {
                        middleCard = mCards[0]
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                        flag = false
                    }

                    KeyCode.M -> {
                        middleCard = mCards[1]
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                        flag = false
                    }

                    KeyCode.L -> {
                        middleCard = mCards[2]
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                        flag = false
                    }

                    else -> {
                        throw Exception("Error by choosing a player Card")
                    }
                }

            }
        }
    }

    override fun refreshAfterStartNewGame() {
        val game = mainService.currentGame
        val currentPlayer = mainService.dealerService.getCurrentPlayer()
        checkNotNull(game) { "No started game found." }
        cardMap.clear()
        val cardImageLoader = CardImageLoader()
        initializeStackView(game.cardDeck, playStack, cardImageLoader)
        initializeCardView(currentPlayer.playerCards, currentPlayerHand, cardImageLoader)
        initializeCardView(game.middleCards, middleCards, cardImageLoader)
        playerName.text = "Player: ${game.playerList[game.moveCount].name}"

    }

    override fun refreshAfterMove() {
        val game = mainService.currentGame
        checkNotNull(game) { "No started game found" }
        val cardImageLoader = CardImageLoader()
        initializeStackView(game.cardDeck, playStack, cardImageLoader)
        passCounter.text = "Pass Counter : ${game.passCount}"
        if (mainService.dealerService.getPreviousPlayer().hasPlayerKnocked) {
            hasPlayerKnocked.text = "Knocked : Yes"
        }

    }

    override fun refreshHandCards() {
        val game = mainService.currentGame
        val currentPlayer = mainService.dealerService.getCurrentPlayer()
        checkNotNull(game) { "No started game found" }
        val cardImageLoader = CardImageLoader()
        initializeCardView(currentPlayer.playerCards, currentPlayerHand, cardImageLoader)
    }

    override fun refreshPlayerLabel() {
        val game = mainService.currentGame
        val currentPlayer = mainService.dealerService.getCurrentPlayer()
        checkNotNull(game) { "No started game found" }
        playerName.text = "Player: ${currentPlayer.name}"
    }

    override fun refreshMiddleCard() {
        val game = mainService.currentGame
        checkNotNull(game) { "No started game found" }
        val cardImageLoader = CardImageLoader()
        initializeCardView(game.middleCards, middleCards, cardImageLoader)
    }


    override fun refreshAfterGameEnd() {

    }


}