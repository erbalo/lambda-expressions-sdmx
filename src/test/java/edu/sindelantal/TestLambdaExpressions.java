package edu.sindelantal;

import edu.sindelantal.mock.StudentCreator;
import edu.sindelantal.model.Student;
import edu.sindelantal.service.StudentService;
import edu.sindelantal.util.ConsoleUtils;
import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLambdaExpressions {

    private static EtmMonitor monitor = EtmManager.getEtmMonitor();
    private static StudentService studentService;
    private static List<Student> students;
    private static List<Student> studentsAux;
    private static Stream<Student> searchStream;
    private static Stream<Student> predicateStream;
    private static Student defaultStudent;

    @BeforeClass
    public static void setUp() {
        BasicEtmConfigurator.configure();
        monitor = EtmManager.getEtmMonitor();
        monitor.start();

        EtmPoint point = monitor.createPoint("Test:creatingMock");

        try {
            studentService = new StudentService();
            students = studentService.getStudents();
            studentsAux = new ArrayList<>();
            studentsAux.addAll(students);
            searchStream = students.stream();
            predicateStream = students.stream();
            defaultStudent = StudentCreator.defaultStudent();
        }finally {
            point.collect();
        }
    }

    @AfterClass
    public static void end() {
        System.out.println();
        monitor.render(new SimpleTextRenderer());
        monitor.stop();
    }

    @Test
    public void testPredicate(){
        EtmPoint point = monitor.createPoint("Test:testPredicate");
        ConsoleUtils.printConsoleTitle("Using predicates");

        try{
            //Predicate<Student> predicate = student -> student.age() > 23;
            long n = predicateStream.filter(student -> student.age() > 23)
                                    .count();

            Assert.assertTrue( n >= 1);
            System.out.println("COUNT > " + n);
        } finally {
            point.collect();
        }
    }

    @Test
    public void testPredicateJava7(){
        EtmPoint point = monitor.createPoint("Test:testPredicateJava7");
        ConsoleUtils.printConsoleTitle("Using predicates compare Java 7");

        try {
            List<Student> auxStudents = students;
            Iterator<Student> i = auxStudents.iterator();

            while(i.hasNext()) {
                Student student = i.next();
                if(student.age() < 24) {
                    i.remove();
                }
            }

            int n = auxStudents.size();

            Assert.assertTrue( n >= 1);
            System.out.println("COUNT > " + n);
        } finally {
            point.collect();
        }
    }

    @Test
    public void searchDefaultUser() {
        EtmPoint point = monitor.createPoint("Test:searchDefaultUser");
        ConsoleUtils.printConsoleTitle("Using predicates and consumers");

        try {
            Predicate<Student> predicate = student -> student.equals(defaultStudent);
            Student student = null;
            searchStream.forEach(System.out::println);
        }finally {
            point.collect();
        }
    }

    @Test
    public void searchDefaultUserJava7() {
        EtmPoint point = monitor.createPoint("Test:searchDefaultUserJava7");
        ConsoleUtils.printConsoleTitle("Using predicates and consumers Java 7");

        try {
            Student founded = null;
            for(Student student: students){
                if(student.equals(defaultStudent)){
                    founded = student;
                    break;
                }
            }

            Assert.assertNotNull(founded);
            studentService.printStudent(founded);
        }finally {
            point.collect();
        }
    }

    @Test
    public void sortList() {
        EtmPoint point = monitor.createPoint("Test:sortList");
        ConsoleUtils.printConsoleTitle("Sort list");
        System.out.println("----- Unsorted -----");
        students.forEach(studentService::printOnlyName);
        System.out.println();

        try {
            System.out.println("----- Sorted -----");
            students.sort(Comparator.comparing(Student::name));
            students.forEach(studentService::printOnlyName);
            System.out.println();
        }finally {
            point.collect();
        }
    }

    @Test
    public void sortListJava7(){
        EtmPoint point = monitor.createPoint("Test:sortListJava7");
        ConsoleUtils.printConsoleTitle("Sort list Java 7");
        System.out.println("----- Unsorted -----");

        for (Student student : studentsAux) {
            studentService.printOnlyName(student);
        }
        System.out.println();

        try {
            System.out.println("----- Sorted -----");
            Collections.sort(studentsAux, new Comparator<Student>() {
                @Override
                public int compare(Student student1, Student student2) {
                    return student1.name().compareTo(student2.name());
                }
            });

            for (Student student : studentsAux) {
                studentService.printOnlyName(student);
            }
            System.out.println();
        }finally {
            point.collect();
        }
    }

    @Test
    public void getDefaultWithOptional() {
        EtmPoint point = monitor.createPoint("Test:getDefaultWithOptional");
        ConsoleUtils.printConsoleTitle("Default student with Optional");

        try {
            studentService.getDefault().ifPresent(studentService::printStudent);
        }finally {
            point.collect();
        }

    }

}
