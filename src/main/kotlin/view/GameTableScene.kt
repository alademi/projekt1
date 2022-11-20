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
import java.awt.Color
import kotlin.system.exitProcess

class GameTableScene(private val mainService: MainService) : BoardGameScene(1920, 1080, ColorVisual.CYAN), Refreshable {


    private var playerCard: Card? = null
    private var middleCard: Card? = null
    private var flag = false
    private val game = mainService.currentGame


    private val playerName1 = Label(
        width = 200, height = 50,
        posX = 850, posY = 690,
        font = Font(size = 22),
        visual = ImageVisual("GameTable_P_Name_Label.png")
    )

    private val playerName2 = Label(
        width = 200, height = 50,
        posX = 850, posY = 280,
        font = Font(size = 22),
        visual = ImageVisual("GameTable_P_Name_Label.png")
    )


    private val playerName3 = Label(
        width = 200, height = 50,
        posX = 1250, posY = 510,
        font = Font(size = 22),
        visual = ImageVisual("GameTable_P_Name_Label.png")
    ).apply {
        rotation = 90.0
    }


    private val playerName4 = Label(
        width = 200, height = 50,
        posX = 400, posY = 510,
        font = Font(size = 22),
        visual = ImageVisual("GameTable_P_Name_Label.png")
    ).apply {
        rotation = -90.0
    }


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
        font = Font(size = 22),
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
            //  selectPlayerCard()
            swapVisual()
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
        posX = 290, posY = 800,
        text = "Pass Counter : 0",
        font = Font(
            fontWeight = Font.FontWeight.BOLD,
            fontStyle = Font.FontStyle.NORMAL,
            size = 15,
            color = Color.white
        ),
        visual = ColorVisual(Color(15, 56, 103))
    )

    private val hasPlayerKnocked = Label(
        width = 200, height = 50,
        posX = 290, posY = 870,
        text = "Knocked : No ",
        font = Font(
            fontWeight = Font.FontWeight.BOLD,
            fontStyle = Font.FontStyle.NORMAL,
            size = 15,
            color = Color.white
        ),
        visual = ColorVisual(Color(227, 23, 54))
    )

    private val playStack = LabeledStackView(posX = 620, posY = 400, "play stack")

    private val currentPlayerHand1 = LinearLayout<CardView>(
        height = 220,
        width = 500,
        posX = 720,
        posY = 750,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private val currentPlayerHand2 = LinearLayout<CardView>(
        height = 220,
        width = 500,
        posX = 720,
        posY = 50,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private val currentPlayerHand3 = LinearLayout<CardView>(
        height = 220,
        width = 500,
        posX = 1250,
        posY = 400,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply { rotation = -90.0 }

    private val currentPlayerHand4 = LinearLayout<CardView>(
        height = 220,
        width = 500,
        posX = 100,
        posY = 400,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply { rotation = 90.0 }

    private val selectPlayerCardLabel = Label(
        width = 600, height = 50,
        posX = 800, posY = 700,
        text = "press L : left card , M : Middle card , R : right card",
        font = Font(size = 22)
    )

    private val middleCards = LinearLayout<CardView>(
        height = 200,
        width = 295,
        posX = 800,
        posY = 400,
        spacing = -50,
        // alignment = Alignment.CENTER,
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

        background = ImageVisual("StartGame_Bg.png")

        addComponents(
            playStack,
            currentPlayerHand1,
            passCounter,
            hasPlayerKnocked,
            passButton,
            knockButton,
            swapButton,
            swapAllButton,
            middleCards,
            playerName1,
            playerName2,
            currentPlayerHand2,
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
            if (Cards == mainService.dealerService.getCurrentPlayer().playerCards || Cards == mainService.dealerService.getMiddleCards()) {
                cardView.showFront()
            }
            view.add(cardView)
            cardMap.add(card to cardView)
        }

    }

    private fun moveCardView(
        cardView: CardView,
        linearView: LinearLayout<CardView>,
        flip: Boolean = false
    ) {
        if (flip) {
            when (cardView.currentSide) {
                CardView.CardSide.BACK -> cardView.showFront()
                CardView.CardSide.FRONT -> cardView.showBack()
            }
        }

        cardView.removeFromParent()
        linearView.add(cardView)
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


    private fun swapVisual() {


        val pCards = mainService.dealerService.getCurrentPlayer().playerCards
        val pView1 = cardMap.forward(pCards[0])
        val pView2 = cardMap.forward(pCards[1])
        val pView3 = cardMap.forward(pCards[2])
        val mCards = mainService.dealerService.getMiddleCards()
        println(mCards)
        val mView2 = cardMap.forward(mCards[1])
        val mView3 = cardMap.forward(mCards[2])
        val mView1 = cardMap.forward(mCards[0])



        pView1.apply {
            onMouseClicked = {
                playerCard = cardMap.backward(pView1)
                mView1.apply {
                    onMouseClicked = {
                        middleCard = cardMap.backward(mView1)
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                    }
                }
                mView2.apply {
                    onMouseClicked = {
                        middleCard = cardMap.backward(mView2)
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                    }
                }
                mView3.apply {
                    onMouseClicked = {
                        middleCard = cardMap.backward(mView3)
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                    }
                }

            }
        }
        pView2.apply {
            onMouseClicked = {
                playerCard = cardMap.backward(pView2)
                mView1.apply {
                    onMouseClicked = {
                        middleCard = cardMap.backward(mView1)
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                    }
                }
                mView2.apply {
                    onMouseClicked = {
                        middleCard = cardMap.backward(mView2)
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                    }
                }
                mView3.apply {
                    onMouseClicked = {
                        middleCard = cardMap.backward(mView3)
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                    }
                }

            }
        }

        pView3.apply {
            onMouseClicked = {
                playerCard = cardMap.backward(pView3)
                mView1.apply {
                    onMouseClicked = {
                        middleCard = cardMap.backward(mView1)
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                    }
                }
                mView2.apply {
                    onMouseClicked = {
                        middleCard = cardMap.backward(mView2)
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
                    }
                }
                mView3.apply {
                    onMouseClicked = {
                        middleCard = cardMap.backward(mView3)
                        mainService.playerActionService.changeSingleCard(playerCard!!, middleCard!!)
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
        initializeCardView(currentPlayer.playerCards, currentPlayerHand1, cardImageLoader)
        initializeCardView(game.middleCards, middleCards, cardImageLoader)
        initializeCardView(game.playerList[1].playerCards, currentPlayerHand2, cardImageLoader)
        playerName1.text = game.playerList[0].name
        playerName2.text = game.playerList[1].name

        if (game.playerList.size == 3) {
            addComponents(currentPlayerHand3)
            addComponents(playerName3)
            initializeCardView(game.playerList[2].playerCards, currentPlayerHand3, cardImageLoader)
            playerName3.text = game.playerList[2].name
        }

        if (game.playerList.size == 4) {
            addComponents(currentPlayerHand3)
            initializeCardView(game.playerList[2].playerCards, currentPlayerHand3, cardImageLoader)
            addComponents(playerName3)
            addComponents(currentPlayerHand4)
            initializeCardView(game.playerList[3].playerCards, currentPlayerHand4, cardImageLoader)
            addComponents(playerName4)
            playerName3.text = game.playerList[2].name
            playerName4.text = game.playerList[3].name

        }


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
        checkNotNull(game) { "No started game found" }

        val cardImageLoader = CardImageLoader()



    initializeCardView(game.playerList[0].playerCards, currentPlayerHand1, cardImageLoader)

    initializeCardView(game.playerList[1].playerCards, currentPlayerHand2, cardImageLoader)


    if (game.playerList.size == 3) {
        initializeCardView(game.playerList[2].playerCards, currentPlayerHand3, cardImageLoader)
    }
    if (game.playerList.size == 4) {
        initializeCardView(game.playerList[2].playerCards, currentPlayerHand3, cardImageLoader)
        initializeCardView(game.playerList[3].playerCards, currentPlayerHand4, cardImageLoader)
    }

    }


    override fun refreshPlayerLabel() {
        val game = mainService.currentGame
        checkNotNull(game) { "No started game found" }
    }

    override fun refreshMiddleCard() {
        cardMap.clear()
        val game = mainService.currentGame
        checkNotNull(game) { "No started game found" }
        val cardImageLoader = CardImageLoader()
        initializeCardView(game.middleCards, middleCards, cardImageLoader)

    }


    override fun refreshAfterGameEnd() {

    }




}
