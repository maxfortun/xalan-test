<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!-- FileName: MATHerr12 -->
  <!-- Document: http://www.w3.org/TR/xpath -->
  <!-- DocVersion: 19991116 -->
  <!-- Section: 4.4 -->
  <!-- Purpose: Test of ceiling() with zero arguments. -->
  <!-- ExpectedException: one argument expected -->

<xsl:template match="/">
  <out>
    <xsl:value-of select="ceiling()"/>
  </out>
</xsl:template>

</xsl:stylesheet>
