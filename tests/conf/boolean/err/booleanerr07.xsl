<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!-- FileName: BOOLerr07 -->
  <!-- Document: http://www.w3.org/TR/xpath -->
  <!-- DocVersion: 19991116 -->
  <!-- Section: 4.3 -->
  <!-- Purpose: Test of true() with an argument. -->
  <!-- ExpectedException: expected zero arguments -->

<xsl:template match="/">
  <out>
    <xsl:value-of select="true(doc)"/>
  </out>
</xsl:template>

</xsl:stylesheet>
