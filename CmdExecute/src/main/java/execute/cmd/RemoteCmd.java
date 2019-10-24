/**
 * @Author wangyl
 * @E-mail wangyl@dsgdata.com
 **/
package execute.cmd;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.sun.org.apache.xml.internal.security.Init;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import sun.plugin.javascript.navig.Array;
import sun.plugin.javascript.navig.LinkArray;
import until.IOTools;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author wyl
 * @version V1.0
 * @ClassName: RemoteCmd
 * @Function: TODO
 * @Date: 2019/10/21 0:07
 */
@Data
public class RemoteCmd {
    /**
     * 连接
     */
    Connection conn = null;
    Session session = null;
    /**
     * 远程地址
     */
    String host;
    /**
     * 远程主机用户名
     */
    String userName;
    /**
     * 远程主机密码
     */
    String passWord;
    /**
     * 远程主机连接端口
     */
    Integer port;
    /**
     * 是否开调试模 式默认不开启 开启有更多日志
     */
    Boolean debug = false;

    /**
     * @Description 自定义端口初始化连接
     * @param host
     * @param userName
     * @param passWord
     * @param port
     * @return
     * @Date 2019/10/25 1:07
     * @Author wangyl
     * @Version  V1.0
     */
    public RemoteCmd(String host, String userName, String passWord, Integer port) {
        this.host = host;
        this.userName = userName;
        this.passWord = passWord;
        this.port = port;
        this.conn = new Connection(host);
    }

    /**
     * @Description 默认22端口初始化连接
     * @param host
     * @param userName
     * @param passWord
     * @return
     * @Date 2019/10/25 1:08
     * @Author wangyl
     * @Version  V1.0
     */
    public RemoteCmd(String host, String userName, String passWord) {
        this(host, userName, passWord, 22);
    }

    /**
     * @Description 执行单个命令
     * @return execute.cmd.CmdRes 返回命令执行结果
     * @Date 2019/10/24 23:51
     * @Author wangyl
     * @Version V1.0
     */
    public CmdRes executeCmd(String cmd) throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        try {
            this.openConnection();
            session.execCommand(cmd);
            List<String> cmdOutInfo = IOTools.TOOLS.getInputStreamToList(session.getStdout());
            cmdOutInfo.addAll(IOTools.TOOLS.getInputStreamToList(session.getStderr()));
            CmdRes cmdRes = new CmdRes(cmd, session.getExitStatus(), cmdOutInfo);
            if (debug) {
                IOTools.TOOLS.printClassObjectInfo(cmdRes);
            }
            return cmdRes;
        }
        finally {
            session.close();
        }
    }

    /**
     * @Description 执行多个命令 如果有命令失败 则终止接下来的命令
     * @return java.util.List<execute.cmd.CmdRes>
     * @Date 2019/10/24 23:52
     * @Author wangyl
     * @Version V1.0
     */
    public List<CmdRes> executeCmds(List<String> cmds) {
        List<CmdRes> cmdResList = new ArrayList<>(5);
        for (int i = 0; i < cmds.size(); i++) {
            String cmd = cmds.get(i);
            try {
                CmdRes cmdRes = this.executeCmd(cmd);
                if (cmdRes.getCmdExecuteStatue() != 0) {
                    cmdResList.add(cmdRes);
                    break;
                }
            }
            catch (IOException | IllegalAccessException | IntrospectionException | InvocationTargetException e) {
                CmdRes cmdres = CmdRes.builder().cmd(cmd).cmdExecuteStatue(-1).outInfo(Arrays.asList(IOTools.TOOLS.getExceptionToString(e))).build();
                cmdResList.add(cmdres);
            }
        }
        return cmdResList;
    }

    /**
     * @Description 开启连接
     * @return void
     * @Date 2019/10/25 0:00
     * @Author wangyl
     * @Version V1.0
     */
    private void openConnection() throws IOException {
        if (!conn.isAuthenticationComplete()) {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(userName, passWord);
            ///是否登录成功
            if (isAuthenticated == false) {
                throw new IOException("登录失败");
            }
        }
        session = conn.openSession();
    }

}