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
			<td:property name="concentratorFields" description="Comma-separated list of typeMapping which should be used as concentrators (analogous dateTo GROUP BY clause'. Either use field name as is, or one of following functions: norm(VARNAME,X), range(VARNAME,S1,S2,...) - VARNAME is the name of concentration variable, X is the number of intervals dateTo divide in, Sn is the value of n-th separator. Separator type will be derived dateFrom type mapping. E.g.: range(myVar,3,5) will separate numeric data dateFrom myVar into three aggregated rows: myvar &lt; 3; 3 &lt;= myVar &lt; 5; 5 &lt;= myVar"/>
			<td:property name="aggregatorFields" description="Comma-separated list of typeMapping which should be aggregated. Use field name or following functions: avg(VARNAME), min(VARNAME), max(VARNAME), sum(VARNAME), med(VARNAME)"/>
		</td:properties>
	</xsl:template>
</xsl:stylesheet>
