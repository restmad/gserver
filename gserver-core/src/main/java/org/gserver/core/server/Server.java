package org.gserver.core.server;

import org.apache.log4j.Logger;
import org.gserver.core.server.config.Config;

public abstract class Server implements Runnable {
	private int server_id;
	private String server_name;
	protected Config config;

	protected Server(Config serverConfig) {
		this.config = serverConfig;
		if (this.config != null) {
			init();
		}
	}

	protected void init() {
		this.server_name = this.config.getName();
		server_id = this.config.getId();
	}

	public void run() {
		Runtime.getRuntime().addShutdownHook(
				new Thread(new CloseByExit(this.server_name)));
	}

	public String getServerName() {
		return this.server_name;
	}

	public int getServerId() {
		return server_id;
	}

	protected abstract void stop();

	private class CloseByExit implements Runnable {
		private Logger log = Logger.getLogger(CloseByExit.class);
		private String server_name;

		public CloseByExit(String server_name) {
			this.server_name = server_name;
		}

		public void run() {
			Server.this.stop();
			log.info(this.server_name + " Stop!");
		}
	}
}
