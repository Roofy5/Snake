
public class SnakeConfiguration {
	private SettingsSnake beginSettings;
	private SettingsControl control;
	
	public SnakeConfiguration(SettingsSnake beginSettings, SettingsControl control)
	{
		this.beginSettings = beginSettings;
		this.control = control;
	}
	
	public SettingsControl GetControl()
	{
		return control;
	}
	
	public SettingsSnake GetSettings()
	{
		return beginSettings;
	}
}
