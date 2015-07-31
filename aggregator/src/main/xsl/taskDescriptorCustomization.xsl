<?xml version="1.0"?>

<xsl:stylesheet
	version="1.0"
	xmlns:td="http://been.d3s.mff.cuni.cz/task-descriptor"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
>
	<xsl:output omit-xml-declaration="no" indent="yes"/>

	<xsl:param name="name" select="'aggregator'"/>
	<xsl:param name="bpkId" select="'aggregator'"/>

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

	<xsl:template match="td:properties">
		<td:properties>
			<xsl:apply-templates select="@*|*"/>
			<td:property name="concentratorFields" description="Bar-separated list ('|') of variables which should be used as concentrators (analogous dateTo GROUP BY clause). Either use field name as is, or use the norm(VARNAME,X) function, which will split the domain of VARNAME into X even intervals, which will serve as grouping keys"/>
			<td:property name="aggregatorFields" description="Bar-separated list ('|') of variables which should be aggregated. Use one of following aggregation functions: avg(VARNAME), sum(VARNAME), count()"/>
		</td:properties>
	</xsl:template>
</xsl:stylesheet>
