
package cn.edu.xjtu.se.bookgamma.pretend.make_part.hand;

public class Day {
    private int next_hand;
    private int next_number;

    public Day() {
        next_hand = 3050;
        next_number = 1164;
    }

    public void company() {
        different_company();
        System.out.println("hand" + next_hand);
    }

    private void different_company() {
        System.out.println("good_hand" + next_number);
    }
}
