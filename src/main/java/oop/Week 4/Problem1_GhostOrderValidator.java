package oop.WEEK 4;
public class Problem1_GhostOrderValidator {

    static class FoodOrder {
        private String studentName;
        private String dishName;
        private boolean delivered;

        // No no-argument constructor is provided.
        FoodOrder(String studentName, String dishName) {
            if (studentName == null || studentName.trim().isEmpty()) {
                throw new IllegalArgumentException("Student name cannot be blank");
            }

            if (dishName == null || dishName.trim().isEmpty()) {
                throw new IllegalArgumentException("Dish name cannot be blank");
            }

            this.studentName = studentName;
            this.dishName = dishName;
            this.delivered = false;
        }

        void markDelivered() {
            if (!delivered) {
                delivered = true;
                System.out.println("Order delivered: " + studentName
                        + " - " + dishName);
            } else {
                System.out.println("Order already delivered: " + studentName
                        + " - " + dishName);
            }
        }
    }

    static void processBatch(String[][] rawOrders) {
        int valid = 0;
        int rejected = 0;

        for (String[] raw : rawOrders) {
            try {
                if (raw == null || raw.length < 2) {
                    throw new IllegalArgumentException("Invalid order data");
                }

                FoodOrder order = new FoodOrder(raw[0], raw[1]);
                valid++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };

        processBatch(rawOrders);

        FoodOrder order = new FoodOrder("Ravi", "Dosa");
        order.markDelivered();
        order.markDelivered();
    }
}
