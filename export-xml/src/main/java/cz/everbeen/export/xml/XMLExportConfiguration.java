package cz.everbeen.export.xml;

import cz.everbeen.processing.configuration.ProcessingConfiguration;
import cz.everbeen.processing.configuration.TaskProperty;

/**
 * Configuration derivate for the {@link cz.everbeen.export.xml.XMLExportLogic}
 *
 * @author darklight
 */
public class XMLExportConfiguration extends ProcessingConfiguration {

	/** Whether the serializer should serialize values into elements rather than attributes (disabled by default) */
	@TaskProperty(name="elementMode")
	private Boolean elementMode = Boolean.FALSE;
}
