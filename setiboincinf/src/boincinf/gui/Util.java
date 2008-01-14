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
package boincinf.gui;

import java.net.*;
import java.util.*;

import org.eclipse.jface.resource.*;

public class Util {
    private static ImageRegistry image_registry = null;
    private static Hashtable<String,ImageDescriptor> my_image_registry = null;

    private static URL newURL(final String url_name) {
        try {
            return new URL(url_name);
        } catch (final MalformedURLException e) {
            throw new RuntimeException("Malformed URL " + url_name, e);
        }
    }

    private static ImageDescriptor createImageFromJar(final String imgName) {
        final String urlName = "jar:file:boincinf.jar!/icons/" + imgName + ".gif";
        try {
            return ImageDescriptor.createFromURL(Util.newURL(urlName));
        } catch (final RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ImageRegistry getImageRegistry() {
        if (image_registry == null) {
            image_registry = new ImageRegistry();
            image_registry.put("run", createImageFromJar("run"));
            image_registry.put("test", createImageFromJar("test"));
        }
        return image_registry;
    }

    private static Hashtable<String,ImageDescriptor> getMyImageRegistry() {
        if (my_image_registry == null) {
            my_image_registry = new Hashtable<String,ImageDescriptor>();
            my_image_registry.put("close", createImageFromJar("close"));
            my_image_registry.put("config", createImageFromJar("config"));
            my_image_registry.put("delete", createImageFromJar("delete"));
            my_image_registry.put("export", createImageFromJar("export"));
            my_image_registry.put("import", createImageFromJar("import"));
            my_image_registry.put("update", createImageFromJar("update"));
        }
        return my_image_registry;
    }

    public static ImageDescriptor getDescriptor(final String d) {
        return getMyImageRegistry().get(d);
    }
}
