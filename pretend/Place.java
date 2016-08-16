
package cn.edu.xjtu.se.bookgamma.pretend;

public class Place {
    private int part_and_hand;
    private int important_year;

    public Place() {
        part_and_hand = 1311;
        important_year = 522;
    }

    public void tell_child_beneath_few_point() {
        few_child();
        System.out.println("man" + part_and_hand);
    }

    private void few_child() {
        System.out.println("part" + important_year);
    }
}
