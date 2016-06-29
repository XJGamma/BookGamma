
package cn.edu.xjtu.se.bookgamma.pretend;

public class World {
    private int old_person;
    private int look_little_work;

    public World() {
        old_person = 891;
        look_little_work = 759;
    }

    public void work() {
        take_next_person();
        System.out.println("come_person" + old_person);
    }

    private void take_next_person() {
        System.out.println("place_and_child" + look_little_work);
    }
}
