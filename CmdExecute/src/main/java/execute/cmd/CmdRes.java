/**
 * @Author wangyl
 * @E-mail wangyl@dsgdata.com
 **/
package execute.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author wyl
 * @version V1.0
 * @ClassName: CmdRes
 * @Function: TODO
 * @Date: 2019/10/22 23:32
 */
@Data
@Builder
@AllArgsConstructor
public class CmdRes {
    /**
     *执行的命令
     */
    private String cmd;

    /**
     *命令执行的状态
     */
    private Integer cmdExecuteStatue;

    /**
     *命令的输出信息
     */
    private List<String> outInfo;

}