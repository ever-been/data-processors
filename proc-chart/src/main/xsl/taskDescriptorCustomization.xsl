<?xml version="1.0"?>

<xsl:stylesheet
	version="1.0"
	xmlns:td="http://been.d3s.mff.cuni.cz/task-descriptor"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
>
	<xsl:output omit-xml-declaration="no" indent="yes"/>

	<xsl:param name="name" select="'proc-chart'"/>
	<xsl:param name="bpkId" select="'proc-chart'"/>

	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="td:taskDescriptor/@name">
		<xsl:attribute name="name">
			<xsl:value-of select="$name"/>
		</xsl:attribute>
	</xsl:template>

	<xsl:template match="td:taskDescriptor/@bpkId">
		<xsl:attribute name="bpkId">
			<xsl:value-of select="$bpkId"/>
		</xsl:attribute>
	</xsl:template>
</xsl:stylesheet>
