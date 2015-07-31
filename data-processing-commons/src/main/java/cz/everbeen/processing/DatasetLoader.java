package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.persistence.DAOException;
import cz.cuni.mff.d3s.been.taskapi.DataSet;
import cz.cuni.mff.d3s.been.taskapi.ResultFacade;
import cz.everbeen.processing.configuration.ProcessingConfiguration;

/**
 * Data loader using queries for previous evaluator results with type mapping (datasets).
 *
 * @author darklight
 */
class DatasetLoader {

	private final ResultFacade resultFacade;

	DatasetLoader(ResultFacade resultFacade) {
		this.resultFacade = resultFacade;
	}

	DataSet loadDataset(ProcessingConfiguration pConf) throws DAOException {
		return resultFacade.query(pConf.getDatasetId());
	}
}
