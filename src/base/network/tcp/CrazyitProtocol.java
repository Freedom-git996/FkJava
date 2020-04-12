package base.network.tcp;

/**
 * @Auther: Vectory
 * @Date: 2019/10/16
 * @Description: base.network.tcp
 * @version: 1.0
 */
public interface CrazyitProtocol {
    int PROTOCOL_LEN = 2;
    String MSG_ROUND = "##";
    String USER_ROUND = "$$";
    String LOGIN_SUCCESS = "1";
    String NAME_REP = "-1";
    String PRIVATE_ROUND = "@@";
    String SPLIT_SIGN = ":";
}
