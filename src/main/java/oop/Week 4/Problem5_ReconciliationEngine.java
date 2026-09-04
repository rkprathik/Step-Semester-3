package oop.WEEK 4;
public class Problem5_ReconciliationEngine {

    static class DeliveryAccount {
        protected String studentId;
        protected double orderValue;
        protected final double minimumSurgePercent;

        static String systemName;

        // One-time class-level setup.
        static {
            systemName = "Nightly Multi-Kitchen Reconciliation";
        }

        public DeliveryAccount(String studentId, double orderValue) {
            this(studentId, orderValue, 1.0);
        }

        protected DeliveryAccount(String studentId, double orderValue,
                                  double minimumSurgePercent) {
            this.studentId = studentId;
            this.orderValue = orderValue;
            this.minimumSurgePercent = minimumSurgePercent;
        }

        public DeliveryAccount(String studentId) {
            this(studentId, 0.0);
        }

        public final double calculateSurgeFee(int delayMinutes) {
            if (orderValue < 0 || delayMinutes < 0) {
                throw new IllegalArgumentException("Invalid account data");
            }

            if (delayMinutes == 0) {
                return 0.0;
            }

            int firstTier = Math.min(delayMinutes, 5);
            int secondTier = Math.min(Math.max(delayMinutes - 5, 0), 10);
            int thirdTier = Math.max(delayMinutes - 15, 0);

            double tieredFee =
                    orderValue * 0.005 * firstTier
                    + orderValue * 0.01 * secondTier
                    + orderValue * 0.02 * thirdTier;

            double floorFee =
                    orderValue * minimumSurgePercent / 100.0;

            return Math.max(tieredFee, floorFee);
        }

        void processAccount(DeliveryAccount account,
                            double amount, int delayMinutes) {
            if (account == null) {
                return;
            }

            // The amount is the amount being reconciled for this account.
            // The account's stored orderValue is used by calculateSurgeFee().
            double originalValue = account.orderValue;
            account.orderValue = amount;

            try {
                double fee = account.calculateSurgeFee(delayMinutes);
                System.out.println(account.studentId
                        + " | amount: Rs " + amount
                        + " | surge fee: Rs " + fee);
            } finally {
                account.orderValue = originalValue;
            }
        }

        static void processBatch(DeliveryAccount[] accounts,
                                 double[] amounts,
                                 int[] delayMinutesArray) {

            // If lengths differ, process only the common prefix. This prevents
            // an index error and avoids pairing missing data with another account.
            int n = Math.min(accounts.length,
                    Math.min(amounts.length, delayMinutesArray.length));

            int processed = 0;
            int nullSkipped = 0;
            int premium = 0;
            int regular = 0;
            double grandTotal = 0.0;

            for (int i = 0; i < n; i++) {
                DeliveryAccount account = accounts[i];

                if (account == null) {
                    nullSkipped++;
                    continue;
                }

                double fee;

                try {
                    double originalValue = account.orderValue;
                    account.orderValue = amounts[i];

                    fee = account.calculateSurgeFee(delayMinutesArray[i]);

                    account.orderValue = originalValue;
                } catch (RuntimeException e) {
                    // A bad individual record must not crash the complete run.
                    continue;
                }

                processed++;
                grandTotal += fee;

                if (account instanceof PremiumAccount) {
                    premium++;
                } else {
                    regular++;
                }
            }

            System.out.println(processed + " processed | "
                    + nullSkipped + " null skipped | "
                    + premium + " premium | "
                    + regular + " regular | grand total surge fees = Rs "
                    + grandTotal);
        }
    }

    static class PremiumAccount extends DeliveryAccount {
        PremiumAccount(String studentId, double orderValue) {
            super(studentId, orderValue, 2.0);
        }

        PremiumAccount(String studentId) {
            this(studentId, 0.0);
        }
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new PremiumAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        DeliveryAccount.processBatch(
                accounts, amounts, delayMinutesArray);
    }
}
