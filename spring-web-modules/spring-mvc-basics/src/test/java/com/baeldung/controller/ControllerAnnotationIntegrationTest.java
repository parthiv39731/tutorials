package com.baeldung.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.controller.student.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControllerApplication.class)
public class ControllerAnnotationIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    private Student selectedStudent;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        selectedStudent = new Student();
        selectedStudent.setId(1);
        selectedStudent.setName("Peter");
    }

    @Test
    public void testTestController() throws Exception {

        ModelAndView mv = this.mockMvc.perform(MockMvcRequestBuilders.get("/test")).andReturn().getModelAndView();

        // validate modal data
        Assert.assertSame(mv.getModelMap().get("data").toString(), "Welcome home man");

        // validate view name
        Assert.assertSame(mv.getViewName(), "welcome");
    }

    @Test
    public void testRestController() throws Exception {

        String responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/student/{studentId}", 1)).andReturn().getResponse().getContentAsString();

        ObjectMapper reader = new ObjectMapper();

        Student studentDetails = reader.readValue(responseBody, Student.class);

        Assert.assertEquals(selectedStudent, studentDetails);

    }
}
