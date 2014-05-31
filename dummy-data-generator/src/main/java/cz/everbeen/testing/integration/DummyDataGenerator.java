package cz.everbeen.testing.integration;

import cz.cuni.mff.d3s.been.mq.MessagingException;
import cz.cuni.mff.d3s.been.persistence.DAOException;
import cz.cuni.mff.d3s.been.taskapi.Persister;
import cz.cuni.mff.d3s.been.taskapi.Task;
import cz.cuni.mff.d3s.been.taskapi.TaskException;
import cz.cuni.mff.d3s.been.util.PropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A dummy data generator for the purposes of EverBEEN integration testing.
 *
 * @author darklight
 */
public class DummyDataGenerator extends Task {

	private static final String COUNT = "count";
	private static final int DEFAULT_COUNT = 100;

	private static final String RESULT_GROUP = "resultGroup";
	private static final String DEFAULT_RESULT_GROUP = "dummy";

	/** EverBEEN logger */
	private static final Logger log = LoggerFactory.getLogger(DummyDataGenerator.class);

	@Override
	public void run(String[] args) throws TaskException, MessagingException, DAOException {
		final PropertyReader propertyReader = createPropertyReader();
		final int count = propertyReader.getInteger(COUNT, DEFAULT_COUNT);
		final String group = propertyReader.getString(RESULT_GROUP, DEFAULT_RESULT_GROUP);

		log.info("Dummy data generator engaged.");

		final Persister resultPersister = results.createResultPersister(group);
		for (int i = 0; i < count; ++i) resultPersister.persist(DummyDataFactory.auth());

		log.info("Dummy data generator terminated.");
	}
}
