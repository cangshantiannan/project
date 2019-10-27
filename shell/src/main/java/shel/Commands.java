package shel;

import execute.cmd.RemoteCmd;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

@ShellComponent
public class Commands {
    @ShellMethod("连接linux的SSH")
    public void add(String host, String username,String password, @ShellOption(defaultValue = "22") Integer port) throws InvocationTargetException, IntrospectionException, IllegalAccessException, IOException {
        RemoteCmd remoteCmd = new RemoteCmd(host, username,password , port);
        remoteCmd.setDebug(true);
        while (true)
        {
            Scanner input = new Scanner(System.in);
            String cmd  = input.nextLine();
            if("stop".equals(cmd))
            {
                break;
            }
            remoteCmd.executeCmd(cmd);
        }
        return;
    }
}