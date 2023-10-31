package events;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class SimpleDocumentListener implements DocumentListener {
	
	@Override
	public final void insertUpdate(DocumentEvent e) {
		onChange();
	}

	@Override
	public final void removeUpdate(DocumentEvent e) {
		onChange();
	}

	@Override
	public final void changedUpdate(DocumentEvent e) {
		onChange();
	}
	
	public abstract void onChange();

}
