package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.core.persistence.EntityID;
import cz.cuni.mff.d3s.been.persistence.DAOException;
import cz.cuni.mff.d3s.been.persistence.QueryBuilder;
import cz.cuni.mff.d3s.been.results.ResultMapping;
import cz.cuni.mff.d3s.been.taskapi.DataSet;
import cz.cuni.mff.d3s.been.taskapi.ResultFacade;
import cz.cuni.mff.d3s.been.taskapi.TaskException;
import cz.cuni.mff.d3s.been.util.JSONUtils;
import cz.cuni.mff.d3s.been.util.JsonException;
import cz.everbeen.processing.configuration.ProcessingConfiguration;

/**
 * Data loader using standard queries into result collections in the persistence layers.
 *
 * @author darklight
 */
class QueryLoader {

	private final ResultFacade resultFacade;

	QueryLoader(ResultFacade resultFacade) {
		this.resultFacade = resultFacade;
	}

	DataSet loadDataset(ProcessingConfiguration pConf) throws TaskException, DAOException {
		final ResultMapping resultMapping = new ResultMapping(
			pConf.parseTypeMapping(),
			pConf.parseAliasMapping()
		);

		final QueryBuilder qb = new QueryBuilder().on(new EntityID().withKind("result").withGroup(pConf.getGroupId()));
		if (pConf.getDateFrom() != null) qb.with("created").above(pConf.getDateFrom());
		if (pConf.getDateTo() != null) qb.with("created").below(pConf.getDateTo());
		if (pConf.getTaskId() != null) qb.with("taskId", pConf.getTaskId());
		if (pConf.getContextId() != null) qb.with("contextId", pConf.getContextId());
		if (pConf.getBenchmarkId() != null) qb.with("benchmarkId", pConf.getBenchmarkId());
		for (String key: resultMapping.getTypeMapping().keySet()) {
			qb.retrieving(key);
		}

		return new DataSet(
				resultMapping,
				resultFacade.query(qb.fetch(), resultMapping)
		);
	}
}
