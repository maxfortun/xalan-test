/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2000 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xalan" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 2000, Lotus
 * Development Corporation., http://www.lotus.com.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

/*
 *
 * StreamSourceAPITest.java
 *
 */
package org.apache.qetest.trax.stream;

// Support for test reporting and harness classes
import org.apache.qetest.*;
import org.apache.qetest.trax.*;
import org.apache.qetest.xsl.*;

// Import all relevant TRAX packages
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;

// Needed SAX, DOM, JAXP classes

// java classes
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.Properties;

//-------------------------------------------------------------------------

/**
 * API Coverage test for the StreamSource class of TRAX..
 * @author shane_curcuru@lotus.com
 * @version $Id$
 */
public class StreamSourceAPITest extends XSLProcessorTestBase
{

    /**
     * Provides nextName(), currentName() functionality for tests 
     * that may produce any number of output files.
     */
    protected OutputNameManager outNames;

    /** 
     * Information about an xsl/xml file pair for transforming.  
     * Public members include inputName (for xsl); xmlName; goldName; etc.
     */
    protected XSLTestfileInfo testFileInfo = new XSLTestfileInfo();

    /** Subdirectory under test\tests\api for our xsl/xml files.  */
    public static final String TRAX_STREAM_SUBDIR = "trax" + File.separator + "stream";


    /** Just initialize test name, comment, numTestCases. */
    public StreamSourceAPITest()
    {
        numTestCases = 2;  // REPLACE_num
        testName = "StreamSourceAPITest";
        testComment = "API Coverage test for the StreamSource class of TRAX.";
    }


    /**
     * Initialize this test - Set names of xml/xsl test files.
     *
     * @param p Properties to initialize from (if needed)
     * @return false if we should abort the test; true otherwise
     */
    public boolean doTestFileInit(Properties p)
    {
        // NOTE: 'reporter' variable is already initialized at this point

        // Used for all tests; just dump files in trax subdir
        File outSubDir = new File(outputDir + File.separator + TRAX_STREAM_SUBDIR);
        if (!outSubDir.mkdirs())
            reporter.logWarningMsg("Could not create output dir: " + outSubDir);
        // Initialize an output name manager to that dir with .out extension
        outNames = new OutputNameManager(outputDir + File.separator + TRAX_STREAM_SUBDIR
                                         + File.separator + testName, ".out");

        String testBasePath = inputDir 
                              + File.separator 
                              + TRAX_STREAM_SUBDIR
                              + File.separator;
        String goldBasePath = goldDir 
                              + File.separator 
                              + TRAX_STREAM_SUBDIR
                              + File.separator;

        testFileInfo.inputName = testBasePath + "StreamImpIncl.xsl";
        testFileInfo.xmlName = testBasePath + "StreamImpIncl.xml";
        testFileInfo.goldName = goldBasePath + "StreamImpIncl.out";
        try
        {
            TransformerFactory tf = TransformerFactory.newInstance();
            if (!(tf.getFeature(StreamSource.FEATURE)
                  && tf.getFeature(StreamResult.FEATURE)))
            {   // The rest of this test relies on Streams
                reporter.logErrorMsg("Streams not supported! Some tests may be invalid!");
            }
        }
        catch (Throwable t)
        {
            reporter.checkFail(
                "Problem creating factory; Some tests may be invalid!");
            reporter.logThrowable(reporter.ERRORMSG, t,
                                  "Problem creating factory; Some tests may be invalid!");
        }

        return true;
    }


    /**
     * Basic API coverage, constructor and set/get methods.
     *
     * @return false if we should abort the test; true otherwise
     */
    public boolean testCase1()
    {
        reporter.testCaseInit("Basic API coverage, constructor and set/get methods");

        // Default no-arg ctor sets nothing
        StreamSource defaultStream = new StreamSource();
        reporter.checkObject(defaultStream.getByteStream(), null, "Default StreamSource should have null ByteStream");
        reporter.checkObject(defaultStream.getCharacterStream(), null, "Default StreamSource should have null CharacterStream");
        reporter.check(defaultStream.getPublicId(), null, "Default StreamSource should have null PublicId");
        reporter.check(defaultStream.getSystemId(), null, "Default StreamSource should have null SystemId");

        byte[] bytes = { 0, 0, 0, 0 };  // just a few zeroes, not really needed
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        StreamSource byteSource1 = new StreamSource(bais);
        reporter.checkObject(byteSource1.getByteStream(), bais, "StreamSource(is) has ByteStream " + byteSource1.getByteStream());
        reporter.checkObject(byteSource1.getCharacterStream(), null, "StreamSource(is) should have null CharacterStream");
        reporter.check(byteSource1.getPublicId(), null, "StreamSource(is) should have null PublicId");
        reporter.check(byteSource1.getSystemId(), null, "StreamSource(is) should have null SystemId");

        StreamSource byteSource2 = new StreamSource(bais, "some-system-id");
        reporter.checkObject(byteSource2.getByteStream(), bais, "StreamSource(is, sysID) has ByteStream " + byteSource2.getByteStream());
        reporter.checkObject(byteSource2.getCharacterStream(), null, "StreamSource(is, sysID) should have null CharacterStream");
        reporter.check(byteSource2.getPublicId(), null, "StreamSource(is, sysID) should have null PublicId");
        reporter.check(byteSource2.getSystemId(), "some-system-id", "StreamSource(is, sysID) has SystemId " + byteSource2.getSystemId());

        StringReader strReader = new StringReader("this is not your parent's XML data");
        StreamSource readerSource1 = new StreamSource(strReader);
        reporter.checkObject(readerSource1.getByteStream(), null, "StreamSource(reader) should have null ByteStream");
        reporter.checkObject(readerSource1.getCharacterStream(), strReader, "StreamSource(reader) has CharacterStream " + readerSource1.getCharacterStream());
        reporter.check(readerSource1.getPublicId(), null, "StreamSource(reader) should have null PublicId");
        reporter.check(readerSource1.getSystemId(), null, "StreamSource(reader) should have null SystemId");

        StreamSource readerSource2 = new StreamSource(strReader, "some-system-id");
        reporter.checkObject(readerSource2.getByteStream(), null, "StreamSource(reader, sysID) should have null ByteStream");
        reporter.checkObject(readerSource2.getCharacterStream(), strReader, "StreamSource(reader, sysID) has CharacterStream " + readerSource2.getCharacterStream());
        reporter.check(readerSource2.getPublicId(), null, "StreamSource(reader, sysID) should have null PublicId");
        reporter.check(readerSource2.getSystemId(), "some-system-id", "StreamSource(reader, sysID) has SystemId " + readerSource2.getSystemId());

        StreamSource sysIDStream = new StreamSource("real-system-id");
        reporter.checkObject(sysIDStream.getByteStream(), null, "StreamSource(sysID) should have null ByteStream");
        reporter.checkObject(sysIDStream.getCharacterStream(), null, "StreamSource(sysID) should have null CharacterStream");
        reporter.check(sysIDStream.getPublicId(), null, "StreamSource(sysID) should have null PublicId");
        reporter.check(sysIDStream.getSystemId(), "real-system-id", "StreamSource(sysID) has SystemId " + sysIDStream.getSystemId());

        StreamSource wackyStream = new StreamSource();
        wackyStream.setByteStream(bais);
        InputStream gotStream = wackyStream.getByteStream();
        reporter.checkObject(gotStream, bais, "set/getByteStream API coverage");

        wackyStream.setCharacterStream(strReader);
        Reader gotReader = wackyStream.getCharacterStream();
        reporter.checkObject(gotReader, strReader, "set/getCharacterStream API coverage");

        wackyStream.setSystemId("new-system-id");
        String gotSystemId = wackyStream.getSystemId();
        reporter.check(gotSystemId, "new-system-id", "set/getSystemId API coverage");

        wackyStream.setPublicId("new-public-id");
        String gotPublicId = wackyStream.getPublicId();
        reporter.check(gotPublicId, "new-public-id", "set/getPublicId API coverage");

        reporter.testCaseClose();
        return true;
    }


    /**
     * Basic functionality of StreamSources.
     *
     * @return false if we should abort the test; true otherwise
     */
    public boolean testCase2()
    {
        reporter.testCaseInit("Basic functionality of StreamSources");

        TransformerFactory factory = null;
        String xslID = testFileInfo.inputName;
        String xmlID = testFileInfo.xmlName;
        try
        {
            factory = TransformerFactory.newInstance();
            // Create URLs for the filenames
            // What's the simplest way to do this?!? i.e. as a solution 
            //  to the general problem of having a String denoting a 
            //  path/filename, that may be absolute or relative,
            //  and may or may not exist, and to get a valid file: URL?
            File f = new File(testFileInfo.inputName);
            String tmp = f.getCanonicalPath();
            xslID = (new URL("file", "", tmp)).toExternalForm();
            reporter.logTraceMsg("URL-ized names(1) xslID=" + xslID + ", xmlID=" + xmlID);
            f = new File(testFileInfo.xmlName);
            tmp = f.getCanonicalPath();
            xmlID = (new URL("file", "", tmp)).toString();
            reporter.logTraceMsg("URL-ized names(2) xslID=" + xslID + ", xmlID=" + xmlID);
        }
        catch (Throwable t)
        {
            reporter.checkFail("Problem creating factory; can't continue testcase");
            reporter.logThrowable(reporter.ERRORMSG, t,
                                  "Problem creating factory; can't continue testcase");
            return true;
        }

        String outName1 = outNames.nextName();
        String outName2 = outNames.nextName();
        try
        {
            // Verify we can do basic transforms with readers/streams
            reporter.logTraceMsg("Create stream sources and setSystemId separately");
            InputStream xslStream1 = new FileInputStream(testFileInfo.inputName);
            Source xslSource1 = new StreamSource(xslStream1);
            xslSource1.setSystemId(xslID);
            InputStream xmlStream1 = new FileInputStream(testFileInfo.xmlName);
            Source xmlSource1 = new StreamSource(xmlStream1);
            xmlSource1.setSystemId(xmlID);
            FileOutputStream fos1 = new FileOutputStream(outName1);
            Result result1 = new StreamResult(fos1);

            reporter.logTraceMsg("Transform into " + outName1);
            Templates templates1 = factory.newTemplates(xslSource1);
            Transformer transformer1 = templates1.newTransformer();
            transformer1.transform(xmlSource1, result1);
        }
        catch (Throwable t)
        {
            reporter.checkFail("Problem with transform-streams(1)");
            reporter.logThrowable(reporter.ERRORMSG, t, "Problem with transform-streams(1)");
        }
        try
        {
            reporter.logTraceMsg("Create stream sources with setSystemId in ctor");
            InputStream xslStream2 = new FileInputStream(testFileInfo.inputName);
            Source xslSource2 = new StreamSource(xslStream2, xslID);
            InputStream xmlStream2 = new FileInputStream(testFileInfo.xmlName);
            Source xmlSource2 = new StreamSource(xmlStream2, xmlID);
            FileOutputStream fos2 = new FileOutputStream(outName2);
            Result result2 = new StreamResult(fos2);

            reporter.logTraceMsg("Transform into " + outName2);
            Templates templates2 = factory.newTemplates(xslSource2);
            Transformer transformer2 = templates2.newTransformer();
            transformer2.transform(xmlSource2, result2);
            reporter.checkAmbiguous("@todo also verify one against a real gold file");
            fileChecker.check(reporter, 
                              new File(outName2), 
                              new File(outName1), 
                              "transform to streams with both settings of systemId");
        }
        catch (Throwable t)
        {
            reporter.checkFail("Problem with transform-streams(1)");
            reporter.logThrowable(reporter.ERRORMSG, t, "Problem with transform-streams(1)");
        }

        try
        {
            // Do a transform without systemId set
            // Note: is affected by user.dir property; if we're 
            //  already in the correct place, this won't be different
            try
            {
                reporter.logTraceMsg("System.getProperty(user.dir) = " + System.getProperty("user.dir"));
            }
            catch (SecurityException e) // in case of Applet context
            {
                reporter.logTraceMsg("System.getProperty(user.dir) threw SecurityException");
            }
            reporter.logTraceMsg("Create stream sources without setSystemId set");
            InputStream xslStream3 = new FileInputStream(testFileInfo.inputName);
            Source xslSource3 = new StreamSource(xslStream3);
            InputStream xmlStream3 = new FileInputStream(testFileInfo.xmlName);
            Source xmlSource3 = new StreamSource(xmlStream3);
            String outName3 = outNames.nextName();
            FileOutputStream fos3 = new FileOutputStream(outName3);
            Result result3 = new StreamResult(fos3);

            reporter.logTraceMsg("Transform into " + outName3);
            Templates templates3 = factory.newTemplates(xslSource3);
            Transformer transformer3 = templates3.newTransformer();
            transformer3.transform(xmlSource3, result3);
        }
        catch (Throwable t)
        {
            reporter.checkFail("Problem with transform-streams(1)");
            reporter.logThrowable(reporter.ERRORMSG, t, "Problem with transform-streams(1)");
        }

        reporter.testCaseClose();
        return true;
    }

    /**
     * Convenience method to print out usage information - update if needed.  
     * @return String denoting usage of this test class
     */
    public String usage()
    {
        return ("Common [optional] options supported by StreamSourceAPITest:\n"
                + "(Note: assumes inputDir=.\\tests\\api)\n"
                + "REPLACE_any_new_test_arguments\n"
                + super.usage());   // Grab our parent classes usage as well
    }


    /**
     * Main method to run test from the command line - can be left alone.  
     * @param args command line argument array
     */
    public static void main(String[] args)
    {

        StreamSourceAPITest app = new StreamSourceAPITest();

        app.doMain(args);
    }
}