<xsl:transform
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 version="1.0"
>

  <!-- Test FileName: mk047.xsl -->
  <!-- Source Attribution: 
       This test was written by Michael Kay and is taken from 
       'XSLT Programmer's Reference' published by Wrox Press Limited in 2000;
       ISBN 1-861003-12-9; copyright Wrox Press Limited 2000; all rights reserved. 
       Now updated in the second edition (ISBN 1861005067), http://www.wrox.com.
       No part of this book may be reproduced, stored in a retrieval system or 
       transmitted in any form or by any means - electronic, electrostatic, mechanical, 
       photocopying, recording or otherwise - without the prior written permission of 
       the publisher, except in the case of brief quotations embodied in critical articles or reviews.
  -->
  <!-- Example: authors.xml, replace.xsl -->
  <!-- Chapter/Page: 7-519 -->
  <!-- Purpose: Replace all occurrences of a string -->
  
<xsl:param name="replace" select="'author'"/>
<xsl:param name="by" select="'****'"/>

<xsl:template name="do-replace">
     <xsl:param name="text"/>
   <xsl:choose>
   <xsl:when test="contains($text, $replace)">
      <xsl:value-of select="substring-before($text, $replace)"/>
      <xsl:value-of select="$by"/>
      <xsl:call-template name="do-replace">
         <xsl:with-param name="text"
                         select="substring-after($text, $replace)"/>
      </xsl:call-template>
   </xsl:when>
   <xsl:otherwise>
      <xsl:value-of select="$text"/>
   </xsl:otherwise>
   </xsl:choose>

</xsl:template>

<xsl:template match="*">
    <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates/>
    </xsl:copy>
</xsl:template>

<xsl:template match="text()">
    <xsl:call-template name="do-replace">
        <xsl:with-param name="text" select="."/>
    </xsl:call-template>
</xsl:template>

</xsl:transform>
