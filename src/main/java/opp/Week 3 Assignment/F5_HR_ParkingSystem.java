package oop.assigment_problems;
public class F5_HR_ParkingSystem {

    static class Employee {
        private int empId;
        private String empName;
        private double salary;

        Employee(int empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        double getSalary() {
            return salary;
        }
    }

    static class ManagerEmployee extends Employee {
        private double teamBonus;

        ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        double effectiveSalary() {
            return getSalary() + teamBonus;
        }
    }

    static class InternEmployee extends Employee {
        private double stipendCap;

        InternEmployee(int empId, String empName, double salary, double stipendCap) {
            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        double effectiveSalary() {
            return Math.min(getSalary(), stipendCap);
        }
    }

    static class ParkingSlot {
        String slotNo;
        int capacity;
        int occupiedCount;

        ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        boolean allot(String vehicleNo) {
            if (occupiedCount < capacity) {
                occupiedCount++;
                return true;
            }
            return false;
        }
    }

    static class CompanyEmployeeRecord {
        String name;
        String empId;
        Employee employee;
        ParkingSlot slot;

        static int totalRecords = 0;

        CompanyEmployeeRecord(String name, String empId,
                              Employee employee, ParkingSlot slot) {
            this.name = name;
            this.empId = empId;
            this.employee = employee;
            this.slot = slot;
            totalRecords++;
        }

        String fullProfile() {
            double pay;

            if (employee instanceof ManagerEmployee) {
                pay = ((ManagerEmployee) employee).effectiveSalary();
            } else if (employee instanceof InternEmployee) {
                pay = ((InternEmployee) employee).effectiveSalary();
            } else {
                pay = employee.getSalary();
            }

            if (slot == null) {
                return name + " | Pay: Rs " + pay + " | Slot: no parking assigned";
            }

            return name + " | Pay: Rs " + pay + " | Slot: " + slot.slotNo;
        }
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }
        return null;
    }

    static ParkingSlot safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);

        if (slot != null && slot.allot(vehicleNo)) {
            return slot;
        }

        return null;
    }

    public static void main(String[] args) {
        Employee manager = new ManagerEmployee(101, "Divya", 70000, 8000);
        Employee regular = new Employee(102, "Karan", 40000);
        Employee intern = new InternEmployee(103, "Meera", 12000, 10000);

        ParkingSlot[] parking = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };

        ParkingSlot slot1 = safeAllot(parking, "CAR101");
        ParkingSlot slot2 = safeAllot(parking, "CAR102");

        CompanyEmployeeRecord r1 =
                new CompanyEmployeeRecord("Divya", "E101", manager, slot1);
        CompanyEmployeeRecord r2 =
                new CompanyEmployeeRecord("Karan", "E102", regular, slot2);
        CompanyEmployeeRecord r3 =
                new CompanyEmployeeRecord("Meera", "E103", intern, null);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
