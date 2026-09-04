package oop.assigment_problems;
public class F1_BookIssue {

    static class BookIssue {
        String title;
        String borrowerName;
        int daysOverdue;

        BookIssue(String title, String borrowerName, int daysOverdue) {
            this.title = title;
            this.borrowerName = borrowerName;
            this.daysOverdue = daysOverdue;
        }

        double fineAmount() {
            if (daysOverdue > 0) {
                return daysOverdue * 5.0;
            }
            return 0.0;
        }

        boolean isSeverelyOverdue() {
            return daysOverdue > 14;
        }

        // fineAmount() belongs to one book, so it is an instance method.
        // totalFineCollected() works on many BookIssue objects, so it belongs
        // to the class as a whole and is therefore static.
        static double totalFineCollected(BookIssue[] issues) {
            double total = 0.0;

            for (BookIssue issue : issues) {
                total += issue.fineAmount();
            }

            return total;
        }
    }

    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Ravi", 18),
            new BookIssue("Effective Java", "Meera", 5),
            new BookIssue("Refactoring", "Arun", 0),
            new BookIssue("DSA Handbook", "Divya", 21),
            new BookIssue("Design Patterns", "Karan", 9)
        };

        for (BookIssue issue : issues) {
            if (issue.isSeverelyOverdue()) {
                System.out.println(issue.title + " - " + issue.daysOverdue
                        + " days - Severely overdue");
            } else {
                System.out.println(issue.title + " - " + issue.daysOverdue
                        + " days - OK");
            }
        }

        System.out.println("Total fine collected: Rs "
                + BookIssue.totalFineCollected(issues));
    }
}
