package org.jboss.tools.modeshape.ui.bot.test.suite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.eclipse.wst.server.ui.view.ServerLabel;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.tools.modeshape.ui.bot.test.suite.ServerRequirement.Server;

/**
 * 
 * @author apodhrad, lkrejcir
 * 
 */
public class ServerRequirement implements Requirement<Server> {

	private static final String REFRESH = "Refresh / Reconnect Teiid Instance Connection";
	private Server serverConf;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Server {

		Type type();

		State state();
	}

	@Override
	public boolean canFulfill() {
		return ModeshapeSuite.getServerName() != null;
	}

	@Override
	public void fulfill() {
		org.jboss.reddeer.eclipse.wst.server.ui.view.Server server = null;
		server = new ServersView().getServer(ModeshapeSuite.getServerName());

		ServerLabel serverLabel = server.getLabel();
		// Server should be running
		if (serverConf.state().equals(State.RUNNING)) {
			if (serverLabel.getState().equals(ServerState.STOPPED)) {
				server.start();
			}//otherwise just refresh
			else {
				new DefaultToolItem(REFRESH).click();
				new PushButton("OK").click();
			}
		}
		// Server should not be running
		if (serverConf.state().equals(State.NOT_RUNNING)) {
			if (serverLabel.getState().equals(ServerState.STARTED)) {
				server.stop();
			}
		}
	}

	@Override
	public void setDeclaration(Server serverConf) {
		this.serverConf = serverConf;
	}

	public enum Type {
		ALL, SOA, AS
	}

	public enum State {
		PRESENT, RUNNING, NOT_RUNNING, DISABLED
	}

}
