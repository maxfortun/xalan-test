<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!-- CaseName: numbering15 -->
  <!-- Author: David Marston -->
  <!-- Purpose: Test of greek-numeral "traditional" sequence. -->
  <!-- SpecCitation: Rec="XSLT" Version="1.0" type="OASISptr1" place="id(number)/ulist[2]/item[3]/p[1]/text()[6]" -->
  <!-- SpecCitation: Rec="XSLT" Version="1.0" type="OASISptr1" place="id(number)/ulist[1]/item[3]/p[1]/text()[1]" -->
  <!-- SpecCitation: Rec="XSLT" Version="1.0" type="OASISptr1" place="id(convert)/ulist[1]/item[6]/p[1]/text()[1]" -->
  <!-- SpecCitation: Rec="XSLT" Version="1.0" type="OASISptr1" place="id(convert)/p[5]/text()[6]" -->
  <!-- Scenario: operation="standard-HTML" -->
  <!-- Discretionary: number-greek-trad="true" -->

<xsl:output method="html" encoding="ISO-8859-7"/>

<xsl:template match="doc">
  <out>
    <xsl:apply-templates/>
  </out>
</xsl:template>

<xsl:template match="note">
  <xsl:number level="any" from="chapter" format="&#x03b1;" letter-value="traditional"/>
  <xsl:apply-templates/>
</xsl:template>

</xsl:stylesheet>