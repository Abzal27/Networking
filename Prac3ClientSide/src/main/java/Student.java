
import java.io.Serializable;


public class Student implements Serializable {
    private String studentName;
    private String studentId;
    private String studentScore;

    public Student() {
    }

    public Student(String studentName, String studentId, String studentScore) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentScore = studentScore;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(String studentScore) {
        this.studentScore = studentScore;
    }

    @Override
    public String toString() {
        return "Student{" + "studentName=" + studentName + ", studentId=" + studentId + ", studentScore=" + studentScore + '}';
    }
    
}
