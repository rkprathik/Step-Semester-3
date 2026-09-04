package oop.assigment_problems;
public class Problem3_CanteenRanking {

    static class Canteen {
        private String canteenCode;
        private String canteenName;
        private int trustScore;

        public Canteen(String canteenCode, String canteenName, int trustScore) {
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        public Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, 3);
        }

        int compareTo(Canteen other) {
            // Higher trust score comes first.
            if (this.trustScore != other.trustScore) {
                return other.trustScore - this.trustScore;
            }

            // For ties, compare codes ignoring letter case.
            int codeResult =
                    this.canteenCode.compareToIgnoreCase(other.canteenCode);

            if (codeResult != 0) {
                return codeResult;
            }

            // Final tie-breaker: shorter name comes first.
            return this.canteenName.length() - other.canteenName.length();
        }

        static Canteen[] rankCanteens(Canteen[] canteens) {
            Canteen[] result = new Canteen[canteens.length];

            for (int i = 0; i < canteens.length; i++) {
                result[i] = canteens[i];
            }

            // Selection-sort style manual sorting; no built-in sort.
            for (int i = 0; i < result.length - 1; i++) {
                int best = i;

                for (int j = i + 1; j < result.length; j++) {
                    if (result[j].compareTo(result[best]) < 0) {
                        best = j;
                    }
                }

                Canteen temp = result[i];
                result[i] = result[best];
                result[best] = temp;
            }

            return result;
        }

        String getCode() {
            return canteenCode;
        }
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = Canteen.rankCanteens(canteens);

        System.out.print("[");
        for (int i = 0; i < ranked.length; i++) {
            System.out.print("\"" + ranked[i].getCode() + "\"");

            if (i < ranked.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
