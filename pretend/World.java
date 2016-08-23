
package cn.edu.xjtu.se.bookgamma.pretend;

public class World {
    private int able_woman;
    private int big_year;

    public World() {
        able_woman = 3519;
        big_year = 15;
    }

    public void child() {
        call_bad_child();
        System.out.println("young_world_or_work" + able_woman);
    }

    private void call_bad_child() {
        System.out.println("woman_or_first_year" + big_year);
    }
}
