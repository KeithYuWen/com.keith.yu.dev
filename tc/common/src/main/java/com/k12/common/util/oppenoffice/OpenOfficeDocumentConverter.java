package com.k12.common.util.oppenoffice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.converter.AbstractOpenOfficeDocumentConverter; 
import com.sun.star.awt.Size;
import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.task.ErrorCodeIOException;
import com.sun.star.ucb.XFileIdentifierConverter;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.CloseVetoException;
import com.sun.star.util.XCloseable;
import com.sun.star.view.PaperFormat;
import com.sun.star.view.PaperOrientation;
import com.sun.star.view.XPrintable;

public class OpenOfficeDocumentConverter extends AbstractOpenOfficeDocumentConverter{ 
    private static final Logger logger = LoggerFactory.getLogger(OpenOfficeDocumentConverter.class);
    public OpenOfficeDocumentConverter(OpenOfficeConnection connection) {
        super(connection);
    }

    public OpenOfficeDocumentConverter(OpenOfficeConnection connection, DocumentFormatRegistry formatRegistry) {
        super(connection, formatRegistry);
    }
    public final static Size A5, A4, A3;
    public final static Size B4, B5, B6;
    
    static {
        A5 = new Size(14800, 21000);
        A4 = new Size(21000, 29700);
        A3 = new Size(29700, 42000);
    
        B4 = new Size(25000, 35300);
        B5 = new Size(17600, 25000);
        B6 = new Size(12500, 17600);
        
    }

/*
 * XComponent:xCalcComponent
 * 
 * @seecom.artofsolving.jodconverter.openoffice.converter.
 * AbstractOpenOfficeDocumentConverter
 * #refreshDocument(com.sun.star.lang.XComponent)
 */
@Override
protected void refreshDocument(XComponent document) {
    super.refreshDocument(document);

    // The default paper format and orientation is A4 and portrait. To
    // change paper orientation
    // re set page size
    XPrintable xPrintable = (XPrintable) UnoRuntime.queryInterface(XPrintable.class, document);
    PropertyValue[] printerDesc = new PropertyValue[3];

    // Paper Orientation
   printerDesc[0] = new PropertyValue();
   printerDesc[0].Name = "PaperOrientation";
   printerDesc[0].Value = PaperOrientation.LANDSCAPE;//landscape是横向,portrait是纵向

    // Paper Format
    printerDesc[0] = new PropertyValue();
    printerDesc[0].Name = "PaperFormat";
    printerDesc[0].Value = PaperFormat.A4;

    // Paper Size
    printerDesc[1] = new PropertyValue();
    printerDesc[1].Name = "PaperSize";
    printerDesc[1].Value = A4;

    try {
        xPrintable.setPrinter(printerDesc);
    } catch (com.sun.star.lang.IllegalArgumentException e) {
        e.printStackTrace();
    }

}

@Override
protected void convertInternal(InputStream inputStream, DocumentFormat inputFormat, OutputStream outputStream, DocumentFormat outputFormat) {
    File inputFile = null;
    File outputFile = null;
    try {
        inputFile = File.createTempFile("document", "." + inputFormat.getFileExtension());
        OutputStream inputFileStream = null;
        try {
            inputFileStream = new FileOutputStream(inputFile);
            IOUtils.copy(inputStream, inputFileStream);
        } finally {
            IOUtils.closeQuietly(inputFileStream);
        }
        
        outputFile = File.createTempFile("document", "." + outputFormat.getFileExtension());
        convert(inputFile, inputFormat, outputFile, outputFormat);
        InputStream outputFileStream = null;
        try {
            outputFileStream = new FileInputStream(outputFile);
            IOUtils.copy(outputFileStream, outputStream);
        } finally {
            IOUtils.closeQuietly(outputFileStream);
        }
    } catch (IOException ioException) {
        throw new OpenOfficeException("conversion failed", ioException);
    } finally {
        if (inputFile != null) {
            inputFile.delete();
        }
        if (outputFile != null) {
            outputFile.delete();
        }
    }
}

@Override
protected void convertInternal(File inputFile, DocumentFormat inputFormat, File outputFile, DocumentFormat outputFormat) {
    Map/*<String,Object>*/ loadProperties = new HashMap();
    loadProperties.putAll(getDefaultLoadProperties());
    loadProperties.putAll(inputFormat.getImportOptions());

    Map/*<String,Object>*/ storeProperties = outputFormat.getExportOptions(inputFormat.getFamily());

    synchronized (openOfficeConnection) {
        XFileIdentifierConverter fileContentProvider = openOfficeConnection.getFileContentProvider();
        String inputUrl = fileContentProvider.getFileURLFromSystemPath("", inputFile.getAbsolutePath());
        String outputUrl = fileContentProvider.getFileURLFromSystemPath("", outputFile.getAbsolutePath());
        
        loadAndExport(inputUrl, loadProperties, outputUrl, storeProperties);
    }
    
} 
private void loadAndExport(String inputUrl, Map/*<String,Object>*/ loadProperties, String outputUrl, Map/*<String,Object>*/ storeProperties) throws OpenOfficeException {
    XComponent document;
    try {
        document = loadDocument(inputUrl, loadProperties);
    } catch (ErrorCodeIOException errorCodeIOException) {
        throw new OpenOfficeException("conversion failed: could not load input document; OOo errorCode: " + errorCodeIOException.ErrCode, errorCodeIOException);
    } catch (Exception otherException) {
        throw new OpenOfficeException("conversion failed: could not load input document", otherException);
    }
    if (document == null) {
        throw new OpenOfficeException("conversion failed: could not load input document");
    }
    
    refreshDocument(document);
    
    try {
        storeDocument(document, outputUrl, storeProperties);
    } catch (ErrorCodeIOException errorCodeIOException) {
        throw new OpenOfficeException("conversion failed: could not save output document; OOo errorCode: " + errorCodeIOException.ErrCode, errorCodeIOException);
    } catch (Exception otherException) {
        throw new OpenOfficeException("conversion failed: could not save output document", otherException);
    }
}

private XComponent loadDocument(String inputUrl, Map loadProperties) throws com.sun.star.io.IOException, IllegalArgumentException {
    XComponentLoader desktop = openOfficeConnection.getDesktop();
    return desktop.loadComponentFromURL(inputUrl, "_blank", 0, toPropertyValues(loadProperties));
}

private void storeDocument(XComponent document, String outputUrl, Map storeProperties) throws com.sun.star.io.IOException {
    try {
        XStorable storable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
        storable.storeToURL(outputUrl, toPropertyValues(storeProperties));
    } finally {
        XCloseable closeable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class, document);
        if (closeable != null) {
            try {
                closeable.close(true);
            } catch (CloseVetoException closeVetoException) {
                logger.warn("document.close() vetoed");
            }
        } else {
            document.dispose();
        }
    }
}
}
