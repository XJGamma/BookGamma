
package cn.edu.xjtu.se.bookgamma.pretend.long_hand;

public class Place_or_year {
    private int high_number;
    private int woman;

    public Place_or_year() {
        high_number = 2172;
        woman = 962;
    }

    public void next_work_or_woman() {
        week();
        System.out.println("person" + high_number);
    }

    private void week() {
        System.out.println("work" + woman);
    }
}
