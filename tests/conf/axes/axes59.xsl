<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!-- FileName: axes59 -->
  <!-- Document: http://www.w3.org/TR/xpath -->
  <!-- DocVersion: 19991116 -->
  <!-- Section: 2.2 Axes-->
  <!-- Creator: David Marston -->
  <!-- Purpose: Check the namespace axis. -->

<xsl:template match="/">
  <out>
    <xsl:apply-templates/>
  </out>
</xsl:template>

<xsl:template match="doc">
  <xsl:for-each select="namespace::*">
    <xsl:text>&#010;</xsl:text>
    <xsl:value-of select="name(.)"/>=<xsl:value-of select="."/><xsl:text>,</xsl:text>
  </xsl:for-each>
</xsl:template>

</xsl:stylesheet>