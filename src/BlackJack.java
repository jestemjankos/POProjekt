import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJack {
    private class Card {
        String value;
        String type;

        Card(String value, String type) {
            this.value = value;
            this.type = type;
        }

        public String toString() {
            return value + "-" + type;
        }

        public int getValue() {
            if ("AJQK".contains(value)) {
                if (value.equals("A")) {
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(value);
        }

        public boolean isAce() {
            return value.equals("A");
        }

        public String getImagePath() {
            return "./karty/" + toString() + ".png";
        }
    }

    ArrayList<Card> deck;
    Random random = new Random();

    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    int boardWidth = 600;
    int boardHeight = boardWidth;

    int cardWidth = 110;
    int cardHeight = 154;

    boolean gameStarted = false;
    boolean gameEnded = false;
    boolean playerCardsRevealed = false;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                Image backImg = new ImageIcon(getClass().getResource("./karty/BACK.png")).getImage();

                // Pierwsza karta krupiera (widoczna po kliknięciu Zagraj)
                if (playerCardsRevealed) {
                    Card visibleCard = dealerHand.get(0);
                    Image cardImg = new ImageIcon(getClass().getResource(visibleCard.getImagePath())).getImage();
                    g.drawImage(cardImg, 20, 20, cardWidth, cardHeight, null);
                } else {
                    g.drawImage(backImg, 20, 20, cardWidth, cardHeight, null);
                }

                // Druga karta krupiera (ukryta do końca gry)
                if (gameEnded && playerCardsRevealed) {
                    Image hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
                    g.drawImage(hiddenCardImg, cardWidth + 25, 20, cardWidth, cardHeight, null);
                } else {
                    g.drawImage(backImg, cardWidth + 25, 20, cardWidth, cardHeight, null);
                }

                // Dodatkowe karty krupiera
                for (int i = 1; i < dealerHand.size(); i++) {
                    if (gameEnded) {
                        Card card = dealerHand.get(i);
                        Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                        g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
                    } else {
                        g.drawImage(backImg, cardWidth + 25 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
                    }
                }

                // Karty gracza
                for (int i = 0; i < playerHand.size(); i++) {
                    if (playerCardsRevealed) {
                        Card card = playerHand.get(i);
                        Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                        g.drawImage(cardImg, 20 + (cardWidth + 5) * i, 320, cardWidth, cardHeight, null);
                    } else {
                        g.drawImage(backImg, 20 + (cardWidth + 5) * i, 320, cardWidth, cardHeight, null);
                    }
                }

                // Game results
                if (gameEnded) {
                    String message = "";
                    if (playerSum > 21) {
                        message = "You Lose!";
                    } else if (!stayButton.isEnabled()) {
                        dealerSum = reduceDealerAce();
                        playerSum = reducePlayerAce();

                        if (dealerSum > 21) {
                            message = "You Win!";
                        } else if (playerSum == dealerSum) {
                            message = "Tie!";
                        } else if (playerSum > dealerSum) {
                            message = "You Win!";
                        } else if (playerSum < dealerSum) {
                            message = "You Lose!";
                        }
                    }

                    if (!message.isEmpty()) {
                        g.setFont(new Font("Arial", Font.PLAIN, 30));
                        g.setColor(Color.white);
                        g.drawString(message, 220, 250);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    JPanel buttonPanel = new JPanel();
    JButton playButton = new JButton("Zagraj");
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton replayButton = new JButton("Play Again");
    JButton wrocButton = new JButton("Wróć do kasyna");

    BlackJack() {
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        buttonPanel.add(replayButton);
        buttonPanel.add(wrocButton);
        buttonPanel.add(playButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        replayButton.setEnabled(false);
        wrocButton.setEnabled(false);
        playButton.setEnabled(true);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size() - 1);
                playerHand.add(card);
                playerSum += card.getValue();
                playerAceCount += card.isAce() ? 1 : 0;
                playerSum = reducePlayerAce();

                if (playerSum > 21) {
                    hitButton.setEnabled(false);
                    stayButton.setEnabled(false);
                    replayButton.setEnabled(true);
                    wrocButton.setEnabled(true);
                    gameEnded = true;
                }
                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                replayButton.setEnabled(true);
                wrocButton.setEnabled(true);

                while (dealerSum < 17) {
                    Card card = deck.remove(deck.size() - 1);
                    dealerHand.add(card);
                    dealerSum += card.getValue();
                    dealerAceCount += card.isAce() ? 1 : 0;
                    dealerSum = reduceDealerAce();
                }

                gameEnded = true;
                gamePanel.repaint();
            }
        });

        replayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
                gamePanel.repaint();
            }
        });

        wrocButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new mainMenu();
                frame.dispose();
            }
        });

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStarted = true;
                playerCardsRevealed = true;
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                playButton.setEnabled(false);
                replayButton.setEnabled(false);
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();
    }

    public void startGame() {
        buildDeck();
        shuffleDeck();

        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        // Pierwsza karta krupiera (będzie widoczna)
        Card visibleCard = deck.remove(deck.size() - 1);
        dealerSum += visibleCard.getValue();
        dealerAceCount += visibleCard.isAce() ? 1 : 0;
        dealerHand.add(visibleCard);

        // Druga karta krupiera (ukryta)
        hiddenCard = deck.remove(deck.size() - 1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            Card card = deck.remove(deck.size() - 1);
            playerHand.add(card);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
        }

        gameStarted = false;
        gameEnded = false;
        playerCardsRevealed = false;
    }

    public void resetGame() {
        startGame();
        playButton.setEnabled(true);
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        replayButton.setEnabled(false);
        wrocButton.setEnabled(true);
    }

    public void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }
    }

    public int reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }
}