
package cn.edu.xjtu.se.bookgamma.pretend.way.point.new_work;

public class Year {
    private int year;
    private int year_and_government;

    public Year() {
        year = 3139;
        year_and_government = 1481;
    }

    public void last_year() {
        government();
        System.out.println("large_company_and_important_work" + year);
    }

    private void government() {
        System.out.println("person_and_right_place" + year_and_government);
    }
}
