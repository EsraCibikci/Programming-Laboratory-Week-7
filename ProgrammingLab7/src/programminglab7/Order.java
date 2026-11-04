package programminglab7;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int nextOrderNumber = 1001;
    public static final double MIN_ORDER = 100.0;
    public static final double DELIVERY_FEE = 20.0;

    private static int completedOrders = 0;
    private static double totalRevenue = 0.0;

    private int orderNumber;
    private Customer customer;
    private ArrayList<MenuItem> items;
    private Coupon coupon;
    private boolean isCheckedOut = false;

    public Order(Customer customer) {
        this.orderNumber = nextOrderNumber++;
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem newItem) {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(newItem.getName())) {
                try {
                    item.setQuantity(item.getQuantity() + newItem.getQuantity());
                } catch (InvalidQuantityException e) {
                    System.out.println(e.getMessage());
                }
                return;
            }
        }
        items.add(newItem);
    }

    public boolean removeItem(String name) throws ItemNotFoundException {
        MenuItem target = null;
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                target = item;
                break;
            }
        }
        if (target == null) {
            throw new ItemNotFoundException("Item '" + name + "' not found in the order.");
        }
        items.remove(target);
        return true;
    }

    public boolean updateQuantity(String name, int quantity) throws ItemNotFoundException, InvalidQuantityException {
        MenuItem target = null;
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                target = item;
                break;
            }
        }
        if (target == null) {
            throw new ItemNotFoundException("Item '" + name + "' not found in the order.");
        }
        target.setQuantity(quantity);
        return true;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (MenuItem item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(items); // Read-only copy
    }

    public void applyCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public double checkout() throws MinimumOrderException {
        if (isCheckedOut) {
            System.out.println("This order has already been checked out!");
            return 0;
        }

        double subtotal = getSubtotal();
        if (subtotal < MIN_ORDER) {
            throw new MinimumOrderException("Subtotal (" + subtotal + " TL) is less than MIN_ORDER (" + MIN_ORDER + " TL).");
        }

        double discountAmount = 0;
        if (coupon != null && subtotal >= coupon.getMinSubtotal()) {
            discountAmount = subtotal * coupon.getDiscountRate();
        }

        double finalTotal = (subtotal - discountAmount) + DELIVERY_FEE;

        isCheckedOut = true;
        completedOrders++;
        totalRevenue += finalTotal;

        printReceipt(subtotal, discountAmount, finalTotal);

        return finalTotal;
    }

    private void printReceipt(double subtotal, double discount, double total) {
        System.out.println("\n================ RECEIPT ================");
        System.out.println("Order Number  : " + orderNumber);
        System.out.println(customer);
        System.out.println("--- Items ---");
        for (MenuItem item : items) {
            System.out.println("  * " + item);
        }
        System.out.println("-----------------------------------------");
        System.out.println("Subtotal      : " + subtotal + " TL");
        System.out.println("Coupon Disc.  : -" + discount + " TL");
        System.out.println("Delivery Fee  : " + DELIVERY_FEE + " TL");
        System.out.println("Total Amount  : " + total + " TL");
        System.out.println("=========================================\n");
    }

    public static int getCompletedOrders() {
        return completedOrders;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }
}
