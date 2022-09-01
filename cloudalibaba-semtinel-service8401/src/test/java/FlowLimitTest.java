import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FlowLimitTest {
    /*初始化规则*/
    public void initRule(){
        List<FlowRule> rules = new ArrayList<>();
        //定义规则
        FlowRule rule = new FlowRule();
        //定义资源
        rule.setResource("echo");
        //定义模式
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //定义阈值
        rule.setCount(2);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @Test
    public void testFlowRule(){
        initRule();
        Entry entry = null;
        for (int i = 0; i < 10; i++) {
            try {
                entry = SphU.entry("echo");
                System.out.println("访问成功");
            } catch (BlockException e) {
                System.out.println("当前访问人数过多，请刷新后重新!");
            }finally {
                if (entry != null){
                    entry.exit();
                }
            }
        }
    }

// ============== 执行结果 =================
/**
 访问成功
 访问成功
 当前访问人数过多，请刷新后重试!
 当前访问人数过多，请刷新后重试!
 当前访问人数过多，请刷新后重试!
 当前访问人数过多，请刷新后重试!
 当前访问人数过多，请刷新后重试!
 当前访问人数过多，请刷新后重试!
 当前访问人数过多，请刷新后重试!
 当前访问人数过多，请刷新后重试!
 */

}
