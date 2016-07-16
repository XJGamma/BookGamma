
package cn.edu.xjtu.se.bookgamma.pretend.eye;

public class Thing {
    private int same_week_and_place;
    private int group;

    public Thing() {
        same_week_and_place = 2176;
        group = 531;
    }

    public void life() {
        last_person();
        System.out.println("say_long_world_under_small_week" + same_week_and_place);
    }

    private void last_person() {
        System.out.println("fact" + group);
    }
}
