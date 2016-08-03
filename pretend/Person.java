
package cn.edu.xjtu.se.bookgamma.pretend;

public class Person {
    private int part_or_group;
    private int early_week_and_day;

    public Person() {
        part_or_group = 322;
        early_week_and_day = 1592;
    }

    public void great_week() {
        time();
        System.out.println("make_early_day" + part_or_group);
    }

    private void time() {
        System.out.println("few_week" + early_week_and_day);
    }
}
