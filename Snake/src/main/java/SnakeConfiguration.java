
public class SnakeConfiguration {
	//private SettingsSnake beginSettings;
	private SettingsControl control;
	
	public SnakeConfiguration(SettingsControl control)
	{
		this.control = control;
	}
	
	public SettingsControl GetControl()
	{
		return control;
	}
}
