package remote.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KeyValueView<T extends JComponent> extends JPanel {

	private static final long serialVersionUID = -1052404630453546964L;
	
	private JLabel label;
	private T view;
	
	public KeyValueView(String labelText, Dimension labelPreferredSize, Dimension viewPreferredSize, T view) {
		label = new JLabel(labelText);
		label.setPreferredSize(labelPreferredSize);
		this.view = view;
		view.setPreferredSize(viewPreferredSize);
		
		setLayout(new FlowLayout());
		add(label);
		add(view);
	}
	
	public T getView() {
		return view;
	}

}
