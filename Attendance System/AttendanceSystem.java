package attendance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AttendanceSystem {
  protected List<Student> students = new ArrayList<>();
  protected Map<String, List<LocalDate>> attendanceRecords = new HashMap<>();
  private Scanner scanner = new Scanner(System.in);
  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static void main(String[] args) {
    AttendanceSystem system = new AttendanceSystem();
    system.mainMenu();
  }

  public void mainMenu() {
    int choice;
    do {
      print();
      System.out.print("Enter choice: ");
      try {
        choice = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        choice = -1;
      }

      switch (choice) {
        case 1:
          addStudent();
          break;
        case 2:
          deleteStudent();
          break;
        case 3:
          checkPresent();
          break;
        case 4:
          viewStudent();
          break;
        case 5:
          viewAll();
          break;
        case 0:
          System.out.println("Exiting...");
          break;
        default:
          System.out.println("Invalid choice. Try again.");
      }
    } while (choice != 0);
  }

  public void addStudent() {
    System.out.print("Enter student name: ");
    String name = scanner.nextLine().trim();
    System.out.print("Enter student ID: ");
    Integer id = Integer.parseInt(scanner.nextLine().trim());
    System.out.print("Enter student department: ");
    String department = scanner.nextLine().trim();

    if (!name.isEmpty() && students.stream().noneMatch(s -> s.getId().equals(id))) {
      Student student = new Student(name, id, department);
      students.add(student);
      attendanceRecords.put(name, new ArrayList<>());
      System.out.println(name + " added successfully.");
    } else {
      System.out.println("Invalid name or duplicate ID.");
    }
  }

  public void deleteStudent() {
    System.out.print("Enter student ID to delete: ");
    Integer id = Integer.parseInt(scanner.nextLine().trim());
    Student studentToRemove = null;

    for (Student s : students) {
      if (s.getId().equals(id)) {
        studentToRemove = s;
        break;
      }
    }

    if (studentToRemove != null) {
      students.remove(studentToRemove);
      attendanceRecords.remove(studentToRemove.getName());
      System.out.println("Student removed successfully.");
    } else {
      System.out.println("Student not found.");
    }
  }

  public void checkPresent() {
    System.out.print("Enter student ID to mark present: ");
    Integer id = Integer.parseInt(scanner.nextLine().trim());
    Student student = students.stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElse(null);

    if (student != null) {
      LocalDate today = LocalDate.now();
      List<LocalDate> dates = attendanceRecords.get(student.getName());
      if (!dates.contains(today)) {
        dates.add(today);
        System.out.println("Marked present for " + student.getName() + " on " + dtf.format(today));
      } else {
        System.out.println(student.getName() + " is already marked present today.");
      }
    } else {
      System.out.println("Student not found.");
    }
  }

  public void viewStudent() {
    System.out.print("Enter student ID to view record: ");
    Integer id = Integer.parseInt(scanner.nextLine().trim());
    Student student = students.stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElse(null);

    if (student != null) {
      student.viewRecord(this);
    } else {
      System.out.println("Student not found.");
    }
  }

  protected void viewStudResult(String name) {
    List<LocalDate> dates = attendanceRecords.get(name);
    System.out.println("Attendance record for " + name + ":");
    if (dates == null || dates.isEmpty()) {
      System.out.println("  No records.");
    } else {
      dates.sort(Comparator.naturalOrder());
      for (LocalDate date : dates) {
        System.out.println("  " + dtf.format(date));
      }
    }
  }

  private void viewAll() {
    System.out.println("All attendance records:");
    if (students.isEmpty()) {
      System.out.println("  No students in the system.");
      return;
    }
    for (Student student : students) {
      student.viewRecord(this);
    }
  }

  private void print() {
    System.out.println("\n=== Attendance System Menu ===");
    System.out.println("1. Add Student");
    System.out.println("2. Delete Student");
    System.out.println("3. Mark Present");
    System.out.println("4. View Student Record");
    System.out.println("5. View All Records");
    System.out.println("0. Exit");
  }
}
