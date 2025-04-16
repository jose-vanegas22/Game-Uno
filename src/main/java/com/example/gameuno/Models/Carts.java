package com.example.gameuno.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carts {

    static String IMAGES_PATH = "/com/example/gameuno/Images/Cards-uno/";
    //String cart;

    private static final String[] NOMBRES_CARTAS = {
            "0_blue.png", "0_green.png", "0_red.png", "0_yellow.png",
            "1_blue.png", "1_green.png", "1_red.png", "1_yellow.png",
            "2_blue.png", "2_green.png", "2_red.png", "2_wild_draw_blue.png",
            "2_wild_draw_green.png", "2_wild_draw_red.png", "2_wild_draw_yellow.png", "2_yellow.png",
            "3_blue.png", "3_green.png", "3_red.png", "3_yellow.png",
            "4_blue.png", "4_green.png", "4_red.png", "4_wild_draw.png", "4_yellow.png",
            "5_blue.png", "5_green.png", "5_red.png", "5_yellow.png",
            "6_blue.png", "6_green.png", "6_red.png", "6_yellow.png",
            "7_blue.png", "7_green.png", "7_red.png", "7_yellow.png",
            "8_blue.png", "8_green.png", "8_red.png", "8_yellow.png",
            "9_blue.png", "9_green.png", "9_red.png", "9_yellow.png",
            "card_uno.png", "deck_of_cards.png",
            "reserve_blue.png", "reserve_green.png", "reserve_red.png", "reserve_yellow.png",
            "skip_blue.png", "skip_green.png", "skip_red.png", "skip_yellow.png",
            "wild.png"
    };

    /**
    public Carts() {
        this.cart = randomCart();
    }


    private String randomCart(){
        Random random = new Random();
        int  chosenCart = random.nextInt(NOMBRES_CARTAS.length);
        return NOMBRES_CARTAS[chosenCart];
    }

    public String getRandomCart(){
        return IMAGES_PATH + cart;
    }
     **/



    public static List<String> getRandomCards(int number) {
        Random random = new Random();
        List<String> randomCards = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int chosenCart = random.nextInt(NOMBRES_CARTAS.length);
            randomCards.add(IMAGES_PATH + NOMBRES_CARTAS[chosenCart]);
        }
        return randomCards;
    }


}
