package ch17containers;

public class SpringDetector2 {
    public static void main(String[] args) throws Exception {
        SpringDetector.detectSpring(Groundhog2.class);
    }
}/* Output
Map {Groundhog #0=Early Spring!, Groundhog #1=Early Spring!, Groundhog #2=Six more weeks of Winter!, Groundhog #3=Early Spring!, Groundhog #4=Six more weeks of Winter!, Groundhog #5=Early Spring!, Groundhog #6=Six more weeks of Winter!, Groundhog #7=Six more weeks of Winter!, Groundhog #8=Six more weeks of Winter!, Groundhog #9=Early Spring!}
Looking up prediction for Groundhog #3
Early Spring!
*/