// File: Student.java

package attendance;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Student {
  private String name;
  private Integer id;
  private String department;

  // Constructor to initialize Student fields
  public Student(String name, Integer id, String department) {
    this.name = name;
    this.id = id;
    this.department = department;
  }

  // Getter for name
  public String getName() {
    return this.name;
  }

  // Getter for id
  public Integer getId() {
    return this.id;
  }

  // Getter for department
  public String getDepartment() {
    return this.department;
  }

  /**
   * Register this student in an AttendanceSystem instance.
   * Adds the student to the list and creates an empty attendance list.
   */
  public void register(AttendanceSystem system) {
    // Check if student with same ID already exists
    if (!system.students.stream().anyMatch(s -> s.getId().equals(this.id))) {
      system.students.add(this);
      system.attendanceRecords.put(this.name, new ArrayList<>());
    }
  }

  /**
   * Print this student's details and then delegate to the system
   * to print their attendance record.
   */
  public void viewRecord(AttendanceSystem system) {
    System.out.println(
            "Student: " + name +
                    " (ID: " + id +
                    ", Dept: " + department + ")"
    );
    system.viewStudResult(name);
  }
}
