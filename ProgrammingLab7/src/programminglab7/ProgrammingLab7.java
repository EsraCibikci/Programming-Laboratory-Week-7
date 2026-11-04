package programminglab7;

public class ProgrammingLab7 {

    public static void main(String[] args) {
        System.out.println("=== PROGRAM STARTED ===\n");

        Customer c1 = new Customer("Ahmet Yilmaz", "5551112233", "Ataturk St.", "Kocasinan", "Kayseri");
        Customer c2 = new Customer("Ayse Demir", "5554445566", "Bagdat St.", "Melikgazi", "Kayseri");

        Order order1 = new Order(c1);
        Order order2 = new Order(c2);

        try {

            System.out.println("-> Adding items to Order 1...");
            order1.addItem(new MenuItem("Burger", 120.0, 1));
            order1.addItem(new MenuItem("Burger", 120.0, 1));
            order1.addItem(new MenuItem("Fries", 40.0, 2));

            System.out.println("-> Updating quantity and removing an item...");
            order1.updateQuantity("Fries", 1);
            order1.removeItem("Fries");

            Coupon appliedCoupon = new Coupon("SAVE20", 0.20, 200.0);
            order1.applyCoupon(appliedCoupon);

            order1.checkout();

        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }

        System.out.println("=== TESTING CUSTOM EXCEPTIONS ===");

        try {
            Order order3 = new Order(c2);
            order3.addItem(new MenuItem("Water", 10.0, 1));
            order3.checkout();
        } catch (MinimumOrderException e) {
            System.out.println("[CAUGHT MinimumOrderException]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Other exception: " + e.getMessage());
        }

        try {
            Order order4 = new Order(c1);
            order4.removeItem("NonExistentItem");
        } catch (ItemNotFoundException e) {
            System.out.println("[CAUGHT ItemNotFoundException]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Other exception: " + e.getMessage());
        }

        try {
            MenuItem invalidItem = new MenuItem("Cola", 20.0, -2);
        } catch (InvalidQuantityException e) {
            System.out.println("[CAUGHT InvalidQuantityException]: " + e.getMessage());
        }

        System.out.println("\n=== STATIC STATISTICS ===");
        System.out.println("Completed Orders : " + Order.getCompletedOrders());
        System.out.println("Total Revenue    : " + Order.getTotalRevenue() + " TL");
    }
}
