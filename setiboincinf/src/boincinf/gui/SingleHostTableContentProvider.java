package boincinf.gui;

import java.util.*;

import org.eclipse.jface.viewers.*;

public class SingleHostTableContentProvider implements IStructuredContentProvider {

    public Object[] getElements(final Object element) {
        final ArrayList v = (ArrayList)element;
        return v.toArray();
    }

    public void dispose() {}

    public void inputChanged(final Viewer viewer, final Object old_object, final Object new_object) {}
}
