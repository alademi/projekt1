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
import kotlin.system.exitProcess

class GameTableScene(private val mainService: MainService) : BoardGameScene(1920, 1080  ), Refreshable {


    private var playerCard: Card? = null
    private var middleCard: Card? = null
    private var flag = false


    private val playerName = Label(
        width = 200, height = 50,
        posX = 850, posY = 690,
        text = "Player name :",
        font = Font(size = 22) ,
        visual = ImageVisual("GameTable_P_Name_Label.png")
    )

    private val middleCardsLabel = Label(
        width = 200, height = 50,
        posX = 850, posY = 190,
        text = "Middle Cards",
        font = Font(size = 22),
        visual = ImageVisual("GameTable_P_Name_Label.png")
    )


    private val passButton = Button(
        width = 150, height = 50,
        posX = 1530, posY = 820,
        font = Font(size = 22),
        visual = ImageVisual("GameTable_Button_Pass.png")

    ).apply {
        onMouseClicked = {
            mainService.playerActionService.pass()
        }
    }

    private val knockButton = Button(
        width = 150, height = 50,
        posX = 1530, posY = 880,
      // text = "Knock",
        font = Font(size = 22) ,
        visual = ImageVisual("GameTable_Button_Knock.png")
    ).apply {
        onMouseClicked = { mainService.playerActionService.knock() }
    }

    private val swapButton = Button(
        width = 150, height = 50,
        posX = 1700, posY = 820,
        //text = "Swap",
        font = Font(size = 22),
        visual = ImageVisual("GameTable_Button_SwapOne.png")
    ).apply {
        onMouseClicked = {
           // selectPlayerCard()
        }
    }

    private val swapAllButton = Button(
        width = 150, height = 50,
        posX = 1700, posY = 880,
        //text = "Swap all",
        font = Font(size = 22),
        visual = ImageVisual("GameTable_Button_SwapAll.png")
    ).apply {
        onMouseClicked = { mainService.playerActionService.changeAllCards() }
    }


    private val passCounter = Label(
        width = 200, height = 50,
        posX = 200, posY = 800,
        text = "Pass Counter : 0",
        font = Font(size = 22),
        visual = ImageVisual("GameTable_P_Name_Label.png")
    )

    private val hasPlayerKnocked = Label(
        width = 200, height = 50,
        posX = 200, posY = 860,
        text = "Knocked : No ",
        font = Font(size = 22),
        visual = ImageVisual("GameTable_P_Name_Label.png")
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

    private val selectPlayerCardLabel = Label(
        width = 600, height = 50,
        posX = 800, posY = 700,
        text = "press L : left card , M : Middle card , R : right card",
        font = Font(size = 22)
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

    private val selectMiddleCardLabel = Label(
        width = 600, height = 50,
        posX = 800, posY = 200,
        text = "press L : left card , M : Middle card , R : right card",
        font = Font(size = 22)
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

        addComponents(selectPlayerCardLabel)
        onKeyPressed = { event ->
            when (event.keyCode) {
                KeyCode.R -> {
                    playerCard = pCards[0]
                    println(playerCard)
                    println(flag)
                }

                KeyCode.M -> {
                    playerCard = pCards[1]
                    println(playerCard)
                    println(flag)
                }

                KeyCode.L -> {
                    playerCard = pCards[2]
                    println(playerCard)
                    selectMiddleCard()
                }

                else -> {
                    throw Exception("Error by choosing a player Card")
                }
            }

        }
    }


    private fun selectMiddleCard() {
        val mCards = mainService.dealerService.getMiddleCards()
        removeComponents(selectPlayerCardLabel)
        addComponents(selectMiddleCardLabel)
        onKeyPressed = { event ->
            when (event.keyCode) {
                KeyCode.R -> {
                    middleCard = mCards[0]
                    swapVisual()
                }


                KeyCode.M -> {
                    middleCard = mCards[1]
                    swapVisual()
                }

                KeyCode.L -> {
                    middleCard = mCards[2]
                    swapVisual()
                }

                else -> {
                    throw Exception("Error by choosing a player Card")
                }
            }

        }
    }

    private fun swapVisual()
    {

        mainService.playerActionService.changeSingleCard(playerCard!!,middleCard!!)
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
        playerName.text = game.playerList[game.moveCount].name

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
        playerName.text = currentPlayer.name
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