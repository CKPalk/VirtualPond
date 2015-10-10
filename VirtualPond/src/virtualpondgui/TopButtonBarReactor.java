package virtualpondgui;

public class TopButtonBarReactor implements TopButtonBar.Reactor {
	private GUICore guiCore;
	
	public TopButtonBarReactor(GUICore guiCore) {
		this.guiCore = guiCore;
	}
	
	public void onAdd() {
		System.out.println("add not implemented");
	}
	
	public void onEdit() {
		System.out.println("edit not implemented");
	}
	
	public void onDelete() {
		System.out.println("delete not implemented");
	}
}
