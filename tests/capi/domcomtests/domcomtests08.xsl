<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
				xmlns:cnn="http://www.cnn.com">
	

<xsl:output method="xml"/>

  <!-- FileName:  -->
  <!-- Document: http://www.w3.org/TR/xpath -->
  <!-- DocVersion: 19991116 -->
  <!-- Section: 2.2 -->
  <!-- Purpose: Test has no exceptions. -->

<xsl:template match="/">
<root>
<data1 attr1="hello" attr2="goodbye">
<data2 attr1="goodbye" attr2="hello">
<data3 attr1="hello" attr2="goodbye">
<data4 attr1="goodbye" attr2="hello">
<data5 attr1="hello" attr2="goodbye">
<data6 attr1="goodbye" attr2="hello">
<data7 attr1="hello" attr2="goodbye">
<data8 attr1="goodbye" attr2="hello">
<data9 attr1="hello" attr2="bad">
<data10 attr1="goodbye" attr2="hello">
<data11 attr1="hello" attr2="goodbye">
<data12 attr1="goodbye" attr2="hello">
<data13 attr1="What in the world">
<data14 attr1="olleh" xyz:attr2="eybdoog" xmlns:xyz="http://www.xyz.com">Duck</data14>
</data13>
</data12>
</data11>
</data10>
</data9>
</data8>
</data7>
</data6>
</data5>
</data4>
</data3>
</data2>
</data1>
</root>
</xsl:template>

</xsl:stylesheet>