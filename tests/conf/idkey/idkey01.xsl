<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!-- FileName: idkey01 -->
  <!-- Document: http://www.w3.org/TR/xslt -->
  <!-- DocVersion: 19991116 -->
  <!-- Section: 12.2 -->
  <!-- Purpose: Build links using keys and generate-id(). -->

<xsl:output indent="yes"/>

<xsl:key name="titles" match="div" use="title"/>
<xsl:key name="id" match="@id" use="@id"/>

<xsl:template match="doc">
  <P>Reference numbers should match the titles, links should work.</P>
  <xsl:for-each select="div">
    <HR/>
    <H1 id="{generate-id(.)}">
      <xsl:number level="multiple" count="div" format="1.1. "/>
      <xsl:value-of select="title"/></H1>
    <xsl:apply-templates/>
  </xsl:for-each>
</xsl:template>

<xsl:template match="p">
  <P><xsl:apply-templates/></P>
</xsl:template>

<xsl:template match="divref">
  <A href="#{generate-id(key('titles', .))}">
    <xsl:for-each select="key('titles', .)">
      <xsl:number level="multiple" count="div" format="1.1. "/>
    </xsl:for-each>
    <xsl:value-of select="."/>
  </A>
</xsl:template> 

</xsl:stylesheet>
