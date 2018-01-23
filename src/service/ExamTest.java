package service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ExamTest {
    Exam exam;

   @Before
    public void  init() {
       exam = new Exam("name", 1, "lu");
    }

    @Test
    public void setName() {
       exam.setName("newName");
       assertTrue("Test setName", exam.getName().equals("newName"));
    }

    @Test
    public void setId() {
        exam.setId(2);
        assertTrue("Test setId", exam.getId() == (2));
    }

    @Test
    public void setLu() {
        exam.setName("newLu");
        assertTrue("Test setLu", exam.getLu().equals("newLu"));
    }

    @Test
    public void changeName() {
    }

    @Test
    public void changeId() {
    }

    @Test
    public void changeLu() {
    }
}
