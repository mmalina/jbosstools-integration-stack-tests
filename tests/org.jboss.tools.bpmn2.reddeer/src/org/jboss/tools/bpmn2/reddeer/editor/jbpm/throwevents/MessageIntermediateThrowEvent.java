package org.jboss.tools.bpmn2.reddeer.editor.jbpm.throwevents;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.tools.bpmn2.reddeer.editor.AbstractEvent;
import org.jboss.tools.bpmn2.reddeer.editor.ConstructType;

/**
 * 
 * @author Marek Baluch <mbaluch@redhat.com>
 */
public class MessageIntermediateThrowEvent extends AbstractEvent {
	
	/**
	 * 
	 * @param name
	 */
	public MessageIntermediateThrowEvent(String name) {
		super(name, ConstructType.MESSAGE_INTERMEDIATE_THROW_EVENT);
	}

	/**
	 * 
	 * @param message
	 * @param dataType
	 * @param source
	 */
	public void setMessageMapping(String message, String dataType, String source) {
		properties.selectTab("Event");
		new DefaultTable().select(0);
		properties.toolbarButton("Event Definitions", "Edit").click();
		
		SWTBotCombo nameBox = bot.comboBoxWithLabel("Message");
		String messageNameLabel  = message + "(" + dataType + ")";
		if (properties.contains(nameBox, messageNameLabel)) {
			nameBox.setSelection(messageNameLabel);
		} else {
			/*
			 * Click Add
			 */
			new PushButton(0).click();
			SWTBotShell shell = bot.shell("Create New Message");
			shell.activate();
			SWTBot windowBot = shell.bot();

			new LabeledText("Name").setText(message);
			if (dataType != null && !dataType.isEmpty()) {
				nameBox = windowBot.comboBoxWithLabel("Data Type");
				if (properties.contains(nameBox, dataType)) {
					nameBox.setSelection(dataType);
				} else {
					windowBot.button(0).click();
					new LabeledText("Data Type").setText(dataType);
					new PushButton("OK").click();
				}
			}
			
			new PushButton("OK").click();
			new LabeledCombo("Source").setSelection(source);
		}
		
		properties.toolbarButton("Message Event Definition Details", "Close").click();
	}
	
}
