package uk.ac.ebi.mdk.ui.render.list;

import org.apache.log4j.Logger;
import uk.ac.ebi.caf.component.theme.Theme;
import uk.ac.ebi.caf.component.theme.ThemeManager;

import javax.swing.*;

/**
 * Renderer using the normal font/colour for list
 *
 * @author John May
 */
public class DefaultRenderer<O>
        extends JLabel
        implements ListCellRenderer {

    private static final Logger LOGGER = Logger.getLogger(DefaultRenderer.class);

    public DefaultRenderer() {
        Theme theme = ThemeManager.getInstance().getTheme();
        setFont(theme.getBodyFont());
        setOpaque(true); // needs to be opaque for background
    }

    public JLabel getComponent(JList list, O value, int index) {
        setText(value.toString());
        return this;
    }

    @Override
    public final JLabel getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

        if (value == null) {
            setText("");
            return this;
        }

        return getComponent(list, (O) value, index);

    }

}
