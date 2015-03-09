package org.jboss.tools.bpmn2.ui.bot.test.testcase.editor;

import org.jboss.tools.bpmn2.reddeer.editor.ElementType;
import org.jboss.tools.bpmn2.reddeer.editor.Position;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.ErrorRef;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.Process;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.ScriptLanguage;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.activities.ScriptTask;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.activities.UserTask;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.boundaryevents.ErrorBoundaryEvent;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.endevents.ErrorEndEvent;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.gateways.Direction;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.gateways.ParallelGateway;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.startevents.StartEvent;
import org.jboss.tools.bpmn2.ui.bot.test.JBPM6BaseTest;
import org.jboss.tools.bpmn2.ui.bot.test.requirements.ProcessDefinitionRequirement.ProcessDefinition;

/**
 * ISSUES - Engine does not validate the presence of the rules.
 */
@ProcessDefinition(name="BPMN2-ErrorBoundaryEventOnTask", project="EditorTestProject")
public class ErrorBoundaryEventOnTaskTest extends JBPM6BaseTest {

	@Override
	public void buildProcessModel() {
		Process process = new Process("BPMN2-ErrorBoundaryEventOnTask");
		process.addLocalVariable("x", "String");
		ErrorRef myError = new ErrorRef("myError", "org.jbpm.bpmn2.objects.MyError", "String");
		process.addError(myError);
		
		StartEvent start = new StartEvent("StartProcess");
		start.append("Split", ElementType.PARALLEL_GATEWAY);
		
		ParallelGateway gateway = new ParallelGateway("Split");
		gateway.setDirection(Direction.DIVERGING);
		gateway.append("User Task", ElementType.USER_TASK, Position.NORTH_EAST);
		gateway.append("User task error attached", ElementType.USER_TASK, Position.SOUTH_EAST);
		
		UserTask task1 = new UserTask("User Task");
		task1.addActor("john");
		task1.setTaskName("TaskForJohn");
		task1.append("Error end event", ElementType.ERROR_END_EVENT);
		
		ErrorEndEvent end1 = new ErrorEndEvent("Error end event");
		end1.setErrorEvent(myError, "x");
		
		UserTask task2 = new UserTask("User task error attached");
		task2.addActor("mary");
		task2.setTaskName("TaskForMary");
		task2.append("Error 1", ElementType.END_EVENT);
		task2.addEvent("Error Boundary Event", ElementType.ERROR_BOUNDARY_EVENT);
		
		ErrorBoundaryEvent boundaryEvent = new ErrorBoundaryEvent("Error Boundary Event");
		boundaryEvent.setErrorEvent(myError, "x");
		boundaryEvent.append("Script Task", ElementType.SCRIPT_TASK, Position.SOUTH);
		
		ScriptTask script = new ScriptTask("Script Task");
		script.setScript(ScriptLanguage.JAVA, "System.out.println(\"Error handled\");");
		script.append("Error 2", ElementType.END_EVENT);
	}
	
}