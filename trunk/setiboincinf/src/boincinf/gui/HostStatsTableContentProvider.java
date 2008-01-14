package boincinf.gui;

import java.util.ArrayList;

import org.eclipse.jface.viewers.*;

public class HostStatsTableContentProvider implements IStructuredContentProvider {
    public Object[] getElements(Object element) {
        // called after setInput
        ArrayList v = (ArrayList)element;
        return v.toArray();
    }

    public void dispose() {}

    public void inputChanged(Viewer viewer, Object old_object, Object new_object) {}
}
