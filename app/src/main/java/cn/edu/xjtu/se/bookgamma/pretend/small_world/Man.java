
package cn.edu.xjtu.se.bookgamma.pretend.small_world;

public class Man {
    private int company;
    private int few_group;

    public Man() {
        company = 1238;
        few_group = 279;
    }

    public void right_man() {
        think_important_life();
        System.out.println("place" + company);
    }

    private void think_important_life() {
        System.out.println("feel_point" + few_group);
    }
}
