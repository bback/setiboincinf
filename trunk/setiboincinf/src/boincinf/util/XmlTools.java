/*
  Copyright (C) 2008  SetiBoincInf Project

  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License as
  published by the Free Software Foundation; either version 2 of
  the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
*/
package boincinf.util;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.apache.xml.serialize.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XmlTools
{
    /**
     * Parses an XML file and returns a DOM document.
     * If validating is true, the contents is validated against the DTD
     * specified in the file.
     */
    public static Document parseXmlFile(final File file, final boolean validating)
    throws IllegalArgumentException
    {
        try
        {
            // Create a builder factory
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(validating);

            // Create the builder and parse the file
            final Document doc = factory.newDocumentBuilder().parse(file);
            return doc;
        }
        catch( final SAXException e )
        {
            // parsing error occurred; the xml input is not valid
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        catch( final ParserConfigurationException e )
        {
            e.printStackTrace();
        }
        catch( final IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Document parseXmlFile(final InputStream is, final boolean validating)
    throws IllegalArgumentException
    {
        try
        {
            // Create a builder factory
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(validating);

            // Create the builder and parse the file
            final Document doc = factory.newDocumentBuilder().parse(is);
            return doc;
        }
        catch( final SAXException e )
        {
            // parsing error occurred; the xml input is not valid
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        catch( final ParserConfigurationException e )
        {
            e.printStackTrace();
        }
        catch( final IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method writes a DOM document to a file.
     */
    public static boolean writeXmlFile(final Document doc, final String filename)
    throws Throwable
    {
        try {
            //OutputFormat format = new OutputFormat(doc);
            final OutputFormat format = new OutputFormat(doc, "UTF-16", false);
            format.setLineSeparator(LineSeparator.Windows);
            //format.setIndenting(true);
            format.setLineWidth(0);
            format.setPreserveSpace(true);
            final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), "UTF-16");
            final XMLSerializer serializer = new XMLSerializer (writer, format);
            serializer.asDOMSerializer();
            serializer.serialize(doc);
            return true;
        }
        catch(final Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * This method creates a new DOM document.
     */
    public static Document createDomDocument()
    {
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document doc = builder.newDocument();
            return doc;
        } catch (final ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gets a true or false attribute from an element
     */
    public static boolean getBoolValueFromAttribute(final Element el, final String attr, final boolean defaultVal)
    {
        final String res = el.getAttribute(attr);

        if( res == null ) {
            return defaultVal;
        }

        if( res.toLowerCase().equals("true") == true ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a list containing all Elements of this parent with given tag name.
     */
    public static ArrayList<Element> getChildElementsByTagName(final Element parent, final String tagname)
    {
        final ArrayList<Element> newList = new ArrayList<Element>();

        final NodeList childs = parent.getChildNodes();
        for( int x=0; x<childs.getLength(); x++ )
        {
            final Node child = childs.item(x);
            if( child.getNodeType() == Node.ELEMENT_NODE )
            {
                final Element ele = (Element)child;
                if( ele.getTagName().equals( tagname ) == true )
                {
                    newList.add( ele );
                }
            }
        }
        return newList;
    }

    /**
     *
     */
    public static Element findFirstElementByTagName(final Element parent, final String tagname)
    {
        final NodeList childs = parent.getChildNodes();
        for( int x=0; x<childs.getLength(); x++ )
        {
            final Node child = childs.item(x);
            if( child.getNodeType() == Node.ELEMENT_NODE )
            {
                final Element ele = (Element)child;
                if( ele.getTagName().equals( tagname ) == true )
                {
                    return ele;
                }
            }
        }
        return null;
    }

    /**
     * Gets the Element by name from parent and extracts the Text child node.
     * E.g.:
     * <parent>
     *   <child>
     *     text
     */
    public static String getChildElementsTextValue( final Element parent, final String childname )
    {
        final ArrayList<Element> nodes = getChildElementsByTagName( parent, childname );
        if( nodes.size() == 0 ) {
            return null;
        }

        final Text txtname = (Text) (((Node)nodes.get(0)).getFirstChild());

        if( txtname == null ) {
            return null;
        }
        return txtname.getData().trim();
    }

    /**
     * Gets the Element by name from parent and extracts the CDATASection child node.
     */
    public static String getChildElementsCDATAValue( final Element parent, final String childname )
    {
        final ArrayList<Element> nodes = getChildElementsByTagName( parent, childname );
        if( nodes.size() == 0 ) {
            return null;
        }
        final CDATASection txtname = (CDATASection) ((Node)nodes.get(0)).getFirstChild();
        if( txtname == null ) {
            return null;
        }
        return txtname.getData().trim();
    }

    // simple helper
    public static String getValidText(final Element ele, final String childname)
    {
        final String s = XmlTools.getChildElementsTextValue(ele, childname);
        if( s == null )
        {
            System.out.println("Error: Element not in XML: "+childname);
            return "";
        }
        return s;
    }

}
