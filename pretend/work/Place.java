
package cn.edu.xjtu.se.bookgamma.pretend.work;

public class Place {
    private int old_time;
    private int feel_last_time;

    public Place() {
        old_time = 1429;
        feel_last_time = 956;
    }

    public void place() {
        large_eye();
        System.out.println("life" + old_time);
    }

    private void large_eye() {
        System.out.println("early_year_and_woman" + feel_last_time);
    }
}
