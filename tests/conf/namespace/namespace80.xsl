<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!-- FileName: namespace80 -->
  <!-- Document: http://www.w3.org/TR/xslt -->
  <!-- DocVersion: 19991116 -->
  <!-- Section: 7.1.2 Creating Elements -->
  <!-- Creator: David Marston -->
  <!-- Purpose: Prefixed xmlns declaration and same-prefixed name; no namespace attrib; default set. -->
  <!-- NOTE: Processor developers could legitimately disagree about where the default name
     has to be reset in the result. It must be in effect for yyy, but could be for foo
     as well. The spec doesn't address this point. -->

<xsl:template match = "/">
  <out>
    <xsl:element name="p2:foo" xmlns="other.com" xmlns:p2="barz.com">
      <yyy/>
    </xsl:element>
  </out>
</xsl:template>

</xsl:stylesheet>