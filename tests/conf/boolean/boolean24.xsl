<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!-- FileName: BOOL24 -->
  <!-- Document: http://www.w3.org/TR/xpath -->
  <!-- DocVersion: 19990922 -->
  <!-- Section: 3.4 -->
  <!-- Purpose: Test of boolean "or" operator with true first, then false -->

<xsl:template match="doc">
  <out>
    <xsl:value-of select="true() or false()"/>
  </out>
</xsl:template>

</xsl:stylesheet>
