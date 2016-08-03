
package cn.edu.xjtu.se.bookgamma.pretend.public_life;

public class Company {
    private int work_old_world;
    private int go_place;

    public Company() {
        work_old_world = 770;
        go_place = 9;
    }

    public void part() {
        different_life();
        System.out.println("make_day" + work_old_world);
    }

    private void different_life() {
        System.out.println("say_hand_up_place" + go_place);
    }
}
