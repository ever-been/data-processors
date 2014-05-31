package cz.everbeen.testing.integration;

import java.util.Random;

/**
 * Testing class holding information about a sent HTTP request.
 *
 * @author darklight
 */
public class Request {
	/** length of the request's content */
	int length;
	/** time that it took to respond to the packet */
	int responseTime;

	static Request random() {
		final Random rand = new Random(System.currentTimeMillis());
		final Request r = new Request();
		r.setLength(rand.nextInt());
		r.setResponseTime(rand.nextInt());
		return r;
	}

	public int getLength() {
		return length;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}
}
