package memoryLoad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author yangwen-bo
 * @Date 2019/12/6.
 * @Version 1.0
 *
 * 加载激活后提醒业务白名单到内存中，对外提供方法，方便操作，提升速率
 */
public class DbActiveWhitelistService implements ActiveWhitelistService{

    private static final Logger LOGGER = LoggerFactory.getLogger( DbActiveWhitelistService.class );

    @Autowired
    private TblActiveWhitelistService tblActiveWhitelistService;



    @Override
    public boolean contains(String mobile) {
        return false;
    }

    @Override
    public boolean contains(String mobile, String bizId) {
        return false;
    }
}
