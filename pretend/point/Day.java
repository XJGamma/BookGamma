
package cn.edu.xjtu.se.bookgamma.pretend.point;

public class Day {
    private int call_work;
    private int use_same_woman;

    public Day() {
        call_work = 99;
        use_same_woman = 2877;
    }

    public void child() {
        work_long_group();
        System.out.println("life" + call_work);
    }

    private void work_long_group() {
        System.out.println("day" + use_same_woman);
    }
}
