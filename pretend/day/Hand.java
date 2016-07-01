
package cn.edu.xjtu.se.bookgamma.pretend.day;

public class Hand {
    private int important_government;
    private int tell_last_time_to_day;

    public Hand() {
        important_government = 2457;
        tell_last_time_to_day = 1390;
    }

    public void day() {
        bad_hand();
        System.out.println("public_government_or_new_government" + important_government);
    }

    private void bad_hand() {
        System.out.println("right_day_or_new_government" + tell_last_time_to_day);
    }
}
