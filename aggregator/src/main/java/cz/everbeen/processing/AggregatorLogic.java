package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.results.DataSetResult;
import cz.cuni.mff.d3s.been.results.PrimitiveType;
import cz.cuni.mff.d3s.been.results.ResultMapping;
import cz.everbeen.processing.aggregate.AggregatorConfiguration;
import cz.everbeen.processing.configuration.ProcessingConfiguration;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Task that exports selected data to JSON.
 *
 * @author darklight
 */
public class AggregatorLogic implements DataProcessorLogic {

	private static final SimpleDateFormat FILENAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");

	@Override
	public String getName() {
		return "aggregate";
	}

	@Override
	public ProcessingConfiguration createConfig() {
		return new AggregatorConfiguration();
	}

	@Override
	public EvaluatorResult process(Collection<Map<String, Object>> results, ResultMapping mapping, ProcessingConfiguration conf) throws DataProcessingException {

		final AggregatorConfiguration aConf = (AggregatorConfiguration) conf;

		final ReductorFactories reductorFactories = new ReductorFactories(((AggregatorConfiguration) conf).getConcentratorFields(), mapping);
		reductorFactories.init();

		final Map<String, Concentrator> concentratorMap = createConcentrators(aConf, reductorFactories);
		initConcentrators(concentratorMap, results);

		final Map<String, Aggregator> aggregatorMap = createAggregators(aConf, reductorFactories);
		initAggregators(aggregatorMap, results);

		final Map<String, Map<String, Object>> aggregatedResults = new TreeMap<String, Map<String, Object>>();
		final RecordUtils recordUtils = RecordUtils.newInstance();



		// DO MAIN LOOP
		for (Map<String, Object> record: results) {
			final SortedMap<String, Object> reducedRecord = new TreeMap<String, Object>();
			// write values for all grouping cols
			for (Concentrator c: concentratorMap.values()) c.reduce(record, reducedRecord);
			final String reducedRecordKey = recordUtils.flatRecordToKey(reducedRecord);
			aggregatedResults.put(reducedRecordKey, reducedRecord); // add if unique, replaces don't matter
			for (Aggregator a: aggregatorMap.values()) a.add(record, reducedRecord);
		}
		// END MAIN LOOP



		// read out aggregators and dump their values into rows associated by grouping col value
		for (Map<String, Object> reducedRecord: aggregatedResults.values()) {
			for (Aggregator a: aggregatorMap.values()) {
				a.writeAggregatedValue(reducedRecord);
			}
		}

		final ByteArrayOutputStream bao = new ByteArrayOutputStream();
		final JsonGenerator jgen;
		try {
			jgen = new JsonFactory().createJsonGenerator(new BufferedOutputStream(bao), JsonEncoding.UTF8);
		} catch (IOException e) {
			throw new DataProcessingException("Could not set up JSON generator", e);
		}
		final ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(jgen, aggregatedResults.values());
		} catch (IOException e) {
			throw new DataProcessingException("Failed to serialize result object", e);
		}
		try {
			jgen.close();
		} catch (IOException e) {
			throw new DataProcessingException("Could not close result stream", e);
		}
		final Date creationDate = new Date();
		final DataSetResult dataSetResult = new DataSetResult();
		dataSetResult.setPreserializedResultMapping(
				calculateTargetMapping(concentratorMap, aggregatorMap).preSerialize()
		);
		dataSetResult.setData(bao.toByteArray());
		dataSetResult.setMimeType(EvaluatorResult.MIME_TYPE_JSON);
		dataSetResult.setTimestamp(creationDate.getTime());
		dataSetResult.setFilename("aggregate_" + FILENAME_DATE_FORMAT.format(creationDate) + ".json");

		return dataSetResult;
	}

	private ResultMapping calculateTargetMapping(
			Map<String, Concentrator> concentrators,
			Map<String, Aggregator> aggregators
	) {
		final Map<String, PrimitiveType> pTypes = new TreeMap<String, PrimitiveType>();
		for (Concentrator c: concentrators.values()) {
			pTypes.put(c.getColName(), PrimitiveType.STRING);
		}
		for (Aggregator a: aggregators.values()) {
			pTypes.put(a.getColName(), a.getColType());
		}
		return ResultMapping.fromPtypes(pTypes);
	}

	private Map<String, Concentrator> createConcentrators(AggregatorConfiguration aConf, ReductorFactories reductorFactories) throws DataProcessingException {
		final Map<String, Concentrator> concentratorMap = new TreeMap<String, Concentrator>();
		for (String concentratorExpr: aConf.getConcentratorFields()) {
			concentratorMap.put(
					concentratorExpr,
					reductorFactories.loadConcentrator(concentratorExpr)
			);
		}
		return concentratorMap;
	}

	private void initConcentrators(Map<String, Concentrator> concentrators, Collection<Map<String, Object>> resultSet) {
		for(Concentrator c: concentrators.values()) {
			c.initialize(resultSet);
		}
	}

	private void initAggregators(Map<String, Aggregator> aggregators, Collection<Map<String, Object>> resultSet) {
		for (Aggregator a: aggregators.values()) {
			a.initialize(resultSet);
		}
	}

	private Map<String, Aggregator> createAggregators(AggregatorConfiguration aConf, ReductorFactories reductorFactories) throws DataProcessingException {
		final Map<String, Aggregator> aggregatorMap = new TreeMap<String, Aggregator>();
		for (String aggregatorExpr: aConf.getAggregatorFields()) {
			aggregatorMap.put(
					aggregatorExpr,
					reductorFactories.loadAggregator(aggregatorExpr)
			);
		}
		return aggregatorMap;
	}
}
