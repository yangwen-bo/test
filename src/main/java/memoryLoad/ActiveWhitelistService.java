package memoryLoad;

/**
 * @Author yangwen-bo
 * @Date 2019/12/6.
 * @Version 1.0
 *
 * 激活后业务提醒白名单
 */
public interface ActiveWhitelistService {
    //根据手机号判断是否在白名单中
    public boolean contains(String mobile);
    //根据手机号以及业务id判断是否在白名单中
    public boolean contains(String mobile,String bizId);

    public static final class DelegateActiveWhitelistService implements ActiveWhitelistService{

        private ActiveWhitelistService delegate;
        public static final DelegateActiveWhitelistService instance = new DelegateActiveWhitelistService();
        public static final DelegateActiveWhitelistService getInstance(){
            return instance;
        }

        private DelegateActiveWhitelistService(){}

        public ActiveWhitelistService getDelegate(){
            return delegate;
        }
        public void setDelegate(ActiveWhitelistService delegate){
            this.delegate = delegate;
        }

        @Override
        public boolean contains(String mobile) {
            return delegate.contains( mobile );
        }

        @Override
        public boolean contains(String mobile, String bizId) {
            return delegate.contains( mobile,bizId );
        }
    }
}
