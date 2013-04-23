package org.jboss.tools.bpmn2.itests.editor;

public enum ConnectionType {
	
	ASSOCIATION,
	SEQUENCE_FLOW;

	public String toName() {
		StringBuilder r = new StringBuilder();
		for (String w : name().split("_")) {
			r.append(w.charAt(0) + w.substring(1).toLowerCase());
			r.append(" ");
		}
		return r.toString().trim();
	}
	
}
