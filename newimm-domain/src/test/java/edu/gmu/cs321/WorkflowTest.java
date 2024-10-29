package edu.gmu.cs321;
import static org.junit.Assert.*;

import org.junit.Test;

class WorkflowTest {

    @Test
    void testConstructorAndGetters {
        Workflow workflow = new Workflow(1, "Stp 1", "stp 2");
        assertEquals(1, workflow.WorkflowID());
        assertEquals("stp 1", workflow.getCurrentStep());
        assertEquals("stp 2", workflow.getNextStep());
    }

    @Test
    void testSetCurrentStep {
        Workflow workflow = new Workflow(1, "Stp 1", "stp 2");
        workflow.setCurrentStep("stp 3")
        assertEquals("stp 3", workflow.getCurrentStep());
    }

    @Test
    void testSetNextStep {
        Workflow workflow = new Workflow(1, "Stp 1", "stp 2");
        workflow.setNextStep("stp 8")
        assertEquals("stp 8", workflow.getNextStep());
    }

    @Test
    void testAdvanceWorkflow() {
        Workflow workflow = new Workflow(1, "Stp 1", "stp 2");
        Form form = new Form(897, new String[]{"feild1"});
        Employee employee = new Employee(321);

        workflow.advanceWorkflow(form, employee);

        assertEquals("stp 2", workflow.getCurrentStep());
    }
}
