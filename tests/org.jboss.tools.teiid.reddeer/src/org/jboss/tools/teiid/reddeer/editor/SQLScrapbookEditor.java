package org.jboss.tools.teiid.reddeer.editor;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;



public class SQLScrapbookEditor extends SWTBotEditor {

	public SQLScrapbookEditor() {
		this("SQL Scrapbook 0");
	}
	
	public SQLScrapbookEditor(String name) {
		super(new SWTWorkbenchBot().editorByTitle(name).getReference(), new SWTWorkbenchBot());
	}

	public void setDatabase(String dbName){
		new SWTWorkbenchBot().comboBoxWithLabel("Database:").setSelection(dbName);
	}
	
	public void setText(String text){
		new SWTWorkbenchBot().styledText().setText(text);
	}
	
	public void executeAll(){
		new SWTWorkbenchBot().styledText().contextMenu("Execute All").click();
		
		SWTBotShell shell = new SWTWorkbenchBot().shell("SQL Statement Execution");
		new SWTWorkbenchBot().waitUntil(Conditions.shellCloses(shell), 60 * 1000);
	}
}
