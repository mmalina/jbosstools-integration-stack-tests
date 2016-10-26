package org.jboss.tools.bpmn2.ui.bot.complex.test.testcase;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.jboss.tools.bpmn2.reddeer.editor.ElementType;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.Message;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.activities.ServiceTask;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.endevents.EndEvent;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.startevents.StartEvent;
import org.jboss.tools.bpmn2.ui.bot.complex.test.JBPM6ComplexTest;
import org.jboss.tools.bpmn2.ui.bot.complex.test.JBPM6ComplexTestDefinitionRequirement.JBPM6ComplexTestDefinition;
import org.jboss.tools.bpmn2.ui.bot.complex.test.TestPhase.Phase;
import org.jboss.tools.bpmn2.ui.bot.complex.test.TestPhase;
import org.jboss.tools.bpmn2.ui.bot.test.jbpm.JbpmAssertions;
import org.jbpm.bpmn2.handler.ServiceTaskHandler;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkflowProcessInstance;

@JBPM6ComplexTestDefinition(projectName = "JBPM6ComplexTest", importFolder = "resources/bpmn2/model/base", openFile = "BaseBPMN2-ServiceTask.bpmn2", saveAs = "BPMN2-ServiceTask.bpmn2")
public class ComplexServiceTaskTest extends JBPM6ComplexTest {

	private ServiceTask service;
	
	@TestPhase(phase = Phase.MODEL)
	public void model() {
		StartEvent start = new StartEvent("StartProcess");
		service = (ServiceTask) start.append("DateService", ElementType.SERVICE_TASK);

		service.setImplementation("Java");
		service.setOperation("java.util.Date/compareTo", new Message("DateVar", "java.util.Date"),
				new Message("ObjectVar", OBJECT), null);
		
		service.setServiceInputVariable("DateVar");
		service.setServiceOutputVariable("ObjectVar");
		service.connectTo(new EndEvent("EndProcess"));
	}
	
	@TestPhase(phase = Phase.VALIDATE)
	public void noParameterAndResultInIOTab() {
		assertEquals(0, service.getInputParameterMappingCount());
		assertEquals(0, service.getOutputParameterMappingCount());
	}

	@TestPhase(phase = Phase.RUN)
	public void run(KieSession kSession) {
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("ObjectVar", null);
		args.put("DateVar", new java.util.Date());

		kSession.getWorkItemManager().registerWorkItemHandler("Service Task", new ServiceTaskHandler());
		WorkflowProcessInstance processInstance = (WorkflowProcessInstance) kSession.startProcess("BPMN2ServiceTask",
				args);

		JbpmAssertions.assertProcessInstanceCompleted(processInstance, kSession);
		assertEquals(1, processInstance.getVariable("ObjectVar"));
	}
}
