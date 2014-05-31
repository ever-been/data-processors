package cz.everbeen.testing.integration;

import cz.cuni.mff.d3s.been.results.Result;

/**
 * A testing authentication statistic
 *
 * @author darklight
 */
public class Auth extends Result {

	/** The 'synchronize' request */
	Request syn;
	/** The 'confirm' request */
	Request ack;

	public Auth(){}

	static Auth random() {
		final Auth auth = new Auth();
		auth.setSyn(Request.random());
		auth.setAck(Request.random());
		return auth;
	}

	public Request getSyn() {
		return syn;
	}

	public void setSyn(Request syn) {
		this.syn = syn;
	}

	public Request getAck() {
		return ack;
	}

	public void setAck(Request ack) {
		this.ack = ack;
	}
}
