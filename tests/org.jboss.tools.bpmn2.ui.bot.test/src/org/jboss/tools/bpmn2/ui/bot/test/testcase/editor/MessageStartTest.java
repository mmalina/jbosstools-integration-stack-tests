package org.jboss.tools.bpmn2.ui.bot.test.testcase.editor;

import org.jboss.tools.bpmn2.reddeer.editor.ConstructType;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.BPMN2Process;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.activities.ScriptTask;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.startevents.MessageStartEvent;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.startevents.StartEvent;
import org.jboss.tools.bpmn2.ui.bot.test.JBPM6BaseTest;
import org.jboss.tools.bpmn2.ui.bot.test.requirements.ProcessDefinitionRequirement.ProcessDefinition;

/**
 *     
 * @author mbaluch
 */
@ProcessDefinition(name="BPMN2-MessageStart", project="EditorTestProject")
public class MessageStartTest extends JBPM6BaseTest {

	@Override
	public void buildProcessModel() {
		BPMN2Process process = new BPMN2Process("BPMN2-MessageStart");
		process.addLocalVariable("x", "String");
		process.addMessage("HelloMessage", "String");
		
		new StartEvent("StartProcess").delete();
		process.add("StartProcess", ConstructType.MESSAGE_START_EVENT);
		
		MessageStartEvent start = new MessageStartEvent("StartProcess");
		start.setMessageMapping("HelloMessage", "String", "BPMN2-MessageStart/x");
		start.append("Script", ConstructType.SCRIPT_TASK);
		
		ScriptTask script = new ScriptTask("Script");
		script.setScript("Java", "System.out.println(\"x = \" + x);");
		script.append("EndProcess", ConstructType.TERMINATE_END_EVENT);
	}
	
}