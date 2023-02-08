


public class RobotDesigner{
	
	private DesignerGui window;
	
	//cria a gui e vai buscar o tempo em que foi iniciado o programa para prints na gui de tempo
	public RobotDesigner() {
		window = new DesignerGui("Mensagens executadas");
		window.setVisible();
	}
	
	public void setTextToWindow(String txt) {
		window.setTextToTextArea(window.getTextFromTextArea()+txt+"\n");
	}
	
}
