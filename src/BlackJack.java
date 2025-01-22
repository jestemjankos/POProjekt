import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJack {
    private final Deck deck;
    private final DealerHand dealerHand;
    private final PlayerHand playerHand;

    private final int boardWidth = 600;
    private final int boardHeight = boardWidth;
    private final int cardWidth = 110;
    private final int cardHeight = 154;

    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private boolean playerCardsRevealed = false;

    private final JFrame frame;
    private final JPanel gamePanel;
    private final JButton playButton;
    private final JButton hitButton;
    private final JButton stayButton;
    private final JButton replayButton;
    private final JButton wrocButton;

    public BlackJack() {
        deck = new Deck();
        dealerHand = new DealerHand();
        playerHand = new PlayerHand();

        frame = new JFrame("Black Jack");
        gamePanel = createGamePanel();

        // Initialize buttons
        playButton = new JButton("Zagraj");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        replayButton = new JButton("Play Again");
        wrocButton = new JButton("Wróć do kasyna");

        setupUI();
        setupButtonListeners();
        startGame();
    }

    private JPanel createGamePanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGame(g);
            }
        };
    }

    private void drawGame(Graphics g) {
        try {
            Image backImg = new ImageIcon(getClass().getResource("./karty/BACK.png")).getImage();

            // Draw dealer's cards
            drawDealerCards(g, backImg);

            // Draw player's cards
            drawPlayerCards(g, backImg);

            // Draw game results
            if (gameEnded) {
                drawGameResults(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawDealerCards(Graphics g, Image backImg) {
        // Drawing logic for dealer's cards
        // (Implementation similar to original, but using dealerHand)

        // Pierwsza karta krupiera (widoczna po kliknięciu Zagraj)
        int xOffset = 20;
        if (playerCardsRevealed) {
            Card visibleCard = dealerHand.getCards().get(0);
            Image cardImg = new ImageIcon(getClass().getResource(visibleCard.getImagePath())).getImage();
            g.drawImage(cardImg, xOffset, 20, cardWidth, cardHeight, null);
        } else {
            g.drawImage(backImg, xOffset, 20, cardWidth, cardHeight, null);
        }

        // Druga karta krupiera (ukryta do końca gry)
        xOffset = cardWidth + 25;
        if (gameEnded && playerCardsRevealed) {
            Card hiddenCard = dealerHand.getHiddenCard();
            Image hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
            g.drawImage(hiddenCardImg, xOffset, 20, cardWidth, cardHeight, null);
        } else {
            g.drawImage(backImg, xOffset, 20, cardWidth, cardHeight, null);
        }

        // Dodatkowe karty krupiera
        for (int i = 1; i < dealerHand.getCards().size(); i++) {
            xOffset = cardWidth + 25 + (cardWidth + 5) * i;
            if (gameEnded) {
                Card card = dealerHand.getCards().get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImg, xOffset, 20, cardWidth, cardHeight, null);
            } else {
                g.drawImage(backImg, xOffset, 20, cardWidth, cardHeight, null);
            }
        }
    }

    private void drawPlayerCards(Graphics g, Image backImg) {
        // Drawing logic for player's cards
        // (Implementation similar to original, but using playerHand)

        for (int i = 0; i < playerHand.getCards().size(); i++) {
            int xOffset = 20 + (cardWidth + 5) * i;
            if (playerCardsRevealed) {
                Card card = playerHand.getCards().get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImg, xOffset, 320, cardWidth, cardHeight, null);
            } else {
                g.drawImage(backImg, xOffset, 320, cardWidth, cardHeight, null);
            }
        }
    }

    private void drawGameResults(Graphics g) {
        // Drawing logic for game results
        // (Implementation similar to original)

        String message = "";

        if (playerHand.isBust()) {
            message = "You Lose!";
        } else if (!stayButton.isEnabled()) {
            int dealerFinalSum = dealerHand.getSum();
            int playerFinalSum = playerHand.getSum();

            if (dealerFinalSum > 21) {
                message = "You Win!";
            } else if (playerFinalSum == dealerFinalSum) {
                message = "Tie!";
            } else if (playerFinalSum > dealerFinalSum) {
                message = "You Win!";
            } else {
                message = "You Lose!";
            }
        }

        if (!message.isEmpty()) {
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.setColor(Color.white);
            g.drawString(message, 220, 250);
        }
    }

    private void setupUI() {
        // UI setup logic
        // (Implementation similar to original)

        // Konfiguracja głównego okna
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Konfiguracja panelu gry
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        // Konfiguracja panelu przycisków
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        buttonPanel.add(replayButton);
        buttonPanel.add(wrocButton);
        buttonPanel.add(playButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Ustawienie początkowego stanu przycisków
        updateButtonStates(false);
    }

    private void setupButtonListeners() {
        hitButton.addActionListener(e -> handleHit());
        stayButton.addActionListener(e -> handleStay());
        replayButton.addActionListener(e -> handleReplay());
        wrocButton.addActionListener(e -> handleWroc());
        playButton.addActionListener(e -> handlePlay());
    }

    private void handleHit() {
        Card card = deck.drawCard();
        playerHand.addCard(card);

        if (playerHand.isBust() || playerHand.hasBlackjack()) {
            endGame();
        }
        gamePanel.repaint();
    }

    private void handleStay() {
        while (dealerHand.shouldDraw(playerHand.getSum())) {
            dealerHand.addCard(deck.drawCard());
        }
        endGame();
    }

    private void handleReplay() {
        resetGame();
        gamePanel.repaint();
    }

    private void handleWroc() {
        new mainMenu();
        frame.dispose();
    }

    private void handlePlay() {
        gameStarted = true;
        playerCardsRevealed = true;
        updateButtonStates(true);
        gamePanel.repaint();
    }

    private void startGame() {
        deck.reset();
        dealerHand.reset();
        playerHand.reset();

        // Deal initial cards
        dealerHand.addCard(deck.drawCard());
        dealerHand.setHiddenCard(deck.drawCard());

        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());

        gameStarted = false;
        gameEnded = false;
        playerCardsRevealed = false;

        updateButtonStates(false);
    }

    private void resetGame() {
        startGame();
        updateButtonStates(false);
    }

    private void endGame() {
        gameEnded = true;
        updateButtonStates(false);
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        replayButton.setEnabled(true);
        wrocButton.setEnabled(true);
        gamePanel.repaint();
        playButton.setEnabled(false);
    }

    private void updateButtonStates(boolean duringGame) {
        hitButton.setEnabled(duringGame);
        stayButton.setEnabled(duringGame);
        playButton.setEnabled(!duringGame);
        replayButton.setEnabled(!duringGame);
        wrocButton.setEnabled(true);
    }
}