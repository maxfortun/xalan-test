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
 * TransformerFactoryAPITest.java
 *
 */
package org.apache.qetest.trax;

// Support for test reporting and harness classes
import org.apache.qetest.*;
import org.apache.qetest.xsl.*;

// Import all relevant TRAX packages
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

// Needed SAX, DOM, JAXP classes

// java classes
import java.io.File;
import java.util.Properties;

//-------------------------------------------------------------------------

/**
 * API Coverage test for TransformerFactory class of TRAX.
 * @author shane_curcuru@lotus.com
 * @version $Id$
 */
public class TransformerFactoryAPITest extends XSLProcessorTestBase
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
    public static final String TRAX_SUBDIR = "trax";

    /** Cached system property.  */
    protected String cachedSysProp = null;

    /** System property name, from TransformerFactory (why is it private there?).  */
    public static final String defaultPropName = "javax.xml.transform.TransformerFactory";

    /** System property name for Xalan-J 2.x impl.  */
    public static final String XALAN_CLASSNAME = "org.apache.xalan.processor.TransformerFactoryImpl";

    /** Just initialize test name, comment, numTestCases. */
    public TransformerFactoryAPITest()
    {
        numTestCases = 5;  // REPLACE_num
        testName = "TransformerFactoryAPITest";
        testComment = "API Coverage test for TransformerFactory class of TRAX";
    }


    /**
     * Initialize this test - Set names of xml/xsl test files,
     * cache system property javax.xml.transform.TransformerFactory.  
     *
     * @param p Properties to initialize from (if needed)
     * @return false if we should abort the test; true otherwise
     */
    public boolean doTestFileInit(Properties p)
    {
        // NOTE: 'reporter' variable is already initialized at this point

        // Used for all tests; just dump files in trax subdir
        File outSubDir = new File(outputDir + File.separator + TRAX_SUBDIR);
        if (!outSubDir.mkdirs())
            reporter.logWarningMsg("Could not create output dir: " + outSubDir);
        // Initialize an output name manager to that dir with .out extension
        outNames = new OutputNameManager(outputDir + File.separator + TRAX_SUBDIR
                                         + File.separator + testName, ".out");

        String testBasePath = inputDir 
                              + File.separator 
                              + TRAX_SUBDIR
                              + File.separator;
        String goldBasePath = goldDir 
                              + File.separator 
                              + TRAX_SUBDIR
                              + File.separator;

        testFileInfo.inputName = testBasePath + "TransformerFactoryAPITest.xsl";
        testFileInfo.xmlName = testBasePath + "TransformerFactoryAPITest.xml";
        testFileInfo.goldName = goldBasePath + "TransformerFactoryAPITest.out";

        // Cache the system property
        cachedSysProp = System.getProperty(defaultPropName);
        return true;
    }


    /**
     * Cleanup this test - reset cached system property.  
     *
     * @param p Properties to initialize from (if needed)
     * @return false if we should abort the test; true otherwise
     */
    public boolean doTestFileClose(Properties p)
    {
        if (cachedSysProp == null)
            System.getProperties().remove(defaultPropName);
        else
            System.getProperties().put(defaultPropName, cachedSysProp);
        
        return true;
    }


    /**
     * Coverage tests for factory pattern API's.
     *
     * @return false if we should abort the test; true otherwise
     */
    public boolean testCase1()
    {
        reporter.testCaseInit("Coverage tests for factory pattern API's");

        // protected TransformerFactory(){} not normally accessible - not tested
        // public static TransformerFactory newInstance()
        TransformerFactory factory = null;
        // test when system property is user-set (i.e. whatever we started with)
        try
        {
            reporter.logStatusMsg("System property " + defaultPropName 
                                  + " is: " + System.getProperty(defaultPropName));
            factory = TransformerFactory.newInstance();
            // No verification: just log what happened for user to see
            reporter.logStatusMsg("factory.newInstance() is: " + factory.toString());
        }
        catch (Throwable t)
        {
            reporter.logStatusMsg("factory.newInstance() threw: " + t.toString());
        }

        // test when system property is null
        try
        {
            System.getProperties().remove(defaultPropName);
            reporter.logStatusMsg("System property " + defaultPropName 
                                  + " is: " + System.getProperty(defaultPropName));
            factory = TransformerFactory.newInstance();
            reporter.logStatusMsg("factory.newInstance() is: " + factory.toString());
        }
        catch (Throwable t)
        {
            reporter.logStatusMsg("factory.newInstance() threw: " + t.toString());
        }

        // test when system property is a bogus name
        try
        {
            System.getProperties().put(defaultPropName, "this.class.does.not.exist");
            reporter.logStatusMsg("System property " + defaultPropName 
                                  + " is: " + System.getProperty(defaultPropName));
            factory = TransformerFactory.newInstance();
            reporter.checkFail("factory.newInstance() with bogus name got: " + factory.toString());
        }
        catch (Throwable t)
        {
            reporter.checkPass("factory.newInstance() with bogus name properly threw: " + t.toString());
            // Could also verify specific type of exception
        }

        // test when system property is another kind of classname
        try
        {
            System.getProperties().put(defaultPropName, "java.lang.String");
            reporter.logStatusMsg("System property " + defaultPropName 
                                  + " is: " + System.getProperty(defaultPropName));
            factory = TransformerFactory.newInstance();
            reporter.checkFail("factory.newInstance() with bogus class got: " + factory.toString());
        }
        catch (Throwable t)
        {
            reporter.checkPass("factory.newInstance() with bogus class properly threw: " + t.toString());
            // Could also verify specific type of exception
        }

        // test when system property is Xalan-J 2.x's implementation
        try
        {
            // This should come last so it will stay set for the rest of the test
            System.getProperties().put(defaultPropName, XALAN_CLASSNAME);
            reporter.logStatusMsg("System property " + defaultPropName 
                                  + " is: " + System.getProperty(defaultPropName));
            factory = TransformerFactory.newInstance();
            reporter.checkPass("factory.newInstance() of Xalan impl is: " + factory.toString());
        }
        catch (Throwable t)
        {
            reporter.checkFail("factory.newInstance() of Xalan impl threw: " + t.toString());
            reporter.logThrowable(reporter.ERRORMSG, t, "factory.newInstance() of Xalan impl threw:");
        }

        reporter.checkAmbiguous("@todo code coverage for findFactory() method");

        reporter.testCaseClose();
        return true;
    }


    /**
     * Coverage tests for newTransformer() API's.
     *
     * @return false if we should abort the test; true otherwise
     */
    public boolean testCase2()
    {
        reporter.testCaseInit("Coverage tests for newTransformer() API's");
        TransformerFactory factory = null;

        try
        {
            reporter.logStatusMsg("System property " + defaultPropName 
                                  + " is: " + System.getProperty(defaultPropName));
            factory = TransformerFactory.newInstance();
            reporter.checkAmbiguous("@todo code coverage for newTransformer(Source) method");
            reporter.checkAmbiguous("@todo code coverage for newTransformer() method");
        }
        catch (Throwable t)
        {
            reporter.logThrowable(reporter.ERRORMSG, t, "newTransformer() tests threw:");
        }


        reporter.testCaseClose();
        return true;
    }


    /**
     * Coverage tests for newTemplates() API's.
     *
     * @return false if we should abort the test; true otherwise
     */
    public boolean testCase3()
    {
        reporter.testCaseInit("Coverage tests for newTemplates() API's");
        TransformerFactory factory = null;

        try
        {
            reporter.logStatusMsg("System property " + defaultPropName 
                                  + " is: " + System.getProperty(defaultPropName));
            factory = TransformerFactory.newInstance();
            reporter.checkAmbiguous("@todo code coverage for newTemplates(Source) method");
        }
        catch (Throwable t)
        {
            reporter.logThrowable(reporter.ERRORMSG, t, "newTemplates() tests threw:");
        }

        reporter.testCaseClose();
        return true;
    }


    /**
     * Coverage tests for getAssociatedStylesheet() API's.
     *
     * @return false if we should abort the test; true otherwise
     */
    public boolean testCase4()
    {
        reporter.testCaseInit("Coverage tests for getAssociatedStylesheet() API's");
        TransformerFactory factory = null;

        try
        {
            reporter.logStatusMsg("System property " + defaultPropName 
                                  + " is: " + System.getProperty(defaultPropName));
            factory = TransformerFactory.newInstance();
            reporter.checkAmbiguous("@todo code coverage for getAssociatedStylesheet(...) method");
        }
        catch (Throwable t)
        {
            reporter.logThrowable(reporter.ERRORMSG, t, "getAssociatedStylesheet() tests threw:");
        }

        reporter.testCaseClose();
        return true;
    }


    /**
     * Coverage tests for get/setURIResolver(), get/setErrorListener() API's.
     *
     * @return false if we should abort the test; true otherwise
     */
    public boolean testCase5()
    {
        reporter.testCaseInit("Coverage tests for get/setURIResolver(), get/setErrorListener() API's");
        TransformerFactory factory = null;
        reporter.logStatusMsg("System property " + defaultPropName 
                              + " is: " + System.getProperty(defaultPropName));
        try
        {
            factory = TransformerFactory.newInstance();
            URIResolver URIRes = factory.getURIResolver();
            reporter.logInfoMsg("factory.getURIResolver() default is: " + URIRes);
            
            LoggingURIResolver loggingURIRes = new LoggingURIResolver(reporter);
            factory.setURIResolver(loggingURIRes);
            reporter.checkObject(factory.getURIResolver(), loggingURIRes, "set/getURIResolver API coverage");
            factory.setURIResolver(null);
            if (factory.getURIResolver() == null)
            {
                reporter.checkPass("setURIResolver(null) is OK");
            }
            else
            {
                reporter.checkFail("setURIResolver(null) not OK, is: " + factory.getURIResolver());
            }
        }
        catch (Throwable t)
        {
            reporter.checkErr("Coverage of get/setURIResolver threw: " + t.toString());
            reporter.logThrowable(reporter.STATUSMSG, t, "Coverage of get/setURIResolver threw:");
        }
        reporter.checkAmbiguous("@todo feature testing for URIResolver");

        try
        {
            factory = TransformerFactory.newInstance();
            ErrorListener errListener = factory.getErrorListener();
            if (errListener == null)
            {
                reporter.checkFail("getErrorListener() non-null by default");
            }
            else
            {
                reporter.checkPass("getErrorListener() non-null by default, is: " + errListener);
            }
            
            LoggingErrorListener loggingErrListener = new LoggingErrorListener(reporter);
            factory.setErrorListener(loggingErrListener);
            reporter.checkObject(factory.getErrorListener(), loggingErrListener, "set/getErrorListener API coverage(1)");
            try
            {
                factory.setErrorListener(null);                
                reporter.checkFail("setErrorListener(null) worked, should have thrown exception");
            }
            catch (IllegalArgumentException iae)
            {
                reporter.checkPass("setErrorListener(null) properly threw: " + iae.toString());
            }
            // Verify the previous ErrorListener is still set
            reporter.checkObject(factory.getErrorListener(), loggingErrListener, "set/getErrorListener API coverage(2)");
        }
        catch (Throwable t)
        {
            reporter.checkErr("Coverage of get/setErrorListener threw: " + t.toString());
            reporter.logThrowable(reporter.STATUSMSG, t, "Coverage of get/setErrorListener threw:");
        }
        reporter.checkAmbiguous("@todo feature testing for ErrorListener");

        reporter.testCaseClose();
        return true;
    }


    /**
     * Convenience method to print out usage information - update if needed.  
     * @return String denoting usage of this test class
     */
    public String usage()
    {
        return ("Common [optional] options supported by TransformerFactoryAPITest:\n"
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

        TransformerFactoryAPITest app = new TransformerFactoryAPITest();

        app.doMain(args);
    }
}