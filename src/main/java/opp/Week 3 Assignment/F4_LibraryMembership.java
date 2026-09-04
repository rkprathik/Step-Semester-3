package oop.assigment_problems;
public class F4_LibraryMembership {

    // Broken version: all members share the same fields.
    static class BrokenLibraryMember {
        static String name;
        static String memberId;
        static int booksIssued;

        BrokenLibraryMember(String name, String memberId, int booksIssued) {
            BrokenLibraryMember.name = name;
            BrokenLibraryMember.memberId = memberId;
            BrokenLibraryMember.booksIssued = booksIssued;
        }

        /*
         * static is wrong for these fields because name, memberId and
         * booksIssued belong to each individual member. A static field has
         * only one shared value for the entire class, so a new member
         * overwrites the previous member's data.
         */
    }

    // Fixed version.
    static class LibraryMember {
        private String name;
        private String memberId;
        private int booksIssued;

        static String libraryName = "Central Library";
        static int memberCount = 1000;

        LibraryMember(String name, int booksIssued) {
            this.name = name;
            this.memberId = "LM-" + (++memberCount);
            this.booksIssued = booksIssued;
        }

        void printMemberCard() {
            System.out.println(name + " | " + memberId);
        }

        static void printTotalMembers() {
            System.out.println("Total members: " + (memberCount - 1000));
        }
    }

    public static void main(String[] args) {

        System.out.println("Broken version:");

        BrokenLibraryMember first =
                new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember second =
                new BrokenLibraryMember("Rohan", "LM-1002", 3);

        System.out.println(first.name);
        System.out.println(second.name);
        System.out.println("(Aditi's data was overwritten)");

        System.out.println();
        System.out.println("Fixed version:");

        LibraryMember member1 = new LibraryMember("Aditi", 2);
        LibraryMember member2 = new LibraryMember("Rohan", 3);

        member1.printMemberCard();
        member2.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}
