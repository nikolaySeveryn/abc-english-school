package nks.abc.dao.specification.factory.user;

import nks.abc.dao.specification.chunks.EqualSpecification;
import nks.abc.dao.specification.chunks.Specification;
import nks.abc.dao.specification.chunks.factory.AliasChunksFactory;
import nks.abc.dao.specification.chunks.factory.SpecificationChunksFactory;
import nks.abc.dao.specification.factory.CommonSpecificationFactory;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.impl.UserImpl;

public class UserSpecificationFactory extends CommonSpecificationFactory{
	
	private static final String ACCOUNT_FIELD = "account";
	
	public static UserSpecificationFactory buildFactory(){
		return new UserSpecificationFactory();
	}
	
	@Override
	protected SpecificationChunksFactory getChunksFactory() {
		return new AliasChunksFactory("user", UserImpl.class);
	}
	
	public UserSpecificationFactory() {
		super("id");
	}
	
	public Specification byAccount(Account account){
		EqualSpecification specification = getChunksFactory().createEqualsSpecification();
		specification.setCriteria(ACCOUNT_FIELD, account);
		return specification;
	}
}