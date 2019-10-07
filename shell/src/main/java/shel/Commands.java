package shel;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Commands {
    @ShellMethod("sum函数")
    public int add(int param1, int param2, @ShellOption(defaultValue = "0") int param3) {
        return param1 + param2 + param3;
    }
}