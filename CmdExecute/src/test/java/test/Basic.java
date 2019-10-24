package test;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.sun.imageio.plugins.common.I18N;
import execute.cmd.CmdRes;
import execute.cmd.RemoteCmd;
import until.IOTools;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Basic {
    public static void main(String[] args) throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String hostname = "192.168.0.101";//远程机器IP
        String username = "root";//登录用户名
        String password = "wyl.0629";//登录密码

//        try {
//            Connection conn = new Connection(hostname);
//            conn.connect();
//
//            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
//            ///是否登录成功
//            if (isAuthenticated == false) {
//                throw new IOException("Authentication failed.");
//            }
//            Session sess = conn.openSession();
//            //执行命令
//            sess.execCommand("ls");
//            sess.execCommand("ls");
//            System.out.println("Here is some information about the remote host:");
//            List<String>cmdRes = IOTools.TOOLS.getInputStreamToList(sess.getStdout());
//            cmdRes.addAll(IOTools.TOOLS.getInputStreamToList(sess.getStderr()));
//            IOTools.TOOLS.ListOutFomart(cmdRes);
//            System.out.println("ExitCode: " + sess.getExitStatus());
//            //关闭连接
//            sess.close();
//            conn.close();
//        } catch (IOException e) {
//            e.printStackTrace(System.err);
//            System.exit(2);
//        }
//        RemoteCmd remotecmd = new RemoteCmd(hostname, username, password);
//        remotecmd.setDebug(true);
////        remotecmd.ExecuteCmd("ls");
////        remotecmd.ExecuteCmd("ls -a");
//        List<String> cmds= new ArrayList<>();
//        cmds.add("ls");
//        cmds.add("ls -a");
//        remotecmd.ExecuteCmds(cmds);
//
        CmdRes tmp = CmdRes.builder().cmd("1").cmdExecuteStatue(0).build();
        IOTools.TOOLS.printClassObjectInfo(tmp);
    }
}